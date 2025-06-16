package br.edu.ifgoiano.Empreventos.repository;

import br.edu.ifgoiano.Empreventos.model.Role;
import br.edu.ifgoiano.Empreventos.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository  extends JpaRepository<User, Long> {

    @EntityGraph(attributePaths = {"userRoles", "userRoles.role"})

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.deleted_at IS NULL")
    List<User> findAllActiveUsers();
    @Query("SELECT r FROM Role r WHERE r.name IN :names")
    Set<Role> findAllByNameIn(@Param("names") Set<Role.RoleName> names);
    @Query("SELECT u FROM User u WHERE u.id = :id AND u.active = true")
    Optional<User> findActiveById(@Param("id") Long id);

    Optional<User> findByEmailAndActiveTrue(String email);

}
