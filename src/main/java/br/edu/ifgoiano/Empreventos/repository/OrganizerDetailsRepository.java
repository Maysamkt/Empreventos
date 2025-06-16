package br.edu.ifgoiano.Empreventos.repository;

import br.edu.ifgoiano.Empreventos.model.OrganizerDetails;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface OrganizerDetailsRepository extends JpaRepository<OrganizerDetails, Long> {

    List<OrganizerDetails> findByUser_id (Long user);

}
