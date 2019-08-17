package cl.tolhuysen.henriquez.jorge.desafiotecnicospring.controller;

import cl.tolhuysen.henriquez.jorge.desafiotecnicospring.model.BorrowerData;
import cl.tolhuysen.henriquez.jorge.desafiotecnicospring.model.FormData.BorrowerFormData;
import cl.tolhuysen.henriquez.jorge.desafiotecnicospring.model.FormData.LenderFormData;
import cl.tolhuysen.henriquez.jorge.desafiotecnicospring.model.LenderData;
import cl.tolhuysen.henriquez.jorge.desafiotecnicospring.model.User;
import cl.tolhuysen.henriquez.jorge.desafiotecnicospring.repositories.BorrowerDataRepository;
import cl.tolhuysen.henriquez.jorge.desafiotecnicospring.repositories.LenderDataRepository;
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
import java.math.BigDecimal;
import java.util.Optional;

@Controller
public class Register {

    @Autowired
    private LenderFormData lenderFormData;

    @Autowired
    private BorrowerFormData borrowerFormData;

    @Autowired
    private UserDataRepository userDataRepository;

    @Autowired
    private LenderDataRepository lenderDataRepository;

    @Autowired
    private BorrowerDataRepository borrowerDataRepository;

    public Register() {
    }

    public Register(LenderFormData lenderFormData,
                    BorrowerFormData borrowerFormdata,
                    UserDataRepository userDataRepository,
                    LenderDataRepository lenderDataRepository,
                    BorrowerDataRepository borrowerDataRepository) {
        this.lenderFormData = lenderFormData;
        this.borrowerFormData = borrowerFormdata;
        this.userDataRepository = userDataRepository;
        this.lenderDataRepository = lenderDataRepository;
        this.borrowerDataRepository = borrowerDataRepository;
    }


    @GetMapping({"/register","/Register"})
    public String showRegister(Model model, HttpSession session) {
        model.addAttribute("sessionId", session.getId());
        if (session.getAttribute("userId")!=null) {
            if (session.getAttribute("userEmail")!=null) {
                if (session.getAttribute("userPassword")!=null) {
                    Optional<User> user = userDataRepository.findbyEmail(session.getAttribute("userEmail").toString());
                    if (user.isPresent()) {
                        if (user.get().getUserPassword().equals(session.getAttribute("userPassword"))) {
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
        model.addAttribute("lenderData", lenderFormData);
        model.addAttribute("borrowerData", borrowerFormData);
        return "Register";
    }

    @GetMapping({"/registerLender","/Registerlender", "/registerlender","/RegisterLender"})
    public String registerLenderByGet() {
        return "redirect:/Register";
    }

    @GetMapping({"/registerBorrower","/Registerborrower", "/registerborrower","/RegisterBorrower"})
    public String registerBorrowerByGet() {
        return "redirect:/Register";
    }

    @PostMapping({"/registerLender","/Registerlender", "/registerlender","/RegisterLender"})
    public String registerLender(@Valid @ModelAttribute("lenderData") LenderFormData data, BindingResult bindingResult, Model model, HttpServletRequest request) {
        model.addAttribute("sessionId", request.getSession().getId());
        if (request.getSession().getAttribute("userId")!=null) {
            if (request.getSession().getAttribute("userEmail")!=null) {
                if (request.getSession().getAttribute("userPassword")!=null) {
                    Optional<User> user = userDataRepository.findbyEmail(request.getSession().getAttribute("userEmail").toString());
                    if (user.isPresent()) {
                        if (user.get().getUserPassword().equals(request.getSession().getAttribute("userPassword"))) {
                            if (user.get().getUserIslender().equals("S")) {
                                return String.format("redirect:/Lender/%d",user.get().getUserId());
                            }
                        }
                    }
                }
            }
        }
        if (bindingResult.hasErrors()) {
            //lenderData no es necesario, pues viene inyectado en el modelo a travez del formulario que hizo post
            model.addAttribute("borrowerData", borrowerFormData);
            return "Register";
        }
        Optional<User> user = userDataRepository.findbyEmail(data.geteMail());
        if (user.isPresent()) {
            //Si esta presente ... estamos intentando duplicar ... es un error
            model.addAttribute("registerLenderError", true);
            //lenderData no es necesario, pues viene inyectado en el modelo a travez del formulario que hizo post
            model.addAttribute("borrowerData", borrowerFormData);
            return "Register";
        }
        User newUser = userDataRepository.save(new User(data.getFirstName(), data.getLastName(), data.geteMail(), data.getPassWord(), "S","N"));
        LenderData lenderData = lenderDataRepository.save(new LenderData(newUser.getUserId(), new BigDecimal(data.getMoney())));
        request.getSession().setAttribute("userId", newUser.getUserId());
        request.getSession().setAttribute("userEmail", newUser.getUserEmail());
        request.getSession().setAttribute("userPassword", newUser.getUserPassword());
        return String.format("redirect:/Lender/%d",newUser.getUserId());
    }

    @PostMapping({"/registerBorrower","/Registerborrower", "/registerborrower","/RegisterBorrower"})
    public String registerBorrower(@Valid @ModelAttribute("borrowerData") BorrowerFormData data, BindingResult bindingResult, Model model, HttpServletRequest request) {
        model.addAttribute("sessionId", request.getSession().getId());
        if (request.getSession().getAttribute("userId")!=null) {
            if (request.getSession().getAttribute("userEmail")!=null) {
                if (request.getSession().getAttribute("userPassword")!=null) {
                    Optional<User> user = userDataRepository.findbyEmail(request.getSession().getAttribute("userEmail").toString());
                    if (user.isPresent()) {
                        if (user.get().getUserPassword().equals(request.getSession().getAttribute("userPassword"))) {
                            if (user.get().getUserIsborrower().equals("S")) {
                                return String.format("redirect:/Borrower/%d",user.get().getUserId());
                            }
                        }
                    }
                }
            }
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("lenderData", lenderFormData);
            //borrowerData no es necesario, pues viene inyectado en el modelo a travez del formulario que hizo post
            return "Register";
        }
        Optional<User> user = userDataRepository.findbyEmail(data.geteMail());
        if (user.isPresent()) {
            //Si esta presente ... estamos intentando duplicar ... es un error
            model.addAttribute("registerBorrowerError", true);
            model.addAttribute("lenderData", lenderFormData);
            //borrowerData no es necesario, pues viene inyectado en el modelo a travez del formulario que hizo post
            return "Register";
        }
        User newUser = userDataRepository.save(new User(data.getFirstName(), data.getLastName(), data.geteMail(), data.getPassWord(), "N","S"));
        BorrowerData borrowerData = borrowerDataRepository.save(new BorrowerData(newUser.getUserId(),data.getNeedMoneyFor(), data.getDescription(), new BigDecimal(data.getAmountNeeded())));
        request.getSession().setAttribute("userId", newUser.getUserId());
        request.getSession().setAttribute("userEmail", newUser.getUserEmail());
        request.getSession().setAttribute("userPassword", newUser.getUserPassword());
        return String.format("redirect:/Borrower/%d",newUser.getUserId());
    }


}
