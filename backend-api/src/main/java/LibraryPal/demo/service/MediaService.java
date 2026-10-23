package LibraryPal.demo.service;

import LibraryPal.demo.entity.LocalLibrary;
import LibraryPal.demo.entity.Media;
import LibraryPal.demo.repository.LocalLibraryRepository;
import LibraryPal.demo.repository.MediaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class MediaService {

    private final MediaRepository mediaRepository;
    private final LocalLibraryRepository libraryRepository;

    public MediaService(
            MediaRepository mediaRepository,
            LocalLibraryRepository libraryRepository) {
        this.mediaRepository = mediaRepository;
        this.libraryRepository = libraryRepository;
    }

    public Media create(Media media, Long libraryId) {
        LocalLibrary library = libraryRepository.findById(libraryId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Library not found"));

        media.setLocalLibrary(library);
        return mediaRepository.save(media);
    }

    public List<Media> getAll() {
        return mediaRepository.findAll();
    }

    public List<Media> getAvailable() {
        return mediaRepository.findByAvailableTrue();
    }

    public Media getById(Long id) {
        return mediaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Media not found"));
    }

    public List<Media> getByLibrary(Long libraryId) {
        if (!libraryRepository.existsById(libraryId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Library not found");
        }

        return mediaRepository.findByLocalLibraryId(libraryId);
    }

    public Media update(Long id, Media updated, Long libraryId) {
        Media media = getById(id);

        LocalLibrary library = libraryRepository.findById(libraryId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Library not found"));

        media.setTitle(updated.getTitle());
        media.setLocation(updated.getLocation());
        media.setAvailable(updated.isAvailable());
        media.setLocalLibrary(library);

        return mediaRepository.save(media);
    }

    public void delete(Long id) {
        Media media = getById(id);
        mediaRepository.delete(media);
    }
}