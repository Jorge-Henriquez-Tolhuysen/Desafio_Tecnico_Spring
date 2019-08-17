package cl.tolhuysen.henriquez.jorge.desafiotecnicospring.repositories;

import cl.tolhuysen.henriquez.jorge.desafiotecnicospring.model.BorrowerData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowerDataRepository extends JpaRepository<BorrowerData, Long> {
}
