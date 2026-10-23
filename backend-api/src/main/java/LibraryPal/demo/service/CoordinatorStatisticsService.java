package LibraryPal.demo.service;

import LibraryPal.demo.dto.CoordinatorStatisticsResponse;
import LibraryPal.demo.repository.CoordinatorRepository;
import LibraryPal.demo.repository.ServiceRequestRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CoordinatorStatisticsService {

    private final CoordinatorRepository coordinatorRepository;
    private final ServiceRequestRepository requestRepository;

    public CoordinatorStatisticsService(
            CoordinatorRepository coordinatorRepository,
            ServiceRequestRepository requestRepository) {
        this.coordinatorRepository = coordinatorRepository;
        this.requestRepository = requestRepository;
    }

    public CoordinatorStatisticsResponse getStatistics(Long id) {
        if (!coordinatorRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Coordinator not found");
        }

        long pending =
                requestRepository.countByStatusIgnoreCase("PENDING");
        long approved =
                requestRepository.countByStatusIgnoreCase("APPROVED");
        long completed =
                requestRepository.countByStatusIgnoreCase("COMPLETED");
        long cancelled =
                requestRepository.countByStatusIgnoreCase("CANCELLED");

        long total = requestRepository.count();

        return new CoordinatorStatisticsResponse(
                total, pending, approved, completed, cancelled);
    }
}