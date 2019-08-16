package cl.tolhuysen.henriquez.jorge.desafiotecnicospring.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="LENDER_DATA")
public class LenderData implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="LENDER_ID")
    private long lenderId;

    @Column(name="LENDER_MONEY")
    private BigDecimal lenderMoney;

    //bi-directional many-to-one association to Ledger
    @OneToMany(mappedBy="lenderData")
    private List<Ledger> ledgers;

    //bi-directional one-to-one association to Usuario
    @OneToOne
    @JoinColumn(name="LENDER_ID")
    private User user;

    public LenderData() {
    }

    public LenderData(long lenderId, BigDecimal lenderMoney) {
        this.lenderId = lenderId;
        this.lenderMoney = lenderMoney;
    }

    public long getLenderId() {
        return this.lenderId;
    }

    public void setLenderId(long lenderId) {
        this.lenderId = lenderId;
    }

    public BigDecimal getLenderMoney() {
        return this.lenderMoney;
    }

    public void setLenderMoney(BigDecimal lenderMoney) {
        this.lenderMoney = lenderMoney;
    }

    public List<Ledger> getLedgers() {
        return this.ledgers;
    }

    public void setLedgers(List<Ledger> ledgers) {
        this.ledgers = ledgers;
    }

    public Ledger addLedger(Ledger ledger) {
        getLedgers().add(ledger);
        ledger.setLenderData(this);

        return ledger;
    }

    public Ledger removeLedger(Ledger ledger) {
        getLedgers().remove(ledger);
        ledger.setLenderData(null);

        return ledger;
    }

    public User getUsuario() {
        return this.user;
    }

    public void setUsuario(User usuario) {
        this.user = usuario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LenderData that = (LenderData) o;
        return lenderId == that.lenderId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(lenderId);
    }
}
