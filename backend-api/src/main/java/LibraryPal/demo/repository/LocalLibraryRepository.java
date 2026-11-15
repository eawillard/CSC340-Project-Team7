package LibraryPal.demo.repository;

import LibraryPal.demo.entity.LocalLibrary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocalLibraryRepository
        extends JpaRepository<LocalLibrary, Long> {

    // Find libraries connected to a particular coordinator
    List<LocalLibrary> findByCoordinatorId(Long coordinatorId);

    // Find libraries that have not yet been assigned
    List<LocalLibrary> findByCoordinatorIsNull();

}
