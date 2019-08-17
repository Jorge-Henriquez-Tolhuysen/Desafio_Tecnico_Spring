package cl.tolhuysen.henriquez.jorge.desafiotecnicospring.controller;

import cl.tolhuysen.henriquez.jorge.desafiotecnicospring.model.FormData.LoginFormData;
import cl.tolhuysen.henriquez.jorge.desafiotecnicospring.model.User;
import cl.tolhuysen.henriquez.jorge.desafiotecnicospring.repositories.UserDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

@Controller
public class Login {

    @Autowired
    private LoginFormData formData;

    @Autowired
    private UserDataRepository userDataRepository;


    public Login() {
    }

    public Login(LoginFormData formData, UserDataRepository userDataRepository) {
        this.formData = formData;
        this.userDataRepository = userDataRepository;
    }

    @GetMapping({"", "/", "/login", "/Login"})
    public String showLogin(Model model, HttpSession session) {
        model.addAttribute("sessionId", session.getId());
        if (session.getAttribute("userId")!=null) {
            if (session.getAttribute("userEmail")!=null) {
                if (session.getAttribute("userPassword")!=null) {
                    Optional<User> user = userDataRepository.findbyEmail(session.getAttribute("userEmail").toString());
                    if (user.isPresent()) {
                        if (user.get().getUserPassword().equals(session.getAttribute("userPassword").toString())) {
                            if (user.get().getUserIsborrower().equals("S")) {
                                return String.format("redirect:/Borrower/%d",user.get().getUserId());
                            }
                            if (user.get().getUserIslender().equals("S")) {
                                return String.format("redirect:/Lender/%d",user.get().getUserId());
                            }
                        }
                    }
                }
            }
        }
        model.addAttribute("data", formData);
        return "Login";
    }

    @PostMapping({"", "/", "/login", "/Login"})
    public String doLogin(@Valid @ModelAttribute("data") LoginFormData data, BindingResult bindingResult, Model model, HttpServletRequest request) {
        model.addAttribute("sessionId", request.getSession().getId());
        if (bindingResult.hasErrors()) {
            return "Login";
        }
        Optional<User> user = userDataRepository.findbyEmail(data.getEmail());
        if (!user.isPresent()) {
            model.addAttribute("loginEmailPasswordError", true);
            return "Login";
        }
        if (!user.get().getUserPassword().equals(data.getPassWord())) {
            model.addAttribute("loginEmailPasswordError", true);
            return "Login";
        }
        if (user.get().getUserIsborrower().equals("S")) {
            request.getSession().setAttribute("userId", user.get().getUserId());
            request.getSession().setAttribute("userEmail", data.getEmail());
            request.getSession().setAttribute("userPassword", data.getPassWord());
            return String.format("redirect:/Borrower/%d",user.get().getUserId());
        }
        if (user.get().getUserIslender().equals("S")) {
            request.getSession().setAttribute("userId", user.get().getUserId());
            request.getSession().setAttribute("userEmail", data.getEmail());
            request.getSession().setAttribute("userPassword", data.getPassWord());
            return String.format("redirect:/Lender/%d",user.get().getUserId());
        }
        System.out.println(6);
        return "Login";
    }
}
