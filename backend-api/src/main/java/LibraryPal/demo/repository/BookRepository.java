
package LibraryPal.demo.repository;

import LibraryPal.demo.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    // Find books across all libraries belonging to a coordinator.
    List<Book> findDistinctByLocalLibraryCoordinators_Id(Long coordinatorId);

    // Find books belonging to a particular library.
    List<Book> findByLocalLibraryId(Long libraryId);

    // Check ISBN uniqueness within a library.
    boolean existsByIsbnAndLocalLibraryId(
            String isbn, Long libraryId);

    boolean existsByIsbnAndLocalLibraryIdAndIdNot(
            String isbn, Long libraryId, Long id);

    // Count books in a particular library.
    long countByLocalLibraryId(Long libraryId);

    // Search books by title or author.
    List<Book> findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(
            String title, String author);
}
