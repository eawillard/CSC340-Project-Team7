package LibraryPal.demo.repository;

import LibraryPal.demo.entity.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MediaRepository extends JpaRepository<Media, Long> {

    List<Media> findByAvailableTrue();

    List<Media> findByLocalLibraryId(Long libraryId);
}