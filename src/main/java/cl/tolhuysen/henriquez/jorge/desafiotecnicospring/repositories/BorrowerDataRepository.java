package cl.tolhuysen.henriquez.jorge.desafiotecnicospring.repositories;

import cl.tolhuysen.henriquez.jorge.desafiotecnicospring.model.BorrowerData;
import org.springframework.data.repository.CrudRepository;

public interface BorrowerDataRepository extends CrudRepository<BorrowerData, Long> {
}
