package LibraryPal.demo.controller;

import LibraryPal.demo.entity.Coordinator;
import LibraryPal.demo.service.CoordinatorService;
import LibraryPal.demo.service.LocalLibraryService;

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

    private final CoordinatorService coordinatorService;
    private final PasswordEncoder passwordEncoder;
    private final LocalLibraryService localLibraryService;

    public SharedLoginController(
            CoordinatorService coordinatorService,
            PasswordEncoder passwordEncoder,
            LocalLibraryService localLibraryService) {

        this.coordinatorService = coordinatorService;
        this.passwordEncoder = passwordEncoder;
        this.localLibraryService = localLibraryService;
    }

    // Display provider login page
    @GetMapping("/login")
    public String loginPage() {
        return "provider/login";
    }

    // Process provider login
    @PostMapping("/login")
    public String login(
            @RequestParam String identifier,
            @RequestParam String password,
            HttpServletRequest request,
            Model model) {

        try {

            String email = identifier.trim().toLowerCase();

            Coordinator coordinator = coordinatorService
                    .getCoordinatorByEmail(email)
                    .orElseThrow(() ->
                            new IllegalArgumentException(
                                    "Invalid email or password."
                            ));

            if (coordinator.getPassword() == null ||
                    !passwordEncoder.matches(
                            password,
                            coordinator.getPassword())) {

                throw new IllegalArgumentException(
                        "Invalid email or password."
                );
            }

            // Create a fresh session identifier
            request.getSession(true);
            request.changeSessionId();

            // Store logged-in provider ID
            HttpSession session = request.getSession();

            session.setAttribute(
                    "coordinatorId",
                    coordinator.getId()
            );

            return "redirect:/provider/dashboard";

        } catch (IllegalArgumentException ex) {

            model.addAttribute(
                    "errorMessage",
                    ex.getMessage()
            );

            model.addAttribute("identifier", identifier);

            return "provider/login";
        }
    }

    // Provider logout
    @GetMapping("/logout")
    public String logout(
            HttpSession session,
            RedirectAttributes redirect) {

        session.invalidate();

        redirect.addFlashAttribute(
                "successMessage",
                "You have successfully logged out."
        );

        return "redirect:/login";
    }
    //home page
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("libraries", localLibraryService.getAll()
    );
        return "shared/index";
    }
}
