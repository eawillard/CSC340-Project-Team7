package LibraryPal.demo.service;

import LibraryPal.demo.entity.LocalLibrary;
import LibraryPal.demo.repository.LocalLibraryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class LocalLibraryService {

    private final LocalLibraryRepository repository;
    public LocalLibraryService(LocalLibraryRepository repository) {
        this.repository = repository;
    }
    public LocalLibrary create(LocalLibrary library) {
        return repository.save(library);
    }
    public List<LocalLibrary> getAll() {
        return repository.findAll();
    }
    public LocalLibrary getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Library not found"));
    }
    public LocalLibrary update(Long id, LocalLibrary updated) {
        LocalLibrary library = getById(id);
        library.setName(updated.getName());
        library.setAddress(updated.getAddress());
        return repository.save(library);
    }
    public void delete(Long id) {
        LocalLibrary library = getById(id);
        repository.delete(library);
    }
}
