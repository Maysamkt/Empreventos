package br.edu.ifgoiano.Empreventos.controller;

import br.edu.ifgoiano.Empreventos.dto.request.UserRequestDTO;
import br.edu.ifgoiano.Empreventos.dto.response.UserResponseDTO;
import br.edu.ifgoiano.Empreventos.model.Role;
import br.edu.ifgoiano.Empreventos.security.jwt.UserDetailsImpl;
import br.edu.ifgoiano.Empreventos.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Usuários", description = "Operações relacionadas a gerenciamento de usuários e perfis")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Registrar um novo usuário", description = "Permite que qualquer pessoa se registre no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário registrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou email já em uso")
    })
    public UserResponseDTO create(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        return userService.registerUser(userRequestDTO);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Listar todos os usuários", description = "Apenas administradores podem listar todos os usuários ativos.")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Acesso negado, apenas ADMINs podem acessar")
    })
    public List<UserResponseDTO> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    @Operation(summary = "Buscar usuário por ID", description = "Admin pode buscar qualquer usuário; outros usuários podem buscar a si mesmos.")
    @Parameter(name = "id", description = "ID do usuário", required = true, example = "1")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public UserResponseDTO findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    @Operation(summary = "Atualizar detalhes do usuário", description = "Permite a um ADMIN atualizar qualquer usuário ou um usuário comum atualizar seus próprios dados.")
    @SecurityRequirement(name = "bearerAuth")
    @Parameter(name = "id", description = "ID do usuário a ser atualizado", required = true, example = "1")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public UserResponseDTO update(@PathVariable Long id,
                                  @Valid @RequestBody UserRequestDTO userRequestDTO) {
        return userService.update(id, userRequestDTO);
    }

    @PutMapping("/me")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Atualizar os próprios dados do usuário logado", description = "Permite que o usuário autenticado atualize seus próprios dados de perfil.")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados do usuário atualizados com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "401", description = "Não autenticado")
    })
    public UserResponseDTO updateMyDetails(@AuthenticationPrincipal UserDetails currentUser,
                                           @Valid @RequestBody UserRequestDTO userRequestDTO) {

        if (currentUser instanceof UserDetailsImpl) {
            Long userId = ((UserDetailsImpl) currentUser).getId();
            return userService.update(userId, userRequestDTO);
        }

        throw new IllegalArgumentException("Não foi possível obter o ID do usuário autenticado.");
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    @Operation(summary = "Deletar um usuário", description = "Permite a um ADMIN deletar qualquer usuário ou um usuário comum deletar sua própria conta (exclusão lógica).")
    @SecurityRequirement(name = "bearerAuth")
    @Parameter(name = "id", description = "ID do usuário a ser deletado", required = true, example = "1")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso (No Content)"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

    // Endpoint para o usuário logado obter seus próprios detalhes completos
    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()") // Apenas exige que o usuário esteja autenticado
    @Operation(summary = "Obter detalhes do usuário logado", description = "Retorna os detalhes do perfil do usuário atualmente autenticado.")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detalhes do usuário logado retornados com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autenticado")
    })
    public UserResponseDTO getMyDetails(@AuthenticationPrincipal UserDetails currentUser) {
        // userDetails é injetado automaticamente pelo Spring Security com as informações do usuário logado
        return userService.findCurrentUserDetails(currentUser);
    }

    // Endpoints específicos para tipos de usuário
    @PostMapping("/speakers")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Registrar um novo palestrante", description = "Permite registrar um novo usuário com a role de PALESTRANTE (SPEAKER).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Palestrante registrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou email já em uso")
    })
    public UserResponseDTO createSpeaker(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        userRequestDTO.setRoles(Set.of(Role.RoleName.SPEAKER));
        return userService.registerUser(userRequestDTO);
    }

    @PostMapping("/listeners")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Registrar um novo participante", description = "Permite registrar um novo usuário com a role de PARTICIPANTE (LISTENER).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Participante registrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou email já em uso")
    })
    public UserResponseDTO createListener(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        userRequestDTO.setRoles(Set.of(Role.RoleName.LISTENER));
        return userService.registerUser(userRequestDTO);
    }

    @PostMapping("/organizers")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Registrar um novo organizador", description = "Permite registrar um novo usuário com a role de ORGANIZADOR.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Organizador registrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou email já em uso")
    })
    public UserResponseDTO createOrganizer(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        userRequestDTO.setRoles(Set.of(Role.RoleName.ORGANIZER));
        return userService.registerUser(userRequestDTO);
    }
}