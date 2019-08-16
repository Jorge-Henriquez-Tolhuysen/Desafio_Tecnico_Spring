package cl.tolhuysen.henriquez.jorge.desafiotecnicospring.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class Ledger implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="LEDGER_ID")
    private long ledgerId;

    //bi-directional many-to-one association to LenderData
    @ManyToOne
    @JoinColumn(name="LENDER_ID")
    private LenderData lenderData;

    //bi-directional many-to-one association to BorrowerData
    @ManyToOne
    @JoinColumn(name="BORROWER_ID")
    private BorrowerData borrowerData;

    @Column(name="MONEY")
    private BigDecimal money;

    public Ledger() {
    }

    public Ledger(LenderData lenderData, BorrowerData borrowerData, BigDecimal money) {
        this.lenderData = lenderData;
        this.borrowerData = borrowerData;
        this.money = money;
    }

    public long getLedgerId() {
        return this.ledgerId;
    }

    public void setLedgerId(long ledgerId) {
        this.ledgerId = ledgerId;
    }

    public BigDecimal getMoney() {
        return this.money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public LenderData getLenderData() {
        return this.lenderData;
    }

    public void setLenderData(LenderData lenderData) {
        this.lenderData = lenderData;
    }

    public BorrowerData getBorrowerData() {
        return this.borrowerData;
    }

    public void setBorrowerData(BorrowerData borrowerData) {
        this.borrowerData = borrowerData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ledger ledger = (Ledger) o;
        return ledgerId == ledger.ledgerId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ledgerId);
    }
}
