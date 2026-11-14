
package LibraryPal.demo.controller;

import jakarta.servlet.http.HttpSession;

import LibraryPal.demo.entity.Book;
import LibraryPal.demo.entity.Coordinator;
import LibraryPal.demo.repository.BookRepository;
import LibraryPal.demo.repository.LocalLibraryRepository;
import LibraryPal.demo.service.CoordinatorService;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
@RequestMapping("/provider")
public class ProviderPageController {

    private final CoordinatorService coordinatorService;
    private final PasswordEncoder passwordEncoder;
    private final BookRepository bookRepository;
    private final LocalLibraryRepository localLibraryRepository;

    public ProviderPageController(
            CoordinatorService coordinatorService,
            PasswordEncoder passwordEncoder,
            BookRepository bookRepository,
            LocalLibraryRepository localLibraryRepository) {

        this.coordinatorService = coordinatorService;
        this.passwordEncoder = passwordEncoder;
        this.bookRepository = bookRepository;
        this.localLibraryRepository = localLibraryRepository;
    }

    // Get the logged-in provider ID
    private Long getCoordinatorId(HttpSession session) {

        Object id = session.getAttribute("coordinatorId");

        if (!(id instanceof Long coordinatorId)) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Please log in to access your provider account."
            );
        }

        return coordinatorId;
    }

    // Get the currently logged-in provider
    private Coordinator getCurrentCoordinator(HttpSession session) {

        Long coordinatorId = getCoordinatorId(session);

        return coordinatorService.getCoordinatorById(coordinatorId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Coordinator not found"
                ));
    }

    // Verify that a book belongs to this provider
    private void verifyBookOwnership(Book book, Long coordinatorId) {

        if (book.getCoordinator() == null ||
                !book.getCoordinator().getId().equals(coordinatorId)) {

            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Book not found"
            );
        }
    }

    // ---------------- REGISTRATION ----------------

    @GetMapping("/register")
    public String registerPage() {
        return "provider/register-coordinator";
    }

    @PostMapping("/register")
    public String registerCoordinator(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String password,
            Model model,
            HttpSession session) {

        try {

            if (name.isBlank() || email.isBlank() || password.isBlank()) {
                throw new IllegalArgumentException(
                        "Please complete all required fields."
                );
            }

            Coordinator coordinator = new Coordinator();

            coordinator.setName(name.trim());
            coordinator.setEmail(email.trim());
            coordinator.setPassword(passwordEncoder.encode(password));
            coordinator.setAccountStatus("ACTIVE");

            Coordinator saved =
                    coordinatorService.createCoordinator(coordinator);

            session.setAttribute("coordinatorId", saved.getId());

            return "redirect:/provider/profile";

        } catch (ResponseStatusException |
                 IllegalArgumentException ex) {

            model.addAttribute("error", ex.getMessage());

            return "provider/register-coordinator";
        }
    }

    // ---------------- PROVIDER PROFILE ----------------

    @GetMapping("/profile")
    public String profile(
            HttpSession session,
            Model model) {

        Coordinator coordinator = getCurrentCoordinator(session);

        model.addAttribute("coordinator", coordinator);

        model.addAttribute(
                "libraries",
                localLibraryRepository.findByCoordinatorId(
                        coordinator.getId()
                )
        );

        return "provider/profile";
    }

    // ---------------- EDIT PROVIDER DETAILS ----------------

    @GetMapping("/edit-details")
    public String editDetails(
            HttpSession session,
            Model model) {

        Coordinator coordinator = getCurrentCoordinator(session);

        model.addAttribute("coordinator", coordinator);

        return "provider/edit-details";
    }

    @PostMapping("/edit-details")
    public String saveDetails(
            HttpSession session,
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam(required = false) String password,
            Model model) {

        Coordinator existing = getCurrentCoordinator(session);

        try {

            if (name.isBlank() || email.isBlank()) {
                throw new IllegalArgumentException(
                        "Name and email are required."
                );
            }

            Coordinator updated = new Coordinator();

            updated.setName(name.trim());
            updated.setEmail(email.trim());
            updated.setAccountStatus(existing.getAccountStatus());
            updated.setPassword(existing.getPassword());

            if (password != null && !password.isBlank()) {
                updated.setPassword(passwordEncoder.encode(password));
            }

            coordinatorService.updateCoordinator(
                    existing.getId(),
                    updated
            );

            return "redirect:/provider/profile?updated";

        } catch (ResponseStatusException |
                 IllegalArgumentException ex) {

            model.addAttribute("coordinator", existing);
            model.addAttribute("error", ex.getMessage());

            return "provider/edit-details";
        }
    }

    // ---------------- DELETE PROVIDER ACCOUNT ----------------

    @PostMapping("/delete-account")
    public String deleteAccount(HttpSession session) {

        Long coordinatorId = getCoordinatorId(session);

        boolean deleted =
                coordinatorService.deleteCoordinator(coordinatorId);

        if (!deleted) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Coordinator not found"
            );
        }

        session.invalidate();

        return "redirect:/login";
    }

    // ---------------- MANAGE BOOKS ----------------

    @GetMapping("/books")
    public String manageBooks(
            HttpSession session,
            Model model) {

        Long coordinatorId = getCoordinatorId(session);

        List<Book> books =
                bookRepository.findByCoordinatorId(coordinatorId);

        int totalCopies = books.stream()
                .mapToInt(Book::getTotalCopies)
                .sum();

        int availableCopies = books.stream()
                .mapToInt(Book::getAvailableCopies)
                .sum();

        model.addAttribute("books", books);
        model.addAttribute("totalBooks", books.size());
        model.addAttribute("totalCopies", totalCopies);
        model.addAttribute("availableCopies", availableCopies);
        model.addAttribute(
                "checkedOutCopies",
                totalCopies - availableCopies
        );

        return "provider/manage-books";
    }

    // ---------------- ADD BOOK ----------------

    @GetMapping("/books/new")
    public String newBook(
            HttpSession session,
            Model model) {

        getCoordinatorId(session);

        model.addAttribute("book", new Book());

        return "provider/new-book";
    }

    @PostMapping("/books/new")
    public String addBook(
            HttpSession session,
            @RequestParam String title,
            @RequestParam String author,
            @RequestParam String isbn,
            @RequestParam String genre,
            @RequestParam(required = false) String publisher,
            @RequestParam(required = false) Integer publicationYear,
            @RequestParam Integer totalCopies,
            @RequestParam String description,
            @RequestParam String location,
            Model model) {

        Long coordinatorId = getCoordinatorId(session);

        try {

            if (title.isBlank() || author.isBlank()
                    || isbn.isBlank() || genre.isBlank()
                    || description.isBlank() || location.isBlank()) {

                throw new IllegalArgumentException(
                        "Please complete all required fields."
                );
            }

            if (totalCopies < 1) {
                throw new IllegalArgumentException(
                        "You must add at least one copy."
                );
            }

            if (bookRepository.existsByIsbnAndCoordinatorId(
                    isbn.trim(), coordinatorId)) {

                throw new IllegalArgumentException(
                        "This ISBN already exists in your collection."
                );
            }

            Coordinator coordinator =
                    getCurrentCoordinator(session);

            Book book = new Book();

            book.setTitle(title.trim());
            book.setAuthor(author.trim());
            book.setIsbn(isbn.trim());
            book.setGenre(genre.trim());
            book.setPublisher(publisher);
            book.setPublicationYear(publicationYear);
            book.setTotalCopies(totalCopies);
            book.setAvailableCopies(totalCopies);
            book.setDescription(description.trim());
            book.setLocation(location.trim());
            book.setCoordinator(coordinator);
            book.setLocalLibrary(null);

            bookRepository.save(book);

            return "redirect:/provider/books";

        } catch (IllegalArgumentException |
                 ResponseStatusException ex) {

            model.addAttribute("errorMessage", ex.getMessage());
            model.addAttribute("book", new Book());

            model.addAttribute("formTitle", title);
            model.addAttribute("formAuthor", author);
            model.addAttribute("formIsbn", isbn);
            model.addAttribute("formGenre", genre);
            model.addAttribute("formPublisher", publisher);
            model.addAttribute("formPublicationYear", publicationYear);
            model.addAttribute("formTotalCopies", totalCopies);
            model.addAttribute("formDescription", description);
            model.addAttribute("formLocation", location);

            return "provider/new-book";
        }
    }

    // ---------------- EDIT BOOK ----------------

    @GetMapping("/books/{id}/edit")
    public String editBook(
            @PathVariable Long id,
            HttpSession session,
            Model model) {

        Long coordinatorId = getCoordinatorId(session);

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Book not found"
                ));

        verifyBookOwnership(book, coordinatorId);

        model.addAttribute("book", book);

        return "provider/edit-book";
    }

    @PostMapping("/books/{id}/edit")
    public String updateBook(
            @PathVariable Long id,
            HttpSession session,
            @RequestParam String title,
            @RequestParam String author,
            @RequestParam String isbn,
            @RequestParam String genre,
            @RequestParam(required = false) String publisher,
            @RequestParam(required = false) Integer publicationYear,
            @RequestParam Integer totalCopies,
            @RequestParam String description,
            @RequestParam String location,
            Model model) {

        Long coordinatorId = getCoordinatorId(session);

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Book not found"
                ));

        verifyBookOwnership(book, coordinatorId);

        try {

            if (title.isBlank() || author.isBlank()
                    || isbn.isBlank() || genre.isBlank()
                    || description.isBlank() || location.isBlank()) {

                throw new IllegalArgumentException(
                        "Please complete all required fields."
                );
            }

            if (totalCopies < 1) {
                throw new IllegalArgumentException(
                        "Total copies must be at least one."
                );
            }

            if (bookRepository.existsByIsbnAndCoordinatorIdAndIdNot(
                    isbn.trim(), coordinatorId, id)) {

                throw new IllegalArgumentException(
                        "Another book in your collection already uses this ISBN."
                );
            }

            int checkedOut =
                    book.getTotalCopies() - book.getAvailableCopies();

            if (totalCopies < checkedOut) {
                throw new IllegalArgumentException(
                        "Total copies cannot be less than the number currently checked out."
                );
            }

            book.setTitle(title.trim());
            book.setAuthor(author.trim());
            book.setIsbn(isbn.trim());
            book.setGenre(genre.trim());
            book.setPublisher(publisher);
            book.setPublicationYear(publicationYear);
            book.setTotalCopies(totalCopies);
            book.setAvailableCopies(totalCopies - checkedOut);
            book.setDescription(description.trim());
            book.setLocation(location.trim());

            bookRepository.save(book);

            return "redirect:/provider/books";

        } catch (IllegalArgumentException ex) {

            model.addAttribute("book", book);
            model.addAttribute("errorMessage", ex.getMessage());

            return "provider/edit-book";
        }
    }

    // ---------------- DELETE BOOK ----------------

    @PostMapping("/books/{id}/delete")
    public String deleteBook(
            @PathVariable Long id,
            HttpSession session) {

        Long coordinatorId = getCoordinatorId(session);

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Book not found"
                ));

        verifyBookOwnership(book, coordinatorId);

        bookRepository.delete(book);

        return "redirect:/provider/books";
    }
}
