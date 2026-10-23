package LibraryPal.demo.controller;

import LibraryPal.demo.dto.LocalLibraryRequest;
import LibraryPal.demo.entity.LocalLibrary;
import LibraryPal.demo.service.LocalLibraryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/libraries")
public class LocalLibraryController {

    private final LocalLibraryService service;

    public LocalLibraryController(LocalLibraryService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<LocalLibrary> create(
            @Valid @RequestBody LocalLibraryRequest body) {
        LocalLibrary library = new LocalLibrary();
        library.setName(body.getName());
        library.setAddress(body.getAddress());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.create(library));
    }

    @GetMapping
    public List<LocalLibrary> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public LocalLibrary getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public LocalLibrary update(
            @PathVariable Long id,
            @Valid @RequestBody LocalLibraryRequest body) {
        LocalLibrary library = new LocalLibrary();
        library.setName(body.getName());
        library.setAddress(body.getAddress());

        return service.update(id, library);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}