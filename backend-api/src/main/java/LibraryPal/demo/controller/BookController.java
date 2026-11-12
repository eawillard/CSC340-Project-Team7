
package LibraryPal.demo.controller;

import LibraryPal.demo.dto.BookRequest;
import LibraryPal.demo.entity.Book;
import LibraryPal.demo.service.BookCatalogService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookCatalogService bookService;

    public BookController(BookCatalogService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<Book> create(
            @Valid @RequestBody BookRequest request) {

        Book book = bookService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }

    @GetMapping
    public List<Book> getAll() {
        return bookService.getAll();
    }

    @GetMapping("/{id}")
    public Book getById(@PathVariable Long id) {
        return bookService.getById(id);
    }

    @GetMapping("/library/{libraryId}")
    public List<Book> getByLibrary(
            @PathVariable Long libraryId) {
        return bookService.getByLibrary(libraryId);
    }

    @PutMapping("/{id}")
    public Book update(
            @PathVariable Long id,
            @Valid @RequestBody BookRequest request) {

        return bookService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
