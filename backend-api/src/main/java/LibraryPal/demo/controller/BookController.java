package LibraryPal.demo.controller;

import LibraryPal.demo.dto.BookRequest;
import LibraryPal.demo.entity.Book;
import LibraryPal.demo.service.BookCatalogService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookCatalogService bookService;

    public BookController(BookCatalogService bookService) {
        this.bookService = bookService;
    }

    // Get the logged-in coordinator's ID.
    private Long getCoordinatorId(HttpSession session) {

        Object id = session.getAttribute("coordinatorId");

        if (!(id instanceof Long coordinatorId)) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Please log in."
            );
        }

        return coordinatorId;
    }

    // CREATE BOOK
    @PostMapping
    public ResponseEntity<Book> create(
            @Valid @RequestBody BookRequest request,
            HttpSession session) {

        Long coordinatorId = getCoordinatorId(session);

        Book book = bookService.create(request, coordinatorId);

        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }

    // GET ALL BOOKS
    @GetMapping
    public List<Book> getAll() {
        return bookService.getAll();
    }

    // GET BOOK BY ID
    @GetMapping("/{id}")
    public Book getById(@PathVariable Long id) {
        return bookService.getById(id);
    }

    // GET BOOKS BY LIBRARY
    @GetMapping("/library/{libraryId}")
    public List<Book> getByLibrary(
            @PathVariable Long libraryId) {

        return bookService.getByLibrary(libraryId);
    }

    // UPDATE BOOK
    @PutMapping("/{id}")
    public Book update(
            @PathVariable Long id,
            @Valid @RequestBody BookRequest request,
            HttpSession session) {

        Long coordinatorId = getCoordinatorId(session);

        return bookService.update(id, request, coordinatorId);
    }

    // DELETE BOOK
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            HttpSession session) {

        Long coordinatorId = getCoordinatorId(session);

        bookService.delete(id, coordinatorId);

        return ResponseEntity.noContent().build();
    }
}
