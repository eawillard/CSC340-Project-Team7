package LibraryPal.demo.service;

import LibraryPal.demo.entity.Book;
import LibraryPal.demo.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

// Read-only book lookups used by the customer pages.
@Service
public class BookBrowseService {

    private final BookRepository bookRepository;

    public BookBrowseService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // All books, or only books whose title or author contains the text.
    public List<Book> search(String query) {
        if (query == null || query.isBlank()) {
            return bookRepository.findAll();
        }

        String text = query.trim();
        return bookRepository
                .findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(
                        text, text);
    }

    public Book getById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "That book could not be found"));
    }
}
