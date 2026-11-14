package LibraryPal.demo.controller;

import LibraryPal.demo.entity.Coordinator;
import LibraryPal.demo.entity.Customer;
import LibraryPal.demo.service.CoordinatorService;
import LibraryPal.demo.service.CustomerService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SharedLoginController {

    private final CustomerService customerService;
    private final CoordinatorService coordinatorService;
    private final PasswordEncoder passwordEncoder;

    public SharedLoginController(
            CustomerService customerService,
            CoordinatorService coordinatorService,
            PasswordEncoder passwordEncoder) {

        this.customerService = customerService;
        this.coordinatorService = coordinatorService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String accountType,
            @RequestParam String identifier,
            @RequestParam String password,
            HttpServletRequest request,
            Model model) {

        try {
            if ("CUSTOMER".equalsIgnoreCase(accountType)) {

                Customer customer = customerService.authenticate(
                        identifier.trim(), password);

                HttpSession session = request.getSession(true);
                request.changeSessionId();

                session.setAttribute("customerId", customer.getId());

                return "redirect:/customer/books";
            }

            if ("PROVIDER".equalsIgnoreCase(accountType)) {

                Coordinator coordinator = coordinatorService
                        .getCoordinatorByEmail(identifier.trim())
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Invalid email or password."));

                if (!passwordEncoder.matches(
                        password, coordinator.getPassword())) {

                    throw new IllegalArgumentException(
                            "Invalid email or password.");
                }

                HttpSession session = request.getSession(true);
                request.changeSessionId();

                session.setAttribute(
                        "coordinatorId", coordinator.getId());

                return "redirect:/provider/profile";
            }

            throw new IllegalArgumentException(
                    "Please select a valid account type.");

        } catch (IllegalArgumentException ex) {

            model.addAttribute("errorMessage", ex.getMessage());
            model.addAttribute("identifier", identifier);

            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(
            HttpSession session,
            RedirectAttributes redirect) {

        session.invalidate();

        redirect.addFlashAttribute(
                "successMessage",
                "You have successfully logged out.");

        return "redirect:/login";
    }
}
