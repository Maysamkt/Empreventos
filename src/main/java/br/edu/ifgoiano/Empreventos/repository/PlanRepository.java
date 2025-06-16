package br.edu.ifgoiano.Empreventos.repository;
import br.edu.ifgoiano.Empreventos.model.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository<Plan, Long> {
}
