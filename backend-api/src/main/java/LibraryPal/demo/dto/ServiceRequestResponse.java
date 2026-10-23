package LibraryPal.demo.dto;

import LibraryPal.demo.entity.ServiceRequest;

import java.time.LocalDate;

public record ServiceRequestResponse(
        Long id,
        String status,
        String location,
        LocalDate scheduleDate,
        LocalDate dateAdded,
        String requestType
) {
    public static ServiceRequestResponse from(
            ServiceRequest request) {

        return new ServiceRequestResponse(
                request.getId(),
                request.getStatus(),
                request.getLocation(),
                request.getScheduleDate(),
                request.getDateAdded(),
                request.getRequestType()
        );
    }
}
