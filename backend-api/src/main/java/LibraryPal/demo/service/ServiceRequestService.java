package LibraryPal.demo.service;

import LibraryPal.demo.entity.ServiceRequest;
import LibraryPal.demo.repository.ServiceRequestRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class ServiceRequestService {

    private final ServiceRequestRepository repository;

    public ServiceRequestService(
            ServiceRequestRepository repository) {
        this.repository = repository;
    }

    // Create a new service request.
    public ServiceRequest createRequest(ServiceRequest request) {
        if (request.getStatus() == null
                || request.getStatus().isBlank()) {
            request.setStatus("PENDING");
        }

        if (request.getDateAdded() == null) {
            request.setDateAdded(LocalDate.now());
        }

        return repository.save(request);
    }

    // Retrieve all service requests.
    @Transactional(readOnly = true)
    public List<ServiceRequest> getAllRequests() {
        return repository.findAll();
    }

    // Retrieve one request or return HTTP 404.
    @Transactional(readOnly = true)
    public ServiceRequest getRequestById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Service request not found"));
    }

    // Update the status of an existing request.
    public ServiceRequest updateRequestStatus(
            Long id, String status) {

        ServiceRequest request = getRequestById(id);

        if (status == null || status.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Status must not be blank");
        }

        request.setStatus(status.trim().toUpperCase());

        return repository.save(request);
    }

    // Change the scheduled pickup or drop-off date.
    public ServiceRequest rescheduleRequest(
            Long id, LocalDate scheduleDate) {

        ServiceRequest request = getRequestById(id);

        if (scheduleDate == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Schedule date is required");
        }

        request.setScheduleDate(scheduleDate);

        return repository.save(request);
    }

    // Cancel an existing request.
    public ServiceRequest cancelRequest(Long id) {
        ServiceRequest request = getRequestById(id);

        request.setStatus("CANCELLED");

        return repository.save(request);
    }

    // Delete an existing request.
    public void deleteRequest(Long id) {
        ServiceRequest request = getRequestById(id);
        repository.delete(request);
    }
}