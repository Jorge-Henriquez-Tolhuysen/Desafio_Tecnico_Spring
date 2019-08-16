package cl.tolhuysen.henriquez.jorge.desafiotecnicospring.controller;

import cl.tolhuysen.henriquez.jorge.desafiotecnicospring.model.FormData.LoginFormData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class Logout {

    @Autowired
    private LoginFormData formData;

    public Logout() {
    }

    @Autowired
    public Logout(LoginFormData formData) {
        this.formData = formData;
    }

    @RequestMapping({"/logout", "/Logout"})
    public String showLogout(Model model, HttpSession session) {
        session.invalidate();
        model.addAttribute("sessionId", session.getId());
        model.addAttribute("data", formData);
        return "Login";
        //return "redirect:/Login";
    }
}
