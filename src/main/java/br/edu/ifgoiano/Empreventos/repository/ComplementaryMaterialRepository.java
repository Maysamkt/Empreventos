package br.edu.ifgoiano.Empreventos.repository;

import br.edu.ifgoiano.Empreventos.model.ComplementaryMaterial; // ALTERADO
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository

public interface ComplementaryMaterialRepository extends JpaRepository<ComplementaryMaterial, Integer> {
    List<ComplementaryMaterial> findByActivityId(Integer activityId);
}