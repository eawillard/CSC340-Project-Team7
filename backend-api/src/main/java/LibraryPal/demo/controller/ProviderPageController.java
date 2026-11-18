
package LibraryPal.demo.controller;

import jakarta.servlet.http.HttpSession;

import LibraryPal.demo.entity.Book;
import LibraryPal.demo.entity.Coordinator;
import LibraryPal.demo.entity.LocalLibrary;

import LibraryPal.demo.repository.BookRepository;
import LibraryPal.demo.repository.LocalLibraryRepository;
import LibraryPal.demo.repository.CoordinatorRepository;

import LibraryPal.demo.service.CoordinatorService;
import LibraryPal.demo.service.LocalLibraryService;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/provider")
public class ProviderPageController {

    private final CoordinatorService coordinatorService;
    private final PasswordEncoder passwordEncoder;
    private final BookRepository bookRepository;
    private final LocalLibraryRepository localLibraryRepository;
    private final LocalLibraryService localLibraryService;
    private final CoordinatorRepository coordinatorRepository;

    public ProviderPageController(
            CoordinatorService coordinatorService,
            PasswordEncoder passwordEncoder,
            BookRepository bookRepository,
            LocalLibraryRepository localLibraryRepository,
            LocalLibraryService localLibraryService,
            CoordinatorRepository coordinatorRepository
    ) {
        this.coordinatorService = coordinatorService;
        this.passwordEncoder = passwordEncoder;
        this.bookRepository = bookRepository;
        this.localLibraryRepository = localLibraryRepository;
        this.localLibraryService = localLibraryService;
        this.coordinatorRepository = coordinatorRepository;
    }

    // SESSION MANAGEMENT

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

