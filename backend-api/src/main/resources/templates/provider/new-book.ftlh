
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add New Book | LibraryPal</title>
    <link rel="stylesheet" href="/styles.css">
</head>

<body class="app-page">
<div class="container">

    <#include "/provider/provider-nav.ftlh">

    <main>
        <section class="section">
            <h1>Add a New Book</h1>
            <p>Enter the book details below to add it to your library.</p>
        </section>

        <#if errorMessage??>
            <p class="error-message">${errorMessage}</p>
        </#if>

        <#if libraries?? && libraries?has_content>

            <section class="section">
                <section class="section">
    <h1>Add a New Book</h1>
    <p>Add a book to your provider collection.</p>
</section>

<#if errorMessage??>
    <p class="error-message">${errorMessage}</p>
</#if>

<section class="section">
    <div class="card">
        <div class="card-content">

            <form method="post" action="/provider/books/new">

                <#if _csrf??>
                    <input type="hidden"
                           name="${_csrf.parameterName}"
                           value="${_csrf.token}">
                            </#if>

                            <div class="form-group">
                                <label for="libraryId">Select Library *</label>
                                <select id="libraryId" name="libraryId" required>
                                    <#list libraries as library>
                                        <option value="${library.id}"
                                            <#if selectedLibraryId?? &&
                                                selectedLibraryId == library.id>
                                                selected
                                            </#if>>
                                            ${library.name}
                                        </option>
                                    </#list>
                                </select>
                            </div>

                            <div class="form-group">
                                <label for="title">Book Title *</label>
                                <input type="text" id="title" name="title"
                                       value="${formTitle!''}"
                                       maxlength="255" required>
                            </div>

                            <div class="form-group">
                                <label for="author">Author *</label>
                                <input type="text" id="author" name="author"
                                       value="${formAuthor!''}" required>
                            </div>

                            <div class="form-group">
                                <label for="isbn">ISBN *</label>
                                <input type="text" id="isbn" name="isbn"
                                       value="${formIsbn!''}" required>
                            </div>

                            <div class="form-group">
                                <label for="genre">Category / Genre *</label>
                                <input type="text" id="genre" name="genre"
                                       value="${formGenre!''}" required>
                            </div>

                            <div class="form-group">
                                <label for="publisher">Publisher</label>
                                <input type="text" id="publisher" name="publisher"
                                       value="${formPublisher!''}">
                            </div>

                            <div class="form-group">
                                <label for="publicationYear">Publication Year</label>
                                <input type="number" id="publicationYear"
                                       name="publicationYear" min="1"
                                       value="${formPublicationYear!''}">
                            </div>

                            <div class="form-group">
                                <label for="totalCopies">Number of Copies *</label>
                                <input type="number" id="totalCopies"
                                       name="totalCopies" min="1"
                                       value="${formTotalCopies!'1'}" required>
                            </div>

                            <div class="form-group">
                                <label for="description">Description *</label>
                                <textarea id="description" name="description"
                                          rows="5" maxlength="2000"
                                          required>${formDescription!''}</textarea>
                            </div>

                            <div class="form-group">
                                <label for="location">Book Location *</label>
                                <input type="text" id="location" name="location"
                                       value="${formLocation!''}"
                                       placeholder="Fiction - Shelf A" required>
                            </div>

                            <button type="submit" class="btn btn-primary">
                                Save Book
                            </button>

                            <a href="/provider/books" class="btn btn-outline">
                                Cancel
                            </a>

                        </form>
                    </div>
                </div>
            </section>

        <#else>

            <section class="section">
                <div class="card">
                    <div class="card-content">
                        <h2>No Libraries Available</h2>
                        <p>You must have a library before adding books.</p>
                        <a href="/provider/profile" class="btn btn-primary">
                            Return to Profile
                        </a>
                    </div>
                </div>
            </section>

        </#if>
    </main>

    <footer>
        <p>&copy; 2026 LibraryPal. All rights reserved.</p>
    </footer>

</div>
</body>
</html>
