package LibraryPal.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
public class LocalLibrary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @NotBlank
    @Column(nullable = false)
    private String address;

   // Multiple coordinators can manage the same library.
@ManyToMany
@JoinTable(
    name = "coordinator_library",
    joinColumns = @JoinColumn(name = "library_id"),
    inverseJoinColumns = @JoinColumn(name = "coordinator_id")
)
    @JsonIgnore
    private List<Coordinator> coordinators = new ArrayList<>();

    // One library can contain multiple books.
    @OneToMany(mappedBy = "localLibrary")
    @JsonIgnore
    private List<Book> books = new ArrayList<>();

    // One library can contain multiple media items.
    @OneToMany(mappedBy = "localLibrary")
    @JsonIgnore
    private List<Media> media = new ArrayList<>();

    public LocalLibrary() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Coordinator> getCoordinators() {
    return coordinators;
    }

    public void setCoordinators(List<Coordinator> coordinators) {
    this.coordinators = coordinators;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public List<Media> getMedia() {
        return media;
    }

    public void setMedia(List<Media> media) {
        this.media = media;
    }
}
