
package LibraryPal.demo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class MediaRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String mediaType;

    private String creator;
    private String isbn;
    private String category;
    private String description;

    @NotNull
    @Min(1)
    private Integer copies;

    @NotBlank
    private String location;

    private Boolean available;

    @NotNull
    private Long libraryId;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getMediaType() { return mediaType; }
    public void setMediaType(String mediaType) { this.mediaType = mediaType; }

    public String getCreator() { return creator; }
    public void setCreator(String creator) { this.creator = creator; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getCopies() { return copies; }
    public void setCopies(Integer copies) { this.copies = copies; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public Boolean getAvailable() { return available; }
    public void setAvailable(Boolean available) { this.available = available; }

    public Long getLibraryId() { return libraryId; }
    public void setLibraryId(Long libraryId) { this.libraryId = libraryId; }
}
