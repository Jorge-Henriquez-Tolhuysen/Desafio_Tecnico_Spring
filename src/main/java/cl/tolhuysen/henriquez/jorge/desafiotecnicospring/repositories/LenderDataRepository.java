package cl.tolhuysen.henriquez.jorge.desafiotecnicospring.repositories;

import cl.tolhuysen.henriquez.jorge.desafiotecnicospring.model.LenderData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LenderDataRepository extends JpaRepository<LenderData, Long> {
}
