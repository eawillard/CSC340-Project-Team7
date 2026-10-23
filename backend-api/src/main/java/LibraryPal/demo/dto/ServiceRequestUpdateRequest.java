package LibraryPal.demo.dto;
import jakarta.validation.constraints.NotBlank;

public class ServiceRequestUpdateRequest {
    
    @NotBlank
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
}
