
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

    // Allow a coordinator to assign themselves to a library
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

        boolean alreadyAssigned = coordinator.getLibraries()
                .stream()
                .anyMatch(existing ->
                        existing.getId().equals(libraryId));

        if (!alreadyAssigned) {
            coordinator.getLibraries().add(library);
            coordinatorRepository.save(coordinator);
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

        coordinator.getLibraries().add(saved);

        coordinatorRepository.save(coordinator);

        return saved;
    }
    
    @Transactional
    public LocalLibrary update(Long id, LocalLibrary updated) {

        LocalLibrary library = getById(id);

        library.setName(updated.getName());
        library.setAddress(updated.getAddress());

        return libraryRepository.save(library);
    }

    @Transactional
    public void delete(Long id) {

        LocalLibrary library = getById(id);

        libraryRepository.delete(library);
    }
    // Create a library without assigning a coordinator
    public LocalLibrary create(LocalLibrary library) {
    return libraryRepository.save(library);
}
}
