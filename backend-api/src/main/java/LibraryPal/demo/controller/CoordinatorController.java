package LibraryPal.demo.controller;

import LibraryPal.demo.dto.CoordinatorCreateRequest;
import LibraryPal.demo.dto.CoordinatorResponse;
import LibraryPal.demo.dto.CoordinatorUpdateRequest;
import LibraryPal.demo.entity.Coordinator;
import LibraryPal.demo.service.CoordinatorService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coordinators")
public class CoordinatorController {

    private final CoordinatorService coordinatorService;
        public CoordinatorController(CoordinatorService coordinatorService) {
        this.coordinatorService = coordinatorService;
    }

    @PostMapping
    public ResponseEntity<CoordinatorResponse> createCoordinator(
            @Valid @RequestBody CoordinatorCreateRequest body) {

        Coordinator coordinator = new Coordinator();
        coordinator.setName(body.name());
        coordinator.setEmail(body.email());
        coordinator.setPassword(body.password());
        coordinator.setAccountStatus(body.accountStatus());

        Coordinator created =
                coordinatorService.createCoordinator(coordinator);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CoordinatorResponse.from(created));
    }

    @GetMapping
    public List<CoordinatorResponse> getAllCoordinators() {
        return coordinatorService.getAllCoordinators()
                .stream()
                .map(CoordinatorResponse::from)
                .toList();
    }

    @GetMapping("/{id}")
    public CoordinatorResponse getCoordinatorById(
            @PathVariable Long id) {
        return coordinatorService.getCoordinatorById(id)
                .map(CoordinatorResponse::from)
                .orElseThrow(() ->
                        new org.springframework.web.server
                                .ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Coordinator not found"));
    }

    @PutMapping("/{id}")
    public CoordinatorResponse updateCoordinator(
            @PathVariable Long id,
            @Valid @RequestBody CoordinatorUpdateRequest body) {

        Coordinator updated = new Coordinator();
        updated.setName(body.name());
        updated.setEmail(body.email());
        updated.setPassword(body.password());
        updated.setAccountStatus(body.accountStatus());

        return coordinatorService.updateCoordinator(id, updated)
                .map(CoordinatorResponse::from)
                .orElseThrow(() ->
                        new org.springframework.web.server
                                .ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Coordinator not found"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCoordinator(
            @PathVariable Long id) {

        if (!coordinatorService.deleteCoordinator(id)) {
            throw new org.springframework.web.server
                    .ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Coordinator not found");
        }

        return ResponseEntity.noContent().build();
    }

}