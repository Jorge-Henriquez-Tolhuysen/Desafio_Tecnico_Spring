package cl.tolhuysen.henriquez.jorge.desafiotecnicospring.bootstrap;

import cl.tolhuysen.henriquez.jorge.desafiotecnicospring.model.BorrowerData;
import cl.tolhuysen.henriquez.jorge.desafiotecnicospring.model.Ledger;
import cl.tolhuysen.henriquez.jorge.desafiotecnicospring.model.LenderData;
import cl.tolhuysen.henriquez.jorge.desafiotecnicospring.model.User;
import cl.tolhuysen.henriquez.jorge.desafiotecnicospring.repositories.BorrowerDataRepository;
import cl.tolhuysen.henriquez.jorge.desafiotecnicospring.repositories.LedgerRepository;
import cl.tolhuysen.henriquez.jorge.desafiotecnicospring.repositories.LenderDataRepository;
import cl.tolhuysen.henriquez.jorge.desafiotecnicospring.repositories.UserDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class DevBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private UserDataRepository userDataRepository;

    @Autowired
    private LenderDataRepository lenderDataRepository;

    @Autowired
    private BorrowerDataRepository borrowerDataRepository;

    @Autowired
    private LedgerRepository ledgerRepository;

    public DevBootstrap(UserDataRepository userDataRepository,
                        LenderDataRepository lenderDataRepository,
                        BorrowerDataRepository borrowerDataRepository,
                        LedgerRepository ledgerRepository) {
        this.userDataRepository = userDataRepository;
        this.lenderDataRepository = lenderDataRepository;
        this.borrowerDataRepository = borrowerDataRepository;
        this.ledgerRepository = ledgerRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
         initData();
    }

    private void initData() {
          //Load Sample data to begin with
          /*
          //No funciona desde que integre el manejo de sessiones ... UFFFFF!!!!!!!!!
          //Y continuo sucediendo despues de integrar MySQL, que fue pero pues no creaba las tablas de manejo de sesiones.
          User jorge = userDataRepository.save(new User("Jorge", "Henriquez", "jorge@mail.com", "123456", "S","N"));
          User myriam = userDataRepository.save(new User("Myriam", "Tolhuijsen", "myriam@mail.com", "clave", "S","N"));
          User lorena = userDataRepository.save(new User("Lorena", "Perez", "lorena@mail.com", "7891011", "N","S"));
          User derek = userDataRepository.save(new User("Derek", "mena", "derek@mail.com", "password", "N","S"));
          LenderData lenderJorge = lenderDataRepository.save(new LenderData(jorge.getUserId(), BigDecimal.valueOf(2000)));
          LenderData lenderMyriam = lenderDataRepository.save(new LenderData(myriam.getUserId(), BigDecimal.valueOf(3000)));
          BorrowerData borrowerLorena = borrowerDataRepository.save(new BorrowerData(lorena.getUserId(), "Arreglo de Automovil", "El vehiculo presenta varias fallas", BigDecimal.valueOf(3000)));
          BorrowerData borrowerDerek = borrowerDataRepository.save(new BorrowerData(derek.getUserId(), "Compra de PC", "Esta muy viejo mi computador", BigDecimal.valueOf(1000)));
          ledgerRepository.save(new Ledger(lenderJorge, borrowerLorena, BigDecimal.valueOf(800)));
          ledgerRepository.save(new Ledger(lenderMyriam, borrowerDerek, BigDecimal.valueOf(500)));
          */
    }
}
