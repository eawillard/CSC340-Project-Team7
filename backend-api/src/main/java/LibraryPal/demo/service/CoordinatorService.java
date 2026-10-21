package LibraryPal.demo.service;
import LibraryPal.demo.entity.Coordinator;
import LibraryPal.demo.repository.CoordinatorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CoordinatorService {
     private final CoordinatorRepository coordinatorRepository;

    public CoordinatorService(CoordinatorRepository coordinatorRepository) {
        this.coordinatorRepository = coordinatorRepository;
    }

    public Coordinator createCoordinator(Coordinator coordinator) {
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
            coordinator.setName(updatedCoordinator.getName());
            coordinator.setEmail(updatedCoordinator.getEmail());
            coordinator.setAccountStatus(updatedCoordinator.getAccountStatus());

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
}
