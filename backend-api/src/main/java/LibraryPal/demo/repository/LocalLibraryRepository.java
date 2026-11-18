package LibraryPal.demo.repository;

import LibraryPal.demo.entity.LocalLibrary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocalLibraryRepository
        extends JpaRepository<LocalLibrary, Long> {

    // Find all libraries belonging to a coordinator.
    List<LocalLibrary> findDistinctByCoordinators_Id(Long coordinatorId);
}
