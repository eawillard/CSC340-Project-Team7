
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="stylesheet" href="/styles.css">

    <title>Add New Book | LibraryPal</title>
</head>

<body class="app-page">

<div class="container">

    <#include "/provider/provider-nav.ftlh">

    <main>

        <section>
            <h1>Add a New Book</h1>
            <p>Add a book to your library collection.</p>
        </section>

        <#if errorMessage??>
            <p class="error-message">${errorMessage}</p>
        </#if>

        <section>

            <div class="card">
                <div class="card-content">

                    <form method="post" action="/provider/books/new">

                        <div class="form-group">
                            <label for="title">Book Title</label>

                            <input
                                type="text"
                                id="title"
                                name="title"
                                required>
                        </div>

                        <div class="form-group">
                            <label for="author">Author</label>

                            <input
                                type="text"
                                id="author"
                                name="author"
                                required>
                        </div>

                        <div class="form-group">
                            <label for="isbn">ISBN</label>

                            <input
                                type="text"
                                id="isbn"
                                name="isbn"
                                required>
                        </div>

                        <div class="form-group">
                            <label for="genre">Category / Genre</label>

                            <input
                                type="text"
                                id="genre"
                                name="genre"
                                required>
                        </div>

                        <div class="form-group">
                            <label for="publisher">Publisher</label>

                            <input
                                type="text"
                                id="publisher"
                                name="publisher">
                        </div>

                        <div class="form-group">
                            <label for="publicationYear">Publication Year</label>

                            <input
                                type="number"
                                id="publicationYear"
                                name="publicationYear"
                                min="1">
                        </div>

                        <div class="form-group">
                            <label for="totalCopies">Number of Copies</label>

                            <input
                                type="number"
                                id="totalCopies"
                                name="totalCopies"
                                min="1"
                                value="1"
                                required>
                        </div>

                        <div class="form-group">
                            <label for="description">Description</label>

                            <textarea
                                id="description"
                                name="description"
                                rows="5"
                                required></textarea>
                        </div>

                        <div class="form-group">
                            <label for="location">Book Location</label>

                            <input
                                type="text"
                                id="location"
                                name="location"
                                placeholder="Fiction - Shelf A"
                                required>
                        </div>

                        <button type="submit" class="btn btn-primary">
                            Add Book
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
