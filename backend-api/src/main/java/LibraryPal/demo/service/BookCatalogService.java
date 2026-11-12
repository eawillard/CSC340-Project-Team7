package LibraryPal.demo.service;

import LibraryPal.demo.dto.BookRequest;
import LibraryPal.demo.entity.Book;
import LibraryPal.demo.entity.LocalLibrary;
import LibraryPal.demo.repository.BookRepository;
import LibraryPal.demo.repository.LocalLibraryRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class BookCatalogService {

    private final BookRepository bookRepository;
    private final LocalLibraryRepository libraryRepository;

    public BookCatalogService(
            BookRepository bookRepository,
            LocalLibraryRepository libraryRepository) {
        this.bookRepository = bookRepository;
        this.libraryRepository = libraryRepository;
    }

    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    public List<Book> getByLibrary(Long libraryId) {
        if (!libraryRepository.existsById(libraryId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Library not found");
        }

        return bookRepository.findByLocalLibraryId(libraryId);
    }

    public Book getById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Book not found"));
    }

    @Transactional
    public Book create(BookRequest request) {
        LocalLibrary library = getLibrary(request.getLibraryId());

        validateCopies(request);

        if (bookRepository.existsByIsbnAndLocalLibraryId(
                request.getIsbn(), library.getId())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "This ISBN already exists in this library");
        }

        Book book = new Book();
        updateFields(book, request);
        book.setLocalLibrary(library);

        return bookRepository.save(book);
    }

    @Transactional
    public Book update(Long id, BookRequest request) {
        Book book = getById(id);
        LocalLibrary library = getLibrary(request.getLibraryId());

        validateCopies(request);

        if (bookRepository.existsByIsbnAndLocalLibraryIdAndIdNot(
                request.getIsbn(), library.getId(), id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "This ISBN already exists in this library");
        }

        updateFields(book, request);
        book.setLocalLibrary(library);

        return bookRepository.save(book);
    }

    @Transactional
    public void delete(Long id) {
        Book book = getById(id);
        bookRepository.delete(book);
    }

    private LocalLibrary getLibrary(Long libraryId) {
        return libraryRepository.findById(libraryId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Library not found"));
    }

    private void validateCopies(BookRequest request) {
        if (request.getAvailableCopies() > request.getTotalCopies()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Available copies cannot exceed total copies");
        }
    }

    private void updateFields(Book book, BookRequest request) {
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setIsbn(request.getIsbn());
        book.setGenre(request.getGenre());
        book.setPublisher(request.getPublisher());
        book.setPublicationYear(request.getPublicationYear());
        book.setDescription(request.getDescription());
        book.setTotalCopies(request.getTotalCopies());
        book.setAvailableCopies(request.getAvailableCopies());
        book.setLocation(request.getLocation());
    }
}
