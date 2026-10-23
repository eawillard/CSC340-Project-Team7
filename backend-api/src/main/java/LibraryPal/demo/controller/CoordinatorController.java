package LibraryPal.demo.controller;
import LibraryPal.demo.entity.Coordinator;
import LibraryPal.demo.service.CoordinatorService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import LibraryPal.demo.dto.CoordinatorStatisticsResponse;
import LibraryPal.demo.service.CoordinatorStatisticsService;

@RestController
@RequestMapping("/api/coordinators")
public class CoordinatorController {
     private final CoordinatorService coordinatorService;

    // Create a coordinator profile
    @PostMapping
    public ResponseEntity<Coordinator> createCoordinator(
            @Valid @RequestBody Coordinator coordinator) {
        Coordinator created =
                coordinatorService.createCoordinator(coordinator);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // View all coordinator profiles
    @GetMapping
    public List<Coordinator> getAllCoordinators() {
        return coordinatorService.getAllCoordinators();
    }

    // View one coordinator profile
    @GetMapping("/{id}")
    public ResponseEntity<Coordinator> getCoordinatorById(
            @PathVariable Long id) {
        return coordinatorService.getCoordinatorById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Modify a coordinator profile
    @PutMapping("/{id}")
    public ResponseEntity<Coordinator> updateCoordinator(
            @PathVariable Long id,
            @Valid @RequestBody Coordinator coordinator) {
        return coordinatorService.updateCoordinator(id, coordinator)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Remove a coordinator profile
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCoordinator(
            @PathVariable Long id) {
        if (!coordinatorService.deleteCoordinator(id)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
    private final CoordinatorStatisticsService statisticsService;
    public CoordinatorController(
        CoordinatorService coordinatorService,
        CoordinatorStatisticsService statisticsService) {
        this.coordinatorService = coordinatorService;
        this.statisticsService = statisticsService;
    }
    @GetMapping("/{id}/statistics")
    public CoordinatorStatisticsResponse getStatistics(
        @PathVariable Long id) {
    return statisticsService.getStatistics(id);
}

}
