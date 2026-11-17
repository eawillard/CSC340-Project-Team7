
package LibraryPal.demo.service;

import LibraryPal.demo.dto.BookRequest;
import LibraryPal.demo.entity.Book;
import LibraryPal.demo.entity.Coordinator;
import LibraryPal.demo.entity.LocalLibrary;
import LibraryPal.demo.repository.BookRepository;
import LibraryPal.demo.repository.LocalLibraryRepository;
import LibraryPal.demo.repository.CoordinatorRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class BookCatalogService {

    private final BookRepository bookRepository;
    private final LocalLibraryRepository libraryRepository;
    private final CoordinatorRepository coordinatorRepository;

    public BookCatalogService(
            BookRepository bookRepository,
            LocalLibraryRepository libraryRepository,
            CoordinatorRepository coordinatorRepository) {

        this.bookRepository = bookRepository;
        this.libraryRepository = libraryRepository;
        this.coordinatorRepository = coordinatorRepository;
    }

    // CUSTOMER: Browse all books
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    // CUSTOMER: Browse books from a specific library
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

    // COORDINATOR: Add a book to an assigned library
    @Transactional
    public Book create(BookRequest request, Long coordinatorId) {

        LocalLibrary library = getLibrary(request.getLibraryId());

        checkCoordinatorAssignment(coordinatorId, library.getId());

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

    // COORDINATOR: Edit a book in an assigned library
    @Transactional
    public Book update(
            Long id,
            BookRequest request,
            Long coordinatorId) {

        Book book = getById(id);

        LocalLibrary library = getLibrary(request.getLibraryId());

        checkCoordinatorAssignment(coordinatorId, library.getId());

        // Prevent moving a book to a different library through this method
        if (!book.getLocalLibrary().getId().equals(library.getId())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "You cannot move a book to another library");
        }

        validateCopies(request);

        if (bookRepository.existsByIsbnAndLocalLibraryIdAndIdNot(
                request.getIsbn(), library.getId(), id)) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "This ISBN already exists in this library");
        }

        updateFields(book, request);

        return bookRepository.save(book);
    }

    // COORDINATOR: Delete a book from an assigned library
    @Transactional
    public void delete(Long id, Long coordinatorId) {

        Book book = getById(id);

        checkCoordinatorAssignment(
                coordinatorId,
                book.getLocalLibrary().getId());

        bookRepository.delete(book);
    }

    private LocalLibrary getLibrary(Long libraryId) {

        return libraryRepository.findById(libraryId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Library not found"));
    }

    private void checkCoordinatorAssignment(
            Long coordinatorId,
            Long libraryId) {

        Coordinator coordinator = coordinatorRepository
                .findById(coordinatorId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Coordinator not found"));

        boolean assigned = coordinator.getLibraries()
                .stream()
                .anyMatch(library ->
                        library.getId().equals(libraryId));

        if (!assigned) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You are not assigned to this library");
        }
    }

    private void validateCopies(BookRequest request) {

        if (request.getTotalCopies() == null
                || request.getAvailableCopies() == null
                || request.getAvailableCopies() < 0
                || request.getTotalCopies() < 1
                || request.getAvailableCopies() >
                   request.getTotalCopies()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid book copy numbers");
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
