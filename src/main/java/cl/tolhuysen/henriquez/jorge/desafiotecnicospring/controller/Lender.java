package cl.tolhuysen.henriquez.jorge.desafiotecnicospring.controller;

import cl.tolhuysen.henriquez.jorge.desafiotecnicospring.model.BorrowerData;
import cl.tolhuysen.henriquez.jorge.desafiotecnicospring.model.FormData.LendFormData;
import cl.tolhuysen.henriquez.jorge.desafiotecnicospring.model.Ledger;
import cl.tolhuysen.henriquez.jorge.desafiotecnicospring.model.LenderData;
import cl.tolhuysen.henriquez.jorge.desafiotecnicospring.model.User;
import cl.tolhuysen.henriquez.jorge.desafiotecnicospring.repositories.BorrowerDataRepository;
import cl.tolhuysen.henriquez.jorge.desafiotecnicospring.repositories.LedgerRepository;
import cl.tolhuysen.henriquez.jorge.desafiotecnicospring.repositories.LenderDataRepository;
import cl.tolhuysen.henriquez.jorge.desafiotecnicospring.repositories.UserDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

@Controller
@RequestMapping({"/lender","/Lender"})
public class Lender {

    @Autowired
    private UserDataRepository userDataRepository;

    @Autowired
    private BorrowerDataRepository borrowerDataRepository;

    @Autowired
    private LenderDataRepository lenderDataRepository;

    @Autowired
    private LedgerRepository ledgerDataRepository;

    public Lender() {
    }

    public Lender(UserDataRepository userDataRepository,
                    LenderDataRepository lenderDataRepository,
                    BorrowerDataRepository borrowerDataRepository,
                    LedgerRepository ledgerDataRepository) {
        this.userDataRepository = userDataRepository;
        this.lenderDataRepository = lenderDataRepository;
        this.borrowerDataRepository = borrowerDataRepository;
        this.ledgerDataRepository = ledgerDataRepository;
    }


    @GetMapping("/{lenderId}")
    @PostMapping("/{lenderId}")
    public String showLender(@PathVariable("lenderId") String lenderId, Model model, HttpSession session, HttpServletRequest request) {
        model.addAttribute("sessionId", session.getId());
        Optional<User> user = userDataRepository.findById(Long.parseLong(lenderId));
        if (!user.isPresent()) return "redirect:/Login";
        if (!user.get().getUserIslender().equals("S")) return "redirect:/Login";
        Optional<LenderData> lenderData = lenderDataRepository.findById(Long.parseLong(lenderId));
        model.addAttribute("lenderId", lenderId);
        model.addAttribute("name", String.format("%s %s", user.get().getUserFirstname(), user.get().getUserLastname()));
        if (!lenderData.isPresent()) return "redirect:/Login";
        BigDecimal initialBalance = lenderData.get().getLenderMoney();
        BigDecimal totalAmountLent = BigDecimal.valueOf(0L);
        Optional<BigDecimal> amountLent = ledgerDataRepository.summAllByLenderId(Long.parseLong(lenderId));
        if (amountLent.isPresent()) totalAmountLent = new BigDecimal(amountLent.get().doubleValue());
        BigDecimal accountBalance = initialBalance.subtract(totalAmountLent);
        model.addAttribute("accountBalance", accountBalance );
        ArrayList<UserInNeedData> peopleInNeed = new ArrayList<>();
        for(User userInNeed: userDataRepository.findAllBorrowers()) {
            String name = String.format("%s %s",userInNeed.getUserFirstname(), userInNeed.getUserLastname());
            String needMoneyFor = "";
            String description = "";
            BigDecimal amountNeeded = BigDecimal.valueOf(0L);
            BigDecimal amountRaised  = BigDecimal.valueOf(0L);
            Optional<BorrowerData> borrowerData = borrowerDataRepository.findById(userInNeed.getUserId());
            if (borrowerData.isPresent()) {
                needMoneyFor = borrowerData.get().getBorrowerNeedmoneyfor();
                description = borrowerData.get().getBorrowerDescription();
                amountNeeded = borrowerData.get().getBorrowerAmountneeded();
            }
            Optional<BigDecimal> optAmountRaised = ledgerDataRepository.summAllByBorrowerId(userInNeed.getUserId());
            if (optAmountRaised.isPresent()) amountRaised = BigDecimal.valueOf(optAmountRaised.get().doubleValue());
            BigDecimal amountToRequest  = amountNeeded.subtract(amountRaised);
            BigDecimal maxAmountToLend = accountBalance.compareTo(BigDecimal.valueOf(0L))>0?
                    (amountToRequest.compareTo(accountBalance)>0?accountBalance:amountToRequest)
                    :BigDecimal.valueOf(0L);
            peopleInNeed.add(new UserInNeedData(userInNeed.getUserId(), name, needMoneyFor, description, amountNeeded, amountRaised, maxAmountToLend));
        }

        ArrayList<UserLentData> peopleILentMoney = new ArrayList<>();
        for(Object[] lent: ledgerDataRepository.summAllDistinctByLenderId(Long.parseLong(lenderId))) {
            BigDecimal lentAmountLent = BigDecimal.valueOf(Double.parseDouble(lent[0].toString()));
            Long lentBorrowerId = Long.parseLong(lent[1].toString());
            String lentName = "";
            String lentMoneyNeededFor = "";
            String lentDescription = "";
            BigDecimal lentAmountNeeded = BigDecimal.valueOf(0L);
            BigDecimal lentAmountRaised = BigDecimal.valueOf(0L);
            Optional<User> borrower = userDataRepository.findById(lentBorrowerId);
            if (borrower.isPresent()) {
                lentName = String.format("%s %s", borrower.get().getUserFirstname(), borrower.get().getUserLastname());
                Optional<BorrowerData> borrowerData = borrowerDataRepository.findById(borrower.get().getUserId());
                if (borrowerData.isPresent()) {
                    lentMoneyNeededFor = borrowerData.get().getBorrowerNeedmoneyfor();
                    lentDescription = borrowerData.get().getBorrowerDescription();
                    lentAmountNeeded = borrowerData.get().getBorrowerAmountneeded();
                    Optional<BigDecimal> optAmountRaised = ledgerDataRepository.summAllByBorrowerId(borrower.get().getUserId());
                    if (optAmountRaised.isPresent()) lentAmountRaised = new BigDecimal(optAmountRaised.get().doubleValue());
                }
            }
            peopleILentMoney.add(new UserLentData(lentName, lentMoneyNeededFor, lentDescription, lentAmountNeeded, lentAmountRaised, lentAmountLent));
        }

        model.addAttribute("usersInNeed", peopleInNeed);
        model.addAttribute("usersLent", peopleILentMoney);
        return "Lender";
    }

