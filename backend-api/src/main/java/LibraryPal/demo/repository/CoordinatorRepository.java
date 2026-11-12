
package LibraryPal.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import LibraryPal.demo.entity.Coordinator;

import java.util.Optional;

public interface CoordinatorRepository
        extends JpaRepository<Coordinator, Long> {

    boolean existsByEmail(String email);

    Optional<Coordinator> findByEmail(String email);
}
