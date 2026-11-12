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
        <h1>Add New Book</h1>
        <p>Enter the information below to add a book to your library.</p>

        <#if errorMessage??>
            <p class="error-message">${errorMessage}</p>
        </#if>

        <form action="/provider/books/${book.id}/edit" method="post">

            <#if _csrf??>
                <input type="hidden"
                       name="${_csrf.parameterName}"
                       value="${_csrf.token}">
            </#if>

            <label for="libraryId">Select Library</label>
            <select id="libraryId" name="libraryId" required>
                <#if libraries??>
                    <#list libraries as library>
                        <option value="${library.id}">
                            ${library.name}
                        </option>
                    </#list>
                </#if>
            </select>

            <label for="title">Book Title</label>
            <input
                type="text"
                id="title"
                name="title"
                placeholder="Enter book title"
                maxlength="255"
                required>

            <label for="author">Author</label>
            <input
                type="text"
                id="author"
                name="author"
                placeholder="Enter author name"
                required>

            <label for="isbn">ISBN</label>
            <input
                type="text"
                id="isbn"
                name="isbn"
                placeholder="Enter ISBN"
                required>

            <label for="genre">Category / Genre</label>
            <input
                type="text"
                id="genre"
                name="genre"
                placeholder="Enter book category"
                required>

            <label for="publisher">Publisher</label>
            <input
                type="text"
                id="publisher"
                name="publisher"
                placeholder="Enter publisher">

            <label for="publicationYear">Publication Year</label>
            <input
                type="number"
                id="publicationYear"
                name="publicationYear"
                placeholder="Enter publication year"
                min="1">

            <label for="totalCopies">Number of Copies</label>
            <input
                type="number"
                id="totalCopies"
                name="totalCopies"
                min="1"
                value="1"
                required>

            <label for="description">Description</label>
            <textarea
                id="description"
                name="description"
                placeholder="Enter book description"
                rows="5"
                maxlength="2000"
                required></textarea>

            <label for="location">Book Location</label>
            <input
                type="text"
                id="location"
                name="location"
                placeholder="Example: Fiction - Shelf A"
                required>

            <button type="submit">Add Book</button>

            <a href="/provider/books">Cancel</a>

        </form>
    </main>

    <footer>
        <p>&copy; 2026 LibraryPal. All rights reserved.</p>
    </footer>

</div>
</body>
</html>
