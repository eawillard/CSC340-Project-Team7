package LibraryPal.demo.repository;

import LibraryPal.demo.entity.LocalLibrary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalLibraryRepository
        extends JpaRepository<LocalLibrary, Long> {
}
