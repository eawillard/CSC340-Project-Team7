
package LibraryPal.demo.repository;

import LibraryPal.demo.entity.BookService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookServiceRepository extends JpaRepository<BookService, Long> {
}
