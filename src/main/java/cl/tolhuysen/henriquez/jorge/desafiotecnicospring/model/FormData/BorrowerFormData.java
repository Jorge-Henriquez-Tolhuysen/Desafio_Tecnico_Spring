package cl.tolhuysen.henriquez.jorge.desafiotecnicospring.model.FormData;

import org.springframework.stereotype.Component;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Component
public class BorrowerFormData {

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

    @NotNull(message="Error in NEED MONEY FOR: it can not be empty")
    @NotBlank(message="Error in NEED MONEY FOR: it can not be blank")
    private String needMoneyFor;

    @NotNull(message="Error in DESCRIPTION: it can not be empty")
    @NotBlank(message="Error in DESCRIPTION: it can not be blank")
    private String description;

    @NotNull(message="Error in AMOUNT NEEDED: it can not be empty")
    private Long   amountNeeded;

    public BorrowerFormData() {
    }

    public BorrowerFormData(String firstName, String lastName, String eMail, String passWord, String needMoneyFor, String description, Long amountNeeded) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.eMail = eMail;
        this.passWord = passWord;
        this.needMoneyFor = needMoneyFor;
        this.description = description;
        this.amountNeeded = amountNeeded;
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

    public String getNeedMoneyFor() {
        return needMoneyFor;
    }

    public void setNeedMoneyFor(String needMoneyFor) {
        this.needMoneyFor = needMoneyFor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getAmountNeeded() {
        return amountNeeded;
    }

    public void setAmountNeeded(Long amountNeeded) {
        this.amountNeeded = amountNeeded;
    }
}
