package LibraryPal.demo.controller;

import LibraryPal.demo.dto.ServiceRequestCreateRequest;
import LibraryPal.demo.entity.ServiceRequest;
import LibraryPal.demo.service.ServiceRequestService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/service-requests")
public class ServiceRequestController {

   public static class RequestStatusUpdate {
    @NotBlank
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

public static class RequestReschedule {
    @NotNull
    private LocalDate scheduleDate;

    public LocalDate getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(LocalDate scheduleDate) {
        this.scheduleDate = scheduleDate;
    }
    }

    private final ServiceRequestService service;

    public ServiceRequestController(ServiceRequestService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ServiceRequest> createRequest(
            @Valid @RequestBody ServiceRequestCreateRequest body) {

        ServiceRequest request = new ServiceRequest();
        request.setLocation(body.getLocation());
        request.setScheduleDate(body.getScheduleDate());
        request.setRequestType(body.getRequestType());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.createRequest(request));
    }

    @GetMapping
    public List<ServiceRequest> getAllRequests() {
        return service.getAllRequests();
    }

    @GetMapping("/{id}")
    public ServiceRequest getRequestById(@PathVariable Long id) {
        return service.getRequestById(id);
    }

    @PutMapping("/{id}/status")
    public ServiceRequest updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody RequestStatusUpdate body) {
        return service.updateRequestStatus(id, body.getStatus());
    }

    @PutMapping("/{id}/reschedule")
    public ServiceRequest reschedule(
            @PathVariable Long id,
            @Valid @RequestBody RequestReschedule body) {
        return service.rescheduleRequest(
                id, body.getScheduleDate());
    }

    @PutMapping("/{id}/cancel")
    public ServiceRequest cancel(@PathVariable Long id) {
        return service.cancelRequest(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRequest(@PathVariable Long id) {
        service.deleteRequest(id);
        return ResponseEntity.noContent().build();
    }
}