package br.edu.ifgoiano.Empreventos.repository;

import br.edu.ifgoiano.Empreventos.model.MaterialComplementar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialComplementarRepository extends JpaRepository<MaterialComplementar, Long> {
    List<MaterialComplementar> findByAtividadeId(Long atividadeId);
}