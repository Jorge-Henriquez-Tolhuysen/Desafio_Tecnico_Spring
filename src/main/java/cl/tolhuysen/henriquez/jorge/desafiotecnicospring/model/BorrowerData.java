package cl.tolhuysen.henriquez.jorge.desafiotecnicospring.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="BORROWER_DATA")
public class BorrowerData implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="BORROWER_ID")
    private long borrowerId;

    @Column(name="BORROWER_NEEDMONEYFOR")
    private String borrowerNeedmoneyfor;

    @Column(name="BORROWER_DESCRIPTION")
    private String borrowerDescription;

    @Column(name="BORROWER_AMOUNTNEEDED")
    private BigDecimal borrowerAmountneeded;

    //bi-directional one-to-one association to Usuario
    @OneToOne
    @JoinColumn(name="BORROWER_ID")
    private User user;

    //bi-directional many-to-one association to Ledger
    @OneToMany(mappedBy="borrowerData")
    private List<Ledger> ledgers;

    public BorrowerData() {
    }

    public BorrowerData(long borrowerId,
                        String borrowerNeedmoneyfor,
                        String borrowerDescription,
                        BigDecimal borrowerAmountneeded) {
        this.borrowerId = borrowerId;
        this.borrowerNeedmoneyfor = borrowerNeedmoneyfor;
        this.borrowerDescription = borrowerDescription;
        this.borrowerAmountneeded = borrowerAmountneeded;
    }

    public long getBorrowerId() {
        return this.borrowerId;
    }

    public void setBorrowerId(long borrowerId) {
        this.borrowerId = borrowerId;
    }

    public BigDecimal getBorrowerAmountneeded() {
        return this.borrowerAmountneeded;
    }

    public void setBorrowerAmountneeded(BigDecimal borrowerAmountneeded) {
        this.borrowerAmountneeded = borrowerAmountneeded;
    }

    public String getBorrowerDescription() {
        return this.borrowerDescription;
    }

    public void setBorrowerDescription(String borrowerDescription) {
        this.borrowerDescription = borrowerDescription;
    }

    public String getBorrowerNeedmoneyfor() {
        return this.borrowerNeedmoneyfor;
    }

    public void setBorrowerNeedmoneyfor(String borrowerNeedmoneyfor) {
        this.borrowerNeedmoneyfor = borrowerNeedmoneyfor;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Ledger> getLedgers() {
        return this.ledgers;
    }

    public void setLedgers(List<Ledger> ledgers) {
        this.ledgers = ledgers;
    }

    public Ledger addLedger(Ledger ledger) {
        getLedgers().add(ledger);
        ledger.setBorrowerData(this);

        return ledger;
    }

    public Ledger removeLedger(Ledger ledger) {
        getLedgers().remove(ledger);
        ledger.setBorrowerData(null);

        return ledger;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BorrowerData that = (BorrowerData) o;
        return borrowerId == that.borrowerId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(borrowerId);
    }
}
