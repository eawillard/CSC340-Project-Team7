package LibraryPal.demo.dto;

import LibraryPal.demo.entity.Coordinator;

public record CoordinatorResponse(
        Long id,
        String name,
        String email,
        String accountStatus
) {
    public static CoordinatorResponse from(Coordinator coordinator) {
        return new CoordinatorResponse(
                coordinator.getId(),
                coordinator.getName(),
                coordinator.getEmail(),
                coordinator.getAccountStatus()
        );
    }
}