    private Coordinator getCurrentCoordinator(HttpSession session) {

        Long id = getCoordinatorId(session);

        return coordinatorService.getCoordinatorById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Coordinator not found"
                ));
    }

    private void verifyBookOwnership(Book book, Long coordinatorId) {

        if (book.getLocalLibrary() == null ||
                book.getLocalLibrary().getCoordinators() == null ||
                book.getLocalLibrary().getCoordinators().stream()
                        .noneMatch(c -> c.getId().equals(coordinatorId))) {

            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Book not found"
            );
        }
    }

    // REGISTRATION

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

            if (name.isBlank() ||
                    email.isBlank() ||
                    password.isBlank()) {

                throw new IllegalArgumentException(
                        "Please complete all required fields."
                );
            }

            Coordinator coordinator = new Coordinator();

            coordinator.setName(name.trim());
            coordinator.setEmail(email.trim().toLowerCase());
            coordinator.setPassword(passwordEncoder.encode(password));
            coordinator.setAccountStatus("ACTIVE");

            Coordinator saved =
                    coordinatorService.createCoordinator(coordinator);

            session.setAttribute("coordinatorId", saved.getId());

            return "redirect:/provider/dashboard";

        } catch (ResponseStatusException |
                 IllegalArgumentException ex) {

            model.addAttribute("error", ex.getMessage());

            return "provider/register-coordinator";
        }
    }

    // DASHBOARD

    @GetMapping("/dashboard")
    public String dashboard(
            HttpSession session,
            Model model) {

        Coordinator coordinator = getCurrentCoordinator(session);

        List<Book> books =
                bookRepository.findDistinctByLocalLibraryCoordinators_Id(
                        coordinator.getId());

        int totalCopies = books.stream()
                .mapToInt(Book::getTotalCopies)
                .sum();

        int availableCopies = books.stream()
                .mapToInt(Book::getAvailableCopies)
                .sum();

        model.addAttribute("provider", coordinator);
        model.addAttribute("coordinator", coordinator);
        model.addAttribute("books", books);
        model.addAttribute("totalBooks", books.size());
        model.addAttribute("availableBooks", availableCopies);
        model.addAttribute("totalCustomers", 0);
        model.addAttribute("pendingRequests", 0);

        model.addAttribute("pickups", List.of());
        model.addAttribute("reviews", List.of());

        return "provider/dashboard";
    }

    // PROVIDER PROFILE

    @GetMapping("/profile")
    public String profile(
            HttpSession session,
            Model model) {

        Coordinator coordinator = getCurrentCoordinator(session);

        model.addAttribute("coordinator", coordinator);

        model.addAttribute(
                "libraries",
                localLibraryService.getByCoordinator(coordinator.getId())
        );

        return "provider/profile";
    }

    // EDIT ACCOUNT DETAILS

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
            updated.setEmail(email.trim().toLowerCase());
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

    // DELETE ACCOUNT

    @PostMapping("/delete-account")
    public String deleteAccount(HttpSession session) {

        Long id = getCoordinatorId(session);

        coordinatorService.deleteCoordinator(id);

        session.invalidate();

        return "redirect:/login";
    }

    // MANAGE BOOKS

    @GetMapping("/books")
    public String manageBooks(
            HttpSession session,
            Model model) {

        Long coordinatorId = getCoordinatorId(session);

        List<Book> books =
                bookRepository.findDistinctByLocalLibraryCoordinators_Id(
                        coordinatorId);

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

    // ADD BOOK - GET

    @GetMapping("/books/new")
    public String newBook(
            HttpSession session,
            Model model) {

        Long coordinatorId = getCoordinatorId(session);

        model.addAttribute("book", new Book());

        model.addAttribute(
                "libraries",
                localLibraryRepository.findDistinctByCoordinators_Id(
                        coordinatorId)
        );

        return "provider/new-book";
    }

    // ADD BOOK - POST

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
            @RequestParam Long libraryId,
            Model model) {

        Long coordinatorId = getCoordinatorId(session);

        try {

            LocalLibrary library =
                    localLibraryRepository.findDistinctByCoordinators_Id(
                            coordinatorId)
                            .stream()
                            .filter(l -> l.getId().equals(libraryId))
                            .findFirst()
                            .orElseThrow(() -> new ResponseStatusException(
                                    HttpStatus.FORBIDDEN,
                                    "You are not assigned to this library"
                            ));

            if (title.isBlank() ||
                    author.isBlank() ||
                    isbn.isBlank() ||
                    genre.isBlank() ||
                    description.isBlank() ||
                    location.isBlank()) {

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

            book.setLocalLibrary(library);

            bookRepository.save(book);

            return "redirect:/provider/books";

        } catch (IllegalArgumentException ex) {

            model.addAttribute("book", new Book());
            model.addAttribute("errorMessage", ex.getMessage());

            model.addAttribute("formTitle", title);
            model.addAttribute("formAuthor", author);
            model.addAttribute("formIsbn", isbn);
            model.addAttribute("formGenre", genre);
            model.addAttribute("formPublisher", publisher);
            model.addAttribute("formPublicationYear", publicationYear);
            model.addAttribute("formTotalCopies", totalCopies);
            model.addAttribute("formDescription", description);
            model.addAttribute("formLocation", location);

            model.addAttribute(
                    "libraries",
                    localLibraryRepository.findDistinctByCoordinators_Id(
                            coordinatorId)
            );

            return "provider/new-book";
        }
    }

    // EDIT BOOK - GET

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

        model.addAttribute(
                "libraries",
                localLibraryRepository.findDistinctByCoordinators_Id(
                        coordinatorId)
        );

        return "provider/edit-book";
    }

    // EDIT BOOK - POST

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
            @RequestParam Long libraryId,
            Model model) {

        Long coordinatorId = getCoordinatorId(session);

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Book not found"
                ));

        verifyBookOwnership(book, coordinatorId);

        LocalLibrary library =
                localLibraryRepository.findDistinctByCoordinators_Id(
                        coordinatorId)
                        .stream()
                        .filter(l -> l.getId().equals(libraryId))
                        .findFirst()
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.FORBIDDEN,
                                "You are not assigned to this library"
                        ));

        if (!book.getLocalLibrary().getId().equals(library.getId())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "You cannot move a book to another library."
            );
        }

        try {

            if (title.isBlank() ||
                    author.isBlank() ||
                    isbn.isBlank() ||
                    genre.isBlank() ||
                    description.isBlank() ||
                    location.isBlank()) {

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
                        "Another book already uses this ISBN."
                );
            }

            int checkedOut =
                    book.getTotalCopies() - book.getAvailableCopies();

            if (totalCopies < checkedOut) {
                throw new IllegalArgumentException(
                        "Total copies cannot be less than copies checked out."
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

            model.addAttribute(
                    "libraries",
                    localLibraryRepository.findDistinctByCoordinators_Id(
                            coordinatorId)
            );

            return "provider/edit-book";
        }
    }

    // DELETE BOOK

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

    // MANAGE LIBRARIES

    @GetMapping("/libraries")
    public String manageLibraries(
            HttpSession session,
            Model model) {

        Coordinator coordinator = getCurrentCoordinator(session);

        List<LocalLibrary> libraries =
                localLibraryService.getAll();

    Set<Long> assignedLibraryIds =
        localLibraryService.getByCoordinator(coordinator.getId())
                .stream()
                .map(LocalLibrary::getId)
                .collect(Collectors.toSet());

        model.addAttribute("libraries", libraries);

        model.addAttribute(
                "assignedLibraryIds",
                assignedLibraryIds
        );

        return "provider/libraries";
    }

    // ASSIGN YOURSELF TO A LIBRARY

    @PostMapping("/libraries/{id}/assign")
    public String assignLibrary(
            @PathVariable Long id,
            HttpSession session) {

        Long coordinatorId = getCoordinatorId(session);

        localLibraryService.assignCoordinator(
                id,
                coordinatorId
        );

        return "redirect:/provider/libraries";
    }

    // CREATE A NEW LIBRARY

    @PostMapping("/libraries")
    public String addLibrary(
            HttpSession session,
            @RequestParam String name,
            @RequestParam String address,
            Model model) {

        Long coordinatorId = getCoordinatorId(session);

        if (name.isBlank() || address.isBlank()) {

            model.addAttribute(
                    "errorMessage",
                    "Please complete both library fields."
            );

            return manageLibraries(session, model);
        }

        LocalLibrary library = new LocalLibrary();

        library.setName(name.trim());
        library.setAddress(address.trim());

        localLibraryService.create(library, coordinatorId);

        return "redirect:/provider/libraries";
    }
    // DELETE LIBRARY

@PostMapping("/libraries/{id}/delete")
public String deleteLibrary(
        @PathVariable Long id,
        HttpSession session) {

    getCoordinatorId(session);

    localLibraryService.delete(id);

    return "redirect:/provider/libraries";
}

    // LOG OUT

    @GetMapping("/logout")
    public String logout(HttpSession session) {

        session.invalidate();

        return "redirect:/login";
    }
}
