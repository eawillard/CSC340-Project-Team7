package LibraryPal.demo.repository;
import LibraryPal.demo.entity.ServiceRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRequestRepository
 extends JpaRepository<ServiceRequest, Long> {
	long countByStatusIgnoreCase(String status);
}