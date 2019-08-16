package cl.tolhuysen.henriquez.jorge.desafiotecnicospring.model.FormData;

import org.springframework.stereotype.Component;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Component
public class LenderFormData {

    @NotNull(message="Error in FIRST NAME: it can not be empty")
    @NotBlank(message="Error in FIRST NAME: it can not be blank")
    private String firstName;

    @NotNull(message="Error in LAST NAME: it can not be empty")
    @NotBlank(message="Error in LAST NAME: it can not be blank")
    private String lastName;

    @NotNull(message="Error in EMAIL: it can not be empty")
    @NotBlank(message="Error in EMAIL: it can not be blank")
    @Email(message="Error in EMAIL: no appropiate email structure")
    private String eMail;

    @NotNull(message="Error in PASSWORD: it can not be empty")
    @NotBlank(message="Error in PASSWORD: it can not be blank")
    private String passWord;

    @NotNull(message="Error in MONEY: it can not be empty")
    private Long   money;

    public LenderFormData() {
    }

    public LenderFormData(String firstName, String lastName, String eMail, String passWord, Long money) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.eMail = eMail;
        this.passWord = passWord;
        this.money = money;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public Long getMoney() {
        return money;
    }

    public void setMoney(Long money) {
        this.money = money;
    }
}
