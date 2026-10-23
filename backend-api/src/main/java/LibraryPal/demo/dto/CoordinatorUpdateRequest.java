package LibraryPal.demo.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
public record CoordinatorUpdateRequest(
        @NotBlank String name,
        @NotBlank @Email String email,
        String password,
        @NotBlank String accountStatus
) {
}

