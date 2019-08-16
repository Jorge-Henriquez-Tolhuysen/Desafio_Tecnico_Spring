package cl.tolhuysen.henriquez.jorge.desafiotecnicospring.model.FormData;

import org.springframework.stereotype.Component;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Component
public class LoginFormData {

    @NotNull(message="Error in EMAIL: it can not be empty")
    @NotBlank(message="Error in EMAIL: it can not be blank")
    @Email(message="Error in EMAIL: no appropiate email structure")
    private String email;

    @NotNull(message="Error in PASSWORD: it can not be empty")
    @NotBlank(message="Error in PASSWORD: it can not be blank")
    private String passWord;

    public LoginFormData() {
    }

    public LoginFormData(String email, String passWord) {
        this.email = email;
        this.passWord = passWord;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
