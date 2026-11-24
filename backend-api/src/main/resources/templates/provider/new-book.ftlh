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
            <p>Enter the book details below.</p>

            <a href="/provider/media/new" class="btn btn-outline">
                Add Other Media (DVDs, CDs, etc.)
            </a>
        </section>

        <#if errorMessage??>
            <p class="error-message">${errorMessage}</p>
        </#if>

        <section class="section">

            <div class="card">
                <div class="card-content">

                    <form action="/provider/books/new" method="post">

                        <div class="form-group">
                            <label for="title">Book Title *</label>
                            <input
                                type="text"
                                id="title"
                                name="title"
                                value="${formTitle!''}"
                                maxlength="255"
                                required>
                        </div>

                        <div class="form-group">
                            <label for="author">Author *</label>
                            <input
                                type="text"
                                id="author"
                                name="author"
                                value="${formAuthor!''}"
                                required>
                        </div>

                        <div class="form-group">
                            <label for="isbn">ISBN *</label>
                            <input
                                type="text"
                                id="isbn"
                                name="isbn"
                                value="${formIsbn!''}"
                                required>
                        </div>

                        <div class="form-group">
                            <label for="genre">Category / Genre *</label>
                            <input
                                type="text"
                                id="genre"
                                name="genre"
                                value="${formGenre!''}"
                                required>
                        </div>

                        <div class="form-group">
                            <label for="publisher">Publisher</label>
                            <input
                                type="text"
                                id="publisher"
                                name="publisher"
                                value="${formPublisher!''}">
                        </div>

                        <div class="form-group">
                            <label for="publicationYear">Publication Year</label>
                            <input
                                type="number"
                                id="publicationYear"
                                name="publicationYear"
                                min="1"
                                value="${formPublicationYear!''}">
                        </div>

                        <div class="form-group">
                            <label for="totalCopies">Number of Copies *</label>
                            <input
                                type="number"
                                id="totalCopies"
                                name="totalCopies"
                                min="1"
                                value="${formTotalCopies!'1'}"
                                required>
                        </div>

                        <div class="form-group">
                            <label for="description">Description *</label>
                            <textarea
                                id="description"
                                name="description"
                                rows="5"
                                maxlength="2000"
                                required>${formDescription!''}</textarea>
                        </div>

                        <div class="form-group">
                            <label for="libraryId">Library Location *</label>
                            <select id="libraryId" name="libraryId" required>
                                <option value="">Select a library</option>
                                <#list libraries as library>
                                    <option value="${library.id}"<#if formLibraryId?? && formLibraryId?string == library.id?string> selected</#if>>
                                        ${library.name}
                                    </option>
                                </#list>
                            </select>
                        </div>

                        <button type="submit" class="btn btn-primary">
                            Save Book
                        </button>

                        <a href="/provider/books"
                           class="btn btn-outline">
                            Cancel
                        </a>

                    </form>

                </div>
            </div>

        </section>

    </main>

    <footer>
        <p>&copy; 2026 LibraryPal. All rights reserved.</p>
    </footer>

</div>

</body>
</html>
