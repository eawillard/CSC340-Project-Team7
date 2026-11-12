package LibraryPal.demo.controller;

import LibraryPal.demo.entity.Book;
import LibraryPal.demo.entity.Customer;
import LibraryPal.demo.service.BookBrowseService;
import LibraryPal.demo.service.CustomerService;
import LibraryPal.demo.service.PickupService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

// FreeMarker pages for the customer actor. Each handler calls a service
// and returns the name of a template under templates/customer/.
@Controller
@RequestMapping("/customer")
public class CustomerPageController {

    private static final String SESSION_KEY = "customerId";
    private static final String LOGIN = "redirect:/customer/login";

    private final CustomerService customerService;
    private final BookBrowseService bookBrowseService;
    private final PickupService pickupService;

    public CustomerPageController(
            CustomerService customerService,
            BookBrowseService bookBrowseService,
            PickupService pickupService) {
        this.customerService = customerService;
        this.bookBrowseService = bookBrowseService;
        this.pickupService = pickupService;
    }

    @GetMapping({"", "/"})
    public String home(HttpSession session) {
        return session.getAttribute(SESSION_KEY) == null
                ? LOGIN : "redirect:/customer/books";
    }

    // ---------- Account: register, login, logout (US-3) ----------

    @GetMapping("/register")
    public String registerForm() {
        return "customer/register";
    }

    @PostMapping("/register")
    public String register(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "") String username,
            @RequestParam(defaultValue = "") String password,
            @RequestParam(defaultValue = "") String confirmPassword,
            Model model,
            RedirectAttributes redirect) {

        String error = null;
        if (name.isBlank() || username.isBlank()) {
            error = "Name and username are required.";
        } else if (password.length() < 6) {
            error = "Password must be at least 6 characters.";
        } else if (!password.equals(confirmPassword)) {
            error = "Passwords do not match.";
        }

        if (error == null) {
            try {
                Customer customer = new Customer();
                customer.setName(name.trim());
                customer.setUsername(username.trim());
                customer.setPassword(password);
                customerService.register(customer);

                redirect.addFlashAttribute("successMessage",
                        "Account created. Please sign in.");
                return LOGIN;
            } catch (IllegalArgumentException e) {
                error = e.getMessage();
            }
        }

        model.addAttribute("errorMessage", error);
        model.addAttribute("name", name);
        model.addAttribute("username", username);
        return "customer/register";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "customer/login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam(defaultValue = "") String username,
            @RequestParam(defaultValue = "") String password,
            HttpServletRequest request,
            Model model) {

        try {
            Customer customer =
                    customerService.authenticate(username, password);

            // New session id after login, to prevent session fixation
            request.getSession(true);
            request.changeSessionId();
            request.getSession().setAttribute(SESSION_KEY, customer.getId());

            return "redirect:/customer/books";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("username", username);
            return "customer/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return LOGIN;
    }

    // ---------- Profile (US-3) ----------

    @GetMapping("/profile")
    public String profile(HttpSession session, Model model) {
        Customer customer = currentCustomer(session);
        if (customer == null) {
            return LOGIN;
        }

        model.addAttribute("customer", customer);
        return "customer/profile";
    }

    @PostMapping("/profile")
    public String updateProfile(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "") String username,
            HttpSession session,
            Model model,
            RedirectAttributes redirect) {

        Customer customer = currentCustomer(session);
        if (customer == null) {
            return LOGIN;
        }

        try {
            if (name.isBlank() || username.isBlank()) {
                throw new IllegalArgumentException(
                        "Name and username cannot be blank.");
            }

            customerService.updateProfile(
                    customer.getId(), name.trim(), username.trim());

            redirect.addFlashAttribute("successMessage",
                    "Your profile was updated.");
            return "redirect:/customer/profile";
        } catch (IllegalArgumentException e) {
            model.addAttribute("customer", customer);
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("editName", name);
            model.addAttribute("editUsername", username);
            return "customer/profile";
        }
    }

    // ---------- Browse books and schedule pickup (US-1) ----------

    @GetMapping("/books")
    public String books(
            @RequestParam(required = false) String q,
            HttpSession session,
            Model model) {

        Customer customer = currentCustomer(session);
        if (customer == null) {
            return LOGIN;
        }

        model.addAttribute("customer", customer);
        model.addAttribute("books", bookBrowseService.search(q));
        model.addAttribute("q", q == null ? "" : q);
        return "customer/books";
    }

    @GetMapping("/books/{id}")
    public String bookDetail(
            @PathVariable Long id,
            HttpSession session,
            Model model,
            RedirectAttributes redirect) {

        Customer customer = currentCustomer(session);
        if (customer == null) {
            return LOGIN;
        }

        try {
            Book book = bookBrowseService.getById(id);
            model.addAttribute("customer", customer);
            model.addAttribute("book", book);
            model.addAttribute("today", LocalDate.now().toString());
            return "customer/book-detail";
        } catch (IllegalArgumentException e) {
            redirect.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/customer/books";
        }
    }

    @PostMapping("/books/{id}/request")
    public String requestBook(
            @PathVariable Long id,
            @RequestParam(defaultValue = "") String requestType,
            @RequestParam(defaultValue = "") String scheduleDate,
            HttpSession session,
            RedirectAttributes redirect) {

        Customer customer = currentCustomer(session);
        if (customer == null) {
            return LOGIN;
        }

        try {
            pickupService.schedule(
                    customer.getId(), id, requestType, scheduleDate);

            redirect.addFlashAttribute("successMessage",
                    "Your request was scheduled.");
            return "redirect:/customer/requests";
        } catch (IllegalArgumentException e) {
            redirect.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/customer/books/" + id;
        }
    }

    // ---------- My requests (US-1) ----------

    @GetMapping("/requests")
    public String requests(HttpSession session, Model model) {
        Customer customer = currentCustomer(session);
        if (customer == null) {
            return LOGIN;
        }

        model.addAttribute("customer", customer);
        model.addAttribute("requests",
                pickupService.getForCustomer(customer.getId()));
        return "customer/requests";
    }

    @PostMapping("/requests/{id}/cancel")
    public String cancelRequest(
            @PathVariable Long id,
            HttpSession session,
            RedirectAttributes redirect) {

        Customer customer = currentCustomer(session);
        if (customer == null) {
            return LOGIN;
        }

        try {
            pickupService.cancel(customer.getId(), id);
            redirect.addFlashAttribute("successMessage",
                    "Your request was cancelled.");
        } catch (IllegalArgumentException e) {
            redirect.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/customer/requests";
    }

    // A non-numeric id in a URL (for example /customer/books/abc)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public String badId() {
        return "redirect:/customer/books";
    }

    // The logged-in customer, or null when nobody is logged in.
    private Customer currentCustomer(HttpSession session) {
        Object id = session.getAttribute(SESSION_KEY);
        if (!(id instanceof Long customerId)) {
            return null;
        }

        try {
            return customerService.findById(customerId);
        } catch (IllegalArgumentException e) {
            session.invalidate();
            return null;
        }
    }
}
