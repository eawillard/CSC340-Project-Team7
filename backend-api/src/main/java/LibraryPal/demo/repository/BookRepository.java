package LibraryPal.demo.repository;

import LibraryPal.demo.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByLocalLibraryId(Long libraryId);

    boolean existsByIsbnAndLocalLibraryId(
            String isbn, Long libraryId);

    boolean existsByIsbnAndLocalLibraryIdAndIdNot(
            String isbn, Long libraryId, Long id);

    long countByLocalLibraryId(Long libraryId);
}
