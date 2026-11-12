
package LibraryPal.demo.repository;

import LibraryPal.demo.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    // Find books belonging to a coordinator
    List<Book> findByCoordinatorId(Long coordinatorId);

    // Find books belonging to a library
    List<Book> findByLocalLibraryId(Long libraryId);

    // Check ISBN uniqueness for a coordinator
    boolean existsByIsbnAndCoordinatorId(
            String isbn, Long coordinatorId);

    boolean existsByIsbnAndCoordinatorIdAndIdNot(
            String isbn, Long coordinatorId, Long id);

    // Existing library-based methods
    boolean existsByIsbnAndLocalLibraryId(
            String isbn, Long libraryId);

    boolean existsByIsbnAndLocalLibraryIdAndIdNot(
            String isbn, Long libraryId, Long id);

    long countByLocalLibraryId(Long libraryId);

    // Search books
    List<Book> findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(
            String title, String author);
}
