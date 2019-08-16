package cl.tolhuysen.henriquez.jorge.desafiotecnicospring.repositories;

import cl.tolhuysen.henriquez.jorge.desafiotecnicospring.model.Ledger;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface LedgerRepository  extends CrudRepository<Ledger, Long> {
    @Query("SELECT SUM(l.money) FROM Ledger l WHERE l.borrowerData.borrowerId = :id")
    public Optional<BigDecimal> summAllByBorrowerId(@Param("id") Long id);

    @Query("SELECT DISTINCT SUM(l.money), l.lenderData.lenderId FROM Ledger l WHERE l.borrowerData.borrowerId = :id GROUP BY l.lenderData.lenderId")
    public List<Object[]> summAllDistinctByBorrowerId(@Param("id") Long id);

    @Query("SELECT DISTINCT SUM(l.money), l.borrowerData.borrowerId FROM Ledger l WHERE l.lenderData.lenderId = :id GROUP BY l.borrowerData.borrowerId")
    public List<Object[]> summAllDistinctByLenderId(@Param("id") Long id);

    @Query("SELECT SUM(l.money) FROM Ledger l WHERE l.lenderData.lenderId = :id")
    public Optional<BigDecimal> summAllByLenderId(@Param("id") Long id);

    @Query("SELECT l FROM Ledger l WHERE l.borrowerData.borrowerId = :id")
    public Iterable<Ledger> findAllByBorrowerId(@Param("id") Long id);

    @Query("SELECT l FROM Ledger l WHERE l.lenderData.lenderId = :id")
    public Iterable<Ledger> findAllByLenderId(@Param("id") Long id);


    @Query("SELECT DISTINCT l FROM Ledger l WHERE l.borrowerData.borrowerId = :id")
    public Iterable<Ledger> findAllDistinctByBorrowerId(@Param("id") Long id);

    @Query("SELECT DISTINCT l FROM Ledger l WHERE l.lenderData.lenderId = :id")
    public Iterable<Ledger> findAllDistinctByLenderId(@Param("id") Long id);

}
