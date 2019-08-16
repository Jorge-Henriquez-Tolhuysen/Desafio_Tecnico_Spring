package cl.tolhuysen.henriquez.jorge.desafiotecnicospring.controller;

import cl.tolhuysen.henriquez.jorge.desafiotecnicospring.model.BorrowerData;
import cl.tolhuysen.henriquez.jorge.desafiotecnicospring.model.Ledger;
import cl.tolhuysen.henriquez.jorge.desafiotecnicospring.model.User;
import cl.tolhuysen.henriquez.jorge.desafiotecnicospring.repositories.BorrowerDataRepository;
import cl.tolhuysen.henriquez.jorge.desafiotecnicospring.repositories.LedgerRepository;
import cl.tolhuysen.henriquez.jorge.desafiotecnicospring.repositories.LenderDataRepository;
import cl.tolhuysen.henriquez.jorge.desafiotecnicospring.repositories.UserDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping({"/borrower", "/Borrower"})
public class Borrower {

    @Autowired
    private UserDataRepository userDataRepository;

    @Autowired
    private BorrowerDataRepository borrowerDataRepository;

    @Autowired
    private LedgerRepository ledgerDataRepository;

    public Borrower() {
    }

    public Borrower(UserDataRepository userDataRepository,
                    BorrowerDataRepository borrowerDataRepository,
                    LedgerRepository ledgerDataRepository) {
        this.userDataRepository = userDataRepository;
        this.borrowerDataRepository = borrowerDataRepository;
        this.ledgerDataRepository = ledgerDataRepository;
    }

    @GetMapping("/{borrowerId}")
    @PostMapping("/{borrowerId}")
    public String showBorrower(@PathVariable("borrowerId") String borrowerId, Model model, HttpSession session, HttpServletRequest request) {
        model.addAttribute("sessionId", session.getId());
        Optional<User> user = userDataRepository.findById(Long.parseLong(borrowerId));
        if (!user.isPresent()) return "redirect:/Login";
        if (!user.get().getUserIsborrower().equals("S")) return "redirect:/Login";
        Optional<BorrowerData> borrowerData = borrowerDataRepository.findById(Long.parseLong(borrowerId));
        model.addAttribute("name", String.format("%s %s", user.get().getUserFirstname(), user.get().getUserLastname()));
        if (!borrowerData.isPresent()) return "redirect:/Login";
        model.addAttribute("amountNeeded", borrowerData.get().getBorrowerAmountneeded());
        Optional<BigDecimal> amountRaised = ledgerDataRepository.summAllByBorrowerId(user.get().getUserId());
        if (amountRaised.isPresent()) {
            model.addAttribute("amountRaised", amountRaised.get().doubleValue());
        }
        else {
            model.addAttribute("amountRaised", 0);
        }
        ArrayList<UserLentData> lentsData = new ArrayList<>();

        List<Object[]> l = ledgerDataRepository.summAllDistinctByBorrowerId(Long.parseLong(borrowerId));
        for(Object[] lent: l) {
            UserLentData userLent = new UserLentData();
            Optional<User> lender = userDataRepository.findById(Long.parseLong(lent[1].toString()));
            if (lender.isPresent()) {
                userLent.name = String.format("%s %s",lender.get().getUserFirstname(), lender.get().getUserLastname());
                userLent.email = lender.get().getUserEmail();
                userLent.money = BigDecimal.valueOf(Double.parseDouble(lent[0].toString()));
            }
            lentsData.add(userLent);
        }

        model.addAttribute("lents", lentsData);
        return "Borrower";
    }

    class UserLentData {
        public String name = "";
        public String email = "";
        public BigDecimal money = BigDecimal.valueOf(0L);
    }

}
