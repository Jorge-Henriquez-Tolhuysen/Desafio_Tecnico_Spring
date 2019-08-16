package cl.tolhuysen.henriquez.jorge.desafiotecnicospring.repositories;

import cl.tolhuysen.henriquez.jorge.desafiotecnicospring.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserDataRepository extends CrudRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE UPPER(u.userEmail) = UPPER(:email)")
    public User findbyEmail(@Param("email") String email);

    @Query("SELECT u FROM User u WHERE u.userIsborrower = 'S'")
    public Iterable<User> findAllBorrowers();
}
