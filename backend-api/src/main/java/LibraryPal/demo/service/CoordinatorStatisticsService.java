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
    private final ServiceRequestRepository serviceRequestRepository;

    public CoordinatorStatisticsService(
            CoordinatorRepository coordinatorRepository,
            ServiceRequestRepository serviceRequestRepository) {
        this.coordinatorRepository = coordinatorRepository;
        this.serviceRequestRepository = serviceRequestRepository;
    }

    public CoordinatorStatisticsResponse getStatistics(Long coordinatorId) {

        if (!coordinatorRepository.existsById(coordinatorId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Coordinator not found"
            );
        }
        long totalRequests = serviceRequestRepository.count();
        long pendingRequests =
                serviceRequestRepository.countByStatusIgnoreCase("PENDING");
        long approvedRequests =
                serviceRequestRepository.countByStatusIgnoreCase("APPROVED");
        long completedRequests =
                serviceRequestRepository.countByStatusIgnoreCase("COMPLETED");
        long cancelledRequests =
                serviceRequestRepository.countByStatusIgnoreCase("CANCELLED");

        return new CoordinatorStatisticsResponse(
                totalRequests,
                pendingRequests,
                approvedRequests,
                completedRequests,
                cancelledRequests
        );
    }
}