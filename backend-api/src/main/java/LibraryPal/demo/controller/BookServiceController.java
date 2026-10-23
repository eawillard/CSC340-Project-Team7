
package LibraryPal.demo.controller;

import LibraryPal.demo.entity.BookService;
import LibraryPal.demo.service.BookServiceService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book-services")
public class BookServiceController {

    private final BookServiceService bookServiceService;

    public BookServiceController(BookServiceService bookServiceService) {
        this.bookServiceService = bookServiceService;
    }

    @GetMapping
    public List<BookService> getAll() {
        return bookServiceService.findAll();
    }

    @GetMapping("/{id}")
    public BookService getById(@PathVariable Long id) {
        return bookServiceService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookService create(@Valid @RequestBody BookServiceCreateRequest request) {
        return bookServiceService.create(
                request.getCustomerId(),
                request.getServiceName(),
                request.getDescription()
        );
    }

    @PutMapping("/{id}")
    public BookService update(
            @PathVariable Long id,
            @Valid @RequestBody BookServiceUpdateRequest request) {
        return bookServiceService.update(
                id,
                request.getCustomerId(),
                request.getServiceName(),
                request.getDescription()
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable Long id,
            @RequestParam Long customerId) {
        bookServiceService.delete(id, customerId);
    }

    public static class BookServiceCreateRequest {
        @NotNull
        private Long customerId;

        @NotBlank
        private String serviceName;

        @NotBlank
        private String description;

        public Long getCustomerId() { return customerId; }
        public void setCustomerId(Long customerId) { this.customerId = customerId; }
        public String getServiceName() { return serviceName; }
        public void setServiceName(String serviceName) { this.serviceName = serviceName; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
    }

    public static class BookServiceUpdateRequest {
        @NotNull
        private Long customerId;

        private String serviceName;
        private String description;

        public Long getCustomerId() { return customerId; }
        public void setCustomerId(Long customerId) { this.customerId = customerId; }
        public String getServiceName() { return serviceName; }
        public void setServiceName(String serviceName) { this.serviceName = serviceName; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
    }
}
