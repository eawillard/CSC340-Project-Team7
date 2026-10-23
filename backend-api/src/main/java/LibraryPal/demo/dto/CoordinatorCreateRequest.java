package LibraryPal.demo.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CoordinatorCreateRequest(
        @NotBlank String name,
        @NotBlank @Email String email,
        @NotBlank String password,
        @NotBlank String accountStatus
) {
}