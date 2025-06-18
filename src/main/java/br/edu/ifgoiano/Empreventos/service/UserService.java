package br.edu.ifgoiano.Empreventos.service;

import br.edu.ifgoiano.Empreventos.dto.request.UserRequestDTO;
import br.edu.ifgoiano.Empreventos.dto.response.UserResponseDTO;
import br.edu.ifgoiano.Empreventos.model.Role;
import br.edu.ifgoiano.Empreventos.model.User;

import br.edu.ifgoiano.Empreventos.mapper.UserMapper;
import br.edu.ifgoiano.Empreventos.model.UserRole;
import br.edu.ifgoiano.Empreventos.repository.RoleRepository;
import br.edu.ifgoiano.Empreventos.repository.UserRepository;
import br.edu.ifgoiano.Empreventos.security.jwt.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final Logger logger = Logger.getLogger(UserService.class.getName());

    @Autowired
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public List<UserResponseDTO> findAll() {
        logger.info("Buscando todos os usuários ativos");
        return userRepository.findAllActiveUsers().stream()
                .map(userMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserResponseDTO findById(Long id) {
        logger.info("Buscando usuário por ID: " + id);
        User user = userRepository.findActiveById(id)
                .orElseThrow(() -> new NoSuchElementException("Usuário com ID " + id + " não encontrado"));
        return userMapper.toResponseDTO(user);
    }

    @Transactional
    public UserResponseDTO registerUser(UserRequestDTO userRequestDTO) {
        logger.info("Registrando novo usuário: " + userRequestDTO.getEmail());
        validateUserRequest(userRequestDTO);

        User user = userMapper.toEntity(userRequestDTO);
        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        user.setActive(true);
        user.setCreated_at(new Date());
        user.setUpdated_at(new Date());

        // Busca todas as roles de uma vez
        Set<Role> roles = roleRepository.findAllByNameIn(userRequestDTO.getRoles());

        // Associa roles ao usuário
        roles.forEach(role -> {
            UserRole userRole = new UserRole();
            userRole.setUser(user);
            userRole.setRole(role);
            user.getUserRoles().add(userRole);
        });

        User savedUser = userRepository.save(user);
        return userMapper.toResponseDTO(savedUser);
    }

    @Transactional
    public UserResponseDTO update(Long id, UserRequestDTO userRequestDTO) {
        logger.info("Atualizando usuário ID: " + id);
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Usuário com ID " + id + " não encontrado"));

        userRequestDTO.setCpf_cnpj(null);
        if (userRequestDTO.getEmail() != null && !userRequestDTO.getEmail().equals(existingUser.getEmail())) {
            if (userRepository.existsByEmail(userRequestDTO.getEmail())) {
                throw new IllegalArgumentException("Email '" + userRequestDTO.getEmail() + "' já está em uso por outro usuário.");
            }
        }
        if (userRequestDTO.getPassword() != null && !userRequestDTO.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        }
        userMapper.updateEntityFromDTO(userRequestDTO, existingUser);
        existingUser.setUpdated_at(new Date());

        if (userRequestDTO.getRoles() != null && !userRequestDTO.getRoles().isEmpty()) {
            updateUserRoles(existingUser, userRequestDTO.getRoles());
        }

        User updatedUser = userRepository.save(existingUser);
        return userMapper.toResponseDTO(updatedUser);
    }

    @Transactional
    public void delete(Long id) {
        logger.info("Deletando (inativando) usuário ID: " + id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Usuário com ID " + id + " não encontrado"));

        user.setActive(false);
        user.setDeleted_at(new Date());
        userRepository.save(user);
    }

    // Método de validação para CRIAÇÃO de usuário
    private void validateUserCreation(UserRequestDTO userRequestDTO) {
        if (userRepository.existsByEmail(userRequestDTO.getEmail())) {
            throw new IllegalArgumentException("Email já está em uso");
        }

        if (userRequestDTO.getRoles() == null || userRequestDTO.getRoles().isEmpty()) {
            throw new IllegalArgumentException("Pelo menos uma role deve ser especificada");
        }
    }

    // Métodos auxiliares privados
    private void validateUserRequest(UserRequestDTO userRequestDTO) {
        if (userRepository.existsByEmail(userRequestDTO.getEmail())) {
            throw new IllegalArgumentException("Email já está em uso");
        }

        if (userRequestDTO.getRoles() == null || userRequestDTO.getRoles().isEmpty()) {
            throw new IllegalArgumentException("Pelo menos uma role deve ser especificada");
        }
    }

    private void addRolesToUser(User user, Set<Role.RoleName> roleNames) {
        roleNames.forEach(roleName -> {
            Role role = roleRepository.findByName(roleName)
                    .orElseThrow(() -> new IllegalArgumentException("Role não encontrada: " + roleName));
            user.addRole(role);
        });
    }

    private void updateUserRoles(User user, Set<Role.RoleName> newRoleNames) {

        if (user.getUserRoles() == null) {
            user.setUserRoles(new HashSet<>());
        } else {

            user.getUserRoles().size();
        }
        Set<UserRole> desiredUserRoles = newRoleNames.stream()
                .map(roleName -> {
                    Role role = roleRepository.findByName(roleName)
                            .orElseThrow(() -> new IllegalArgumentException("Role não encontrada: " + roleName));


                    UserRole newUserRole = new UserRole();
                    newUserRole.setUser(user);
                    newUserRole.setRole(role);
                    return newUserRole;
                })
                .collect(Collectors.toSet());
        user.getUserRoles().removeIf(existingUserRole -> !desiredUserRoles.contains(existingUserRole));

        desiredUserRoles.forEach(desired -> {
            if (!user.getUserRoles().contains(desired)) {
                user.getUserRoles().add(desired);
                desired.setUser(user);
            }
        });
    }

    @Transactional(readOnly = true)
    public UserResponseDTO findCurrentUserDetails(UserDetails userDetails) {
        if (userDetails instanceof UserDetailsImpl) {
            UserDetailsImpl userDetailsImpl = (UserDetailsImpl) userDetails;

            User user = userRepository.findActiveById(userDetailsImpl.getId())
                    .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado: " + userDetailsImpl.getId()));

            // Mapeia o User completo para o DTO de resposta
            return userMapper.toResponseDTO(user);
        }
        throw new IllegalArgumentException("Detalhes do usuário não é do tipo esperado. Certifique-se de que o usuário está autenticado corretamente.");
    }

}