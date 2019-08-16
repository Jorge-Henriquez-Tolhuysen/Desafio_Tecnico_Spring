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
                    User user = userDataRepository.findbyEmail(session.getAttribute("userEmail").toString());
                    if (user!=null) {
                        if (user.getUserPassword().equals(session.getAttribute("userPassword"))) {
                            if (user.getUserIsborrower().equals("S")) {
                                return String.format("redirect:/Borrower/%d",user.getUserId());
                            }
                            if (user.getUserIslender().equals("S")) {
                                return String.format("redirect:/Lender/%d",user.getUserId());
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
        User user = userDataRepository.findbyEmail(data.getEmail());
        if (user==null) {
            model.addAttribute("loginEmailPasswordError", true);
            return "Login";
        }
        if (!user.getUserPassword().equals(data.getPassWord())) {
            model.addAttribute("loginEmailPasswordError", true);
            return "Login";
        }
        if (user.getUserIsborrower().equals("S")) {
            request.getSession().setAttribute("userId", user.getUserId());
            request.getSession().setAttribute("userEmail", data.getEmail());
            request.getSession().setAttribute("userPassword", data.getPassWord());
            return String.format("redirect:/Borrower/%d",user.getUserId());
        }
        if (user.getUserIslender().equals("S")) {
            request.getSession().setAttribute("userId", user.getUserId());
            request.getSession().setAttribute("userEmail", data.getEmail());
            request.getSession().setAttribute("userPassword", data.getPassWord());
            return String.format("redirect:/Lender/%d",user.getUserId());
        }
        return "Login";
    }
}
