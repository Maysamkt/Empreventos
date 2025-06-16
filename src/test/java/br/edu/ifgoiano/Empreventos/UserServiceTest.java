package br.edu.ifgoiano.Empreventos;

import br.edu.ifgoiano.Empreventos.dto.request.UserRequestDTO;
import br.edu.ifgoiano.Empreventos.dto.response.UserResponseDTO;
import br.edu.ifgoiano.Empreventos.mapper.UserMapper;
import br.edu.ifgoiano.Empreventos.model.Role;
import br.edu.ifgoiano.Empreventos.model.User;
import br.edu.ifgoiano.Empreventos.model.UserRole;
import br.edu.ifgoiano.Empreventos.repository.RoleRepository;
import br.edu.ifgoiano.Empreventos.repository.UserRepository;
import br.edu.ifgoiano.Empreventos.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User user;
    private UserRequestDTO userRequestDTO;
    private UserResponseDTO userResponseDTO;
    private Role role;


    @BeforeEach
    void setUp() {
        role = new Role();
        role.setId((byte) 1);
        role.setName(Role.RoleName.LISTENER);
        user = new User();
        user.setId(1L);
        user.setName("Test User");
        user.setEmail("test@test.com");
        user.setPassword("encodedPassword");
        user.setActive(true);
        UserRole userRole = new UserRole(user, role);
        user.getUserRoles().add(userRole);
        userRequestDTO = new UserRequestDTO();
        userRequestDTO.setName("Test User");
        userRequestDTO.setEmail("test@test.com");
        userRequestDTO.setPassword("password123");
        userRequestDTO.setRoles(Set.of(Role.RoleName.LISTENER));
        userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(1L);
        userResponseDTO.setName("Test User");
        userResponseDTO.setEmail("test@test.com");
        userResponseDTO.setRoles(Collections.singletonList("LISTENER"));
    }
    @Test
    @DisplayName("Deve registrar um novo usuário com sucesso")
    void registerUser_Success() {
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userMapper.toEntity(any(UserRequestDTO.class))).thenReturn(user);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(roleRepository.findAllByNameIn(anySet())).thenReturn(Set.of(role));
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.toResponseDTO(any(User.class))).thenReturn(userResponseDTO);
        UserResponseDTO result = userService.registerUser(userRequestDTO);
        assertNotNull(result);
        assertEquals(userResponseDTO.getEmail(), result.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Deve falhar ao registrar usuário com email que já existe")
    void registerUser_EmailAlreadyExists() {
        when(userRepository.existsByEmail(anyString())).thenReturn(true);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.registerUser(userRequestDTO);
        });

        assertEquals("Email já está em uso", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Deve encontrar um usuário por ID com sucesso")
    void findById_Success() {
        when(userRepository.findActiveById(anyLong())).thenReturn(Optional.of(user));
        when(userMapper.toResponseDTO(any(User.class))).thenReturn(userResponseDTO);
        UserResponseDTO result = userService.findById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test User", result.getName());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar encontrar um usuário por ID que não existe")
    void findById_NotFound() {
        when(userRepository.findActiveById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> {
            userService.findById(1L);
        });
    }

    @Test
    @DisplayName("Deve deletar (inativar) um usuário com sucesso")
    void delete_Success() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        userService.delete(1L);
        verify(userRepository, times(1)).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();
        assertFalse(savedUser.getActive());
        assertNotNull(savedUser.getDeleted_at());
    }
}