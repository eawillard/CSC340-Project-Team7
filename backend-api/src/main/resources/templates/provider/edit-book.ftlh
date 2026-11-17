
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Book | LibraryPal</title>
    <link rel="stylesheet" href="/styles.css">
</head>

<body class="app-page">

<div class="container">

    <#include "/provider/provider-nav.ftlh">

    <main>

        <section class="section">
            <h1>Edit Book</h1>
            <p>Update the book details below.</p>
        </section>

        <#if errorMessage??>
            <p class="error-message">${errorMessage}</p>
        </#if>

        <section class="section">

            <div class="card">
                <div class="card-content">

                    <form action="/provider/books/${book.id}/edit" method="post">

                        <div class="form-group">
                            <label for="title">Book Title *</label>
                            <input
                                type="text"
                                id="title"
                                name="title"
                                value="${book.title!''}"
                                maxlength="255"
                                required>
                        </div>

                        <div class="form-group">
                            <label for="author">Author *</label>
                            <input
                                type="text"
                                id="author"
                                name="author"
                                value="${book.author!''}"
                                required>
                        </div>

                        <div class="form-group">
                            <label for="isbn">ISBN *</label>
                            <input
                                type="text"
                                id="isbn"
                                name="isbn"
                                value="${book.isbn!''}"
                                required>
                        </div>

                        <div class="form-group">
                            <label for="genre">Category / Genre *</label>
                            <input
                                type="text"
                                id="genre"
                                name="genre"
                                value="${book.genre!''}"
                                required>
                        </div>

                        <div class="form-group">
                            <label for="publisher">Publisher</label>
                            <input
                                type="text"
                                id="publisher"
                                name="publisher"
                                value="${book.publisher!''}">
                        </div>

                        <div class="form-group">
                            <label for="publicationYear">Publication Year</label>
                            <input
                                type="number"
                                id="publicationYear"
                                name="publicationYear"
                                min="1"
                                value="${book.publicationYear!''}">
                        </div>

                        <div class="form-group">
                            <label for="totalCopies">Number of Copies *</label>
                            <input
                                type="number"
                                id="totalCopies"
                                name="totalCopies"
                                min="1"
                                value="${book.totalCopies!'1'}"
                                required>
                        </div>

                        <div class="form-group">
                            <label for="description">Description *</label>
                            <textarea
                                id="description"
                                name="description"
                                rows="5"
                                maxlength="2000"
                                required>${book.description!''}</textarea>
                        </div>

                        <div class="form-group">
                            <label for="location">Library Location *</label>
                            <input
                                type="text"
                                id="location"
                                name="location"
                                value="${book.location!''}"
                                placeholder="Central Library"
                                required>
                        </div>

                        <button type="submit" class="btn btn-primary">
                            Save Changes
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
