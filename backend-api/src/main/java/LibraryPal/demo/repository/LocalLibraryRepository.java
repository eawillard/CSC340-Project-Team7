package LibraryPal.demo.repository;

import LibraryPal.demo.entity.LocalLibrary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocalLibraryRepository
        extends JpaRepository<LocalLibrary, Long> {
    List<LocalLibrary> findByCoordinatorId(Long coordinatorId);
}
