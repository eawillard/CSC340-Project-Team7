
package LibraryPal.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(
    uniqueConstraints = @UniqueConstraint(
        columnNames = {"isbn", "local_library_id"}
    )
)
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String title;

    @NotBlank
    @Column(nullable = false)
    private String author;

    @NotBlank
    @Column(nullable = false)
    private String isbn;

    @NotBlank
    @Column(nullable = false)
    private String genre;

    private String publisher;

    private Integer publicationYear;

    @NotBlank
    @Column(nullable = false, length = 2000)
    private String description;

    @NotNull
    @Min(1)
    @Column(nullable = false)
    private Integer totalCopies;

    @NotNull
    @Min(0)
    @Column(nullable = false)
    private Integer availableCopies;

    @NotBlank
    @Column(nullable = false)
    private String location;

    // A book belongs directly to a coordinator.
    @ManyToOne
    @JoinColumn(name = "coordinator_id")
    @JsonIgnore
    private Coordinator coordinator;

    // A library can be assigned later.
    @ManyToOne
    @JoinColumn(name = "local_library_id", nullable = true)
    @JsonIgnore
    private LocalLibrary localLibrary;

    public Book() {
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getTotalCopies() {
        return totalCopies;
    }

    public void setTotalCopies(Integer totalCopies) {
        this.totalCopies = totalCopies;
    }

    public Integer getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(Integer availableCopies) {
        this.availableCopies = availableCopies;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Coordinator getCoordinator() {
        return coordinator;
    }

    public void setCoordinator(Coordinator coordinator) {
        this.coordinator = coordinator;
    }

    public LocalLibrary getLocalLibrary() {
        return localLibrary;
    }

    public void setLocalLibrary(LocalLibrary localLibrary) {
        this.localLibrary = localLibrary;
    }
}