    @PostMapping("/lend")
    public String lenderLend(@Valid @ModelAttribute("data") LendFormData data, BindingResult bindingResult, RedirectAttributes redirAttr, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "redirect:/Login";
        }
        Optional<User> opLender   = userDataRepository.findById(data.getLenderId());
        Optional<User> opBorrower = userDataRepository.findById(data.getBorrowerId());
        if (!opLender.isPresent()||!opBorrower.isPresent()) {
            return "redirect:/Login";
        }
        if (!opLender.get().getUserIslender().equals("S")) return "redirect:/Login";
        if (!opBorrower.get().getUserIsborrower().equals("S")) return "redirect:/Login";
        Optional<LenderData> opLenderData = lenderDataRepository.findById(opLender.get().getUserId());
        Optional<BorrowerData> opBorrowerData = borrowerDataRepository.findById(opBorrower.get().getUserId());
        if (!opLenderData.isPresent()||!opBorrowerData.isPresent()) {
            return "redirect:/Login";
        }
        BigDecimal initialBalance = opLenderData.get().getLenderMoney();
        BigDecimal totalAmountLent = BigDecimal.valueOf(0L);
        Optional<BigDecimal> amountLent = ledgerDataRepository.summAllByLenderId(data.getLenderId());
        if (amountLent.isPresent()) totalAmountLent = new BigDecimal(amountLent.get().doubleValue());
        BigDecimal accountBalance = initialBalance.subtract(totalAmountLent);
        if (accountBalance.compareTo(BigDecimal.valueOf(data.getMoney()))>=0) {
            ledgerDataRepository.save(new Ledger(opLenderData.get(), opBorrowerData.get(), BigDecimal.valueOf(data.getMoney())));
        }
        else {
            redirAttr.addFlashAttribute("message", "Insufficient funds");
        }
        return String.format("redirect:/Lender/%s", data.getLenderId());
    }


    class UserInNeedData {
        public Long id = 0L;
        public String name = "";
        public String moneyNeededFor = "";
        public String description = "";
        public BigDecimal amountNeeded = BigDecimal.valueOf(0L);
        public BigDecimal amountRaised = BigDecimal.valueOf(0L);
        public BigDecimal maxMoneyToLend = BigDecimal.valueOf(0L);

        public UserInNeedData() {

        }

        public UserInNeedData(Long id,String name, String moneyNeededFor, String description, BigDecimal amountNeeded, BigDecimal amountRaised, BigDecimal maxMoneyToLend) {
            this.id = id;
            this.name = name;
            this.moneyNeededFor = moneyNeededFor;
            this.description = description;
            this.amountNeeded = amountNeeded;
            this.amountRaised = amountRaised;
            this.maxMoneyToLend = maxMoneyToLend;
        }
    }

    class UserLentData {
        public String name = "";
        public String moneyNeededFor = "";
        public String description = "";
        public BigDecimal amountNeeded = BigDecimal.valueOf(0L);
        public BigDecimal amountRaised = BigDecimal.valueOf(0L);
        public BigDecimal amountLent = BigDecimal.valueOf(0L);

        public UserLentData() {
        }

        public UserLentData(String name, String moneyNeededFor, String description, BigDecimal amountNeeded, BigDecimal amountRaised, BigDecimal amountLent) {
            this.name = name;
            this.moneyNeededFor = moneyNeededFor;
            this.description = description;
            this.amountNeeded = amountNeeded;
            this.amountRaised = amountRaised;
            this.amountLent = amountLent;
        }
    }
}
