package LibraryPal.demo.repository;

import LibraryPal.demo.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    // Find books across all libraries belonging to a coordinator.
    @EntityGraph(attributePaths = "localLibrary")
    List<Book> findDistinctByLocalLibraryCoordinators_Id(Long coordinatorId);

    // Find a specific book and eagerly load its library and coordinators.
    @EntityGraph(attributePaths = {
            "localLibrary",
            "localLibrary.coordinators"
    })
    Optional<Book> findBookWithLibraryById(Long id);

    // Check ISBN uniqueness across a coordinator's libraries.
    boolean existsByIsbnAndLocalLibraryCoordinators_Id(
            String isbn, Long coordinatorId);

    boolean existsByIsbnAndLocalLibraryCoordinators_IdAndIdNot(
            String isbn, Long coordinatorId, Long id);

    // Find books belonging to a particular library.
    // Load the library to prevent FreeMarker lazy-loading errors.
    @EntityGraph(attributePaths = "localLibrary")
    List<Book> findByLocalLibraryId(Long libraryId);

    // Check ISBN uniqueness within a library.
    boolean existsByIsbnAndLocalLibraryId(
            String isbn, Long libraryId);

    boolean existsByIsbnAndLocalLibraryIdAndIdNot(
            String isbn, Long libraryId, Long id);

    // Count books in a particular library.
    long countByLocalLibraryId(Long libraryId);

    // Search books by title or author.
    @EntityGraph(attributePaths = "localLibrary")
    List<Book> findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(
            String title, String author);
}
