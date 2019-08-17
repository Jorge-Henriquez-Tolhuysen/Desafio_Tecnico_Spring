package cl.tolhuysen.henriquez.jorge.desafiotecnicospring.repositories;

import cl.tolhuysen.henriquez.jorge.desafiotecnicospring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserDataRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE UPPER(u.userEmail) = UPPER(:email)")
    public Optional<User> findbyEmail(@Param("email") String email);

    @Query("SELECT u FROM User u WHERE u.userIsborrower = 'S'")
    public Iterable<User> findAllBorrowers();
}
