package br.edu.ifgoiano.Empreventos.controller;

import br.edu.ifgoiano.Empreventos.dto.request.UserRequestDTO;
import br.edu.ifgoiano.Empreventos.dto.response.UserResponseDTO;
import br.edu.ifgoiano.Empreventos.model.Role;
import br.edu.ifgoiano.Empreventos.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDTO create(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        return userService.registerUser(userRequestDTO);
    }

    @GetMapping
    public List<UserResponseDTO> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public UserResponseDTO findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @PutMapping("/{id}")
    public UserResponseDTO update(@PathVariable Long id,
                                  @Valid @RequestBody UserRequestDTO userRequestDTO) {
        return userService.update(id, userRequestDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

    // Endpoints específicos para tipos de usuário
    @PostMapping("/speakers")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDTO createSpeaker(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        userRequestDTO.setRoles(Set.of(Role.RoleName.SPEAKER));
        return userService.registerUser(userRequestDTO);
    }

    @PostMapping("/listeners")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDTO createListener(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        userRequestDTO.setRoles(Set.of(Role.RoleName.LISTENER));
        return userService.registerUser(userRequestDTO);
    }
}