package LibraryPal.demo.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class ServiceRequestCreateRequest {

    @NotBlank
    private String location;

    @NotNull
    private LocalDate scheduleDate;

    @NotBlank
    private String requestType;
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public LocalDate getScheduleDate() {
        return scheduleDate;
    }
    public void setScheduleDate(LocalDate scheduleDate) {
        this.scheduleDate = scheduleDate;
    }
    public String getRequestType() {
        return requestType;
    }
    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }
}