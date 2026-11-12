package LibraryPal.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(
    uniqueConstraints = @UniqueConstraint(
        columnNames = {"isbn", "coordinator_id"}
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

    @ManyToOne
    @JoinColumn(name = "local_library_id")
    @JsonIgnore
    private LocalLibrary localLibrary;

    @ManyToOne(optional = false)
    @JoinColumn(name = "coordinator_id", nullable = false)
    @JsonIgnore
    private Coordinator coordinator;

    public Coordinator getCoordinator() {
        return coordinator;
    }

    public void setCoordinator(Coordinator coordinator) {
        this.coordinator = coordinator;
    }

    public Book() {
    }

    public Long getId() { return id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public String getPublisher() { return publisher; }
    public void setPublisher(String publisher) { this.publisher = publisher; }

    public Integer getPublicationYear() { return publicationYear; }
    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getDescription() { return description; }
    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getTotalCopies() { return totalCopies; }
    public void setTotalCopies(Integer totalCopies) {
        this.totalCopies = totalCopies;
    }

    public Integer getAvailableCopies() { return availableCopies; }
    public void setAvailableCopies(Integer availableCopies) {
        this.availableCopies = availableCopies;
    }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public LocalLibrary getLocalLibrary() { return localLibrary; }
    public void setLocalLibrary(LocalLibrary localLibrary) {
        this.localLibrary = localLibrary;
    }
}
