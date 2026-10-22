package LibraryPal.demo.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import LibraryPal.demo.entity.Coordinator;
    
public interface CoordinatorRepository extends JpaRepository<Coordinator, Long> {
    boolean existsByEmail(String email);
}
