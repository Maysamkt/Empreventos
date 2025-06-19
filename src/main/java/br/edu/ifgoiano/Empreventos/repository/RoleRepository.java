package br.edu.ifgoiano.Empreventos.repository;

import br.edu.ifgoiano.Empreventos.model.Role;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;


@Repository
public interface RoleRepository extends JpaRepository<Role, Byte> {

    Optional<Role> findByName(Role.RoleName name);
    Set<Role> findAllByNameIn(Set<Role.RoleName> names);
    boolean existsByName(Role.RoleName name);
}
