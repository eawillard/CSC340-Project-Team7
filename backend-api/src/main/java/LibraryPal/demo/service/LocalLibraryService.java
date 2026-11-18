
package LibraryPal.demo.service;

import LibraryPal.demo.entity.Coordinator;
import LibraryPal.demo.entity.LocalLibrary;
import LibraryPal.demo.repository.CoordinatorRepository;
import LibraryPal.demo.repository.LocalLibraryRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class LocalLibraryService {

    private final LocalLibraryRepository libraryRepository;
    private final CoordinatorRepository coordinatorRepository;

    public LocalLibraryService(
            LocalLibraryRepository libraryRepository,
            CoordinatorRepository coordinatorRepository) {

        this.libraryRepository = libraryRepository;
        this.coordinatorRepository = coordinatorRepository;
    }

    // All coordinators can see all registered libraries
    public List<LocalLibrary> getAll() {
        return libraryRepository.findAll();
    }

    // Get libraries assigned to a particular coordinator
    public List<LocalLibrary> getByCoordinator(Long coordinatorId) {

        return libraryRepository
                .findDistinctByCoordinators_Id(coordinatorId);
    }

    // Find one library
    public LocalLibrary getById(Long id) {

        return libraryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Library not found"));
    }

    
@Transactional
public void assignCoordinator(
        Long libraryId,
        Long coordinatorId) {

    LocalLibrary library = getById(libraryId);

    Coordinator coordinator = coordinatorRepository
            .findById(coordinatorId)
            .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Coordinator not found"));

    boolean alreadyAssigned = library.getCoordinators()
            .stream()
            .anyMatch(existing ->
                    existing.getId().equals(coordinatorId));

    if (!alreadyAssigned) {
        library.getCoordinators().add(coordinator);
        coordinator.getLibraries().add(library);

        libraryRepository.save(library);
    }
}

    // Create a library and assign its creator
    
@Transactional
public LocalLibrary create(
        LocalLibrary library,
        Long coordinatorId) {

    Coordinator coordinator = coordinatorRepository
            .findById(coordinatorId)
            .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Coordinator not found"));

    LocalLibrary saved = libraryRepository.save(library);

    saved.getCoordinators().add(coordinator);
    coordinator.getLibraries().add(saved);

    return libraryRepository.save(saved);
}

    @Transactional
    public LocalLibrary update(Long id, LocalLibrary updated) {

        LocalLibrary library = getById(id);

        library.setName(updated.getName());
        library.setAddress(updated.getAddress());

        return libraryRepository.save(library);
    }
    // delete a library and remove its coordinator associations
    @Transactional
public void delete(Long id) {

    LocalLibrary library = getById(id);

    // Remove coordinator associations first
    library.getCoordinators().clear();

    // Delete the library
    libraryRepository.delete(library);
}
    // Create a library without assigning a coordinator
    public LocalLibrary create(LocalLibrary library) {
    return libraryRepository.save(library);
}
}
