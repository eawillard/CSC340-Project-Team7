package LibraryPal.demo.service;

import LibraryPal.demo.entity.Coordinator;
import LibraryPal.demo.repository.CoordinatorRepository;
import LibraryPal.demo.repository.ServiceRequestRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CoordinatorService {
private final CoordinatorRepository coordinatorRepository;
    private final ServiceRequestRepository serviceRequestRepository;

    public CoordinatorService(
            CoordinatorRepository coordinatorRepository,
            ServiceRequestRepository serviceRequestRepository) {
        this.coordinatorRepository = coordinatorRepository;
        this.serviceRequestRepository = serviceRequestRepository;
    }
    public Coordinator createCoordinator(Coordinator coordinator) {
        if (coordinatorRepository.existsByEmail(coordinator.getEmail())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Email already exists"
            );
        }

        return coordinatorRepository.save(coordinator);
    }
    public List<Coordinator> getAllCoordinators() {
        return coordinatorRepository.findAll();
    }

    public Optional<Coordinator> getCoordinatorById(Long id) {
        return coordinatorRepository.findById(id);
    }

    public Optional<Coordinator> updateCoordinator(
            Long id, Coordinator updatedCoordinator) {

        return coordinatorRepository.findById(id).map(coordinator -> {
            if (!coordinator.getEmail().equals(updatedCoordinator.getEmail())
                    && coordinatorRepository.existsByEmail(
                            updatedCoordinator.getEmail())) {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        "Email already exists"
                );
            }

            coordinator.setName(updatedCoordinator.getName());
            coordinator.setEmail(updatedCoordinator.getEmail());
            coordinator.setAccountStatus(
                    updatedCoordinator.getAccountStatus());

            if (updatedCoordinator.getPassword() != null
                    && !updatedCoordinator.getPassword().isBlank()) {
                coordinator.setPassword(updatedCoordinator.getPassword());
            }

            return coordinatorRepository.save(coordinator);
        });
    }
    public boolean deleteCoordinator(Long id) {
        if (!coordinatorRepository.existsById(id)) {
            return false;
        }

        coordinatorRepository.deleteById(id);
        return true;
    }
    public Map<String, Long> getStatistics(Long coordinatorId) {
        if (!coordinatorRepository.existsById(coordinatorId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Coordinator not found"
            );
        }
        Map<String, Long> stats = new LinkedHashMap<>();

        stats.put("totalRequests", serviceRequestRepository.count());
        stats.put("pending",
                serviceRequestRepository.countByStatusIgnoreCase("PENDING"));
        stats.put("approved",
                serviceRequestRepository.countByStatusIgnoreCase("APPROVED"));
        stats.put("completed",
                serviceRequestRepository.countByStatusIgnoreCase("COMPLETED"));
        stats.put("cancelled",
                serviceRequestRepository.countByStatusIgnoreCase("CANCELLED"));

        return stats;
    }
}