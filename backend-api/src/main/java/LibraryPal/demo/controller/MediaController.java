package LibraryPal.demo.controller;

import LibraryPal.demo.dto.MediaRequest;
import LibraryPal.demo.entity.Media;
import LibraryPal.demo.service.MediaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/media")
public class MediaController {

    private final MediaService service;

    public MediaController(MediaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Media> create(
            @Valid @RequestBody MediaRequest body) {
        Media media = new Media();
        media.setTitle(body.getTitle());
        media.setLocation(body.getLocation());
        media.setAvailable(
                body.getAvailable() == null || body.getAvailable());

        Media created = service.create(media, body.getLibraryId());

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public List<Media> getAll() {
        return service.getAll();
    }

    @GetMapping("/available")
    public List<Media> getAvailable() {
        return service.getAvailable();
    }

    @GetMapping("/{id}")
    public Media getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping("/library/{libraryId}")
    public List<Media> getByLibrary(
            @PathVariable Long libraryId) {
        return service.getByLibrary(libraryId);
    }

    @PutMapping("/{id}")
    public Media update(
            @PathVariable Long id,
            @Valid @RequestBody MediaRequest body) {
        Media media = new Media();
        media.setTitle(body.getTitle());
        media.setLocation(body.getLocation());
        media.setAvailable(
                body.getAvailable() == null || body.getAvailable());

        return service.update(id, media, body.getLibraryId());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}