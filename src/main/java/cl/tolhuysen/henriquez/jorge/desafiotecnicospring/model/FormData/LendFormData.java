package cl.tolhuysen.henriquez.jorge.desafiotecnicospring.model.FormData;

import org.springframework.stereotype.Component;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Component
public class LendFormData {
    @NotNull
    private Long lenderId;
    @NotNull
    private Long borrowerId;
    @NotNull
    @Min(1)
    private Long money;

    public LendFormData() {
    }

    public LendFormData(Long lenderId, Long borrowerId, Long money) {
        this.lenderId = lenderId;
        this.borrowerId = borrowerId;
        this.money = money;
    }

    public Long getLenderId() {
        return lenderId;
    }

    public void setLenderId(Long lenderId) {
        this.lenderId = lenderId;
    }

    public Long getBorrowerId() {
        return borrowerId;
    }

    public void setBorrowerId(Long borrowerId) {
        this.borrowerId = borrowerId;
    }

    public Long getMoney() {
        return money;
    }

    public void setMoney(Long money) {
        this.money = money;
    }
}
