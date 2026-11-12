
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/styles.css">
    <title>Manage Books | LibraryPal</title>
</head>

<body class="app-page">

<div class="container">

    <#include "/provider/provider-nav.ftlh">

    <main>

        <section>
            <h1>Manage Your Books</h1>
            <p>Add, edit, and manage books in your library collection.</p>
        </section>

        <#if successMessage??>
            <p class="success-message">${successMessage}</p>
        </#if>

        <#if errorMessage??>
            <p class="error-message">${errorMessage}</p>
        </#if>

        <section>

            <div>
                <h2>Your Library Collection</h2>
                <a href="/provider/books/new">+ Add New Book</a>
            </div>

            <#if books?has_content>

                <#list books as book>

                    <article>

                        <h3>${book.title?html}</h3>

                        <p>
                            <strong>Author:</strong>
                            ${book.author?html}
                        </p>

                        <p>
                            <strong>ISBN:</strong>
                            ${book.isbn?html}
                        </p>

                        <p>
                            <strong>Category:</strong>
                            ${book.genre?html}
                        </p>

                        <p>
                            <strong>Copies:</strong>
                            ${book.totalCopies}
                        </p>

                        <p>
                            <strong>Status:</strong>

                            <#if book.availableCopies gt 0>
                                Available
                            <#else>
                                Checked Out
                            </#if>
                        </p>

                        <p>
                            <strong>Description:</strong>
                            ${book.description?html}
                        </p>

                        <a href="/provider/books/${book.id}/edit">
                            Edit
                        </a>

                        <form method="post"
                              action="/provider/books/${book.id}/delete"
                              onsubmit="return confirm('Are you sure you want to remove this book?');">

                            <button type="submit">
                                Remove
                            </button>

                        </form>

                    </article>

                    <hr>

                </#list>

            <#else>

                <p>You haven't added any books yet.</p>

                <a href="/provider/books/new">
                    Add Your First Book
                </a>

            </#if>

        </section>

        <section>
            <h2>Library Statistics</h2>

            <div>
                <h3>Total Book Records</h3>
                <p>${totalBooks!0}</p>
            </div>

            <div>
                <h3>Available Copies</h3>
                <p>${availableCopies!0}</p>
            </div>

            <div>
                <h3>Checked Out Copies</h3>
                <p>${checkedOutCopies!0}</p>
            </div>
        </section>

    </main>

    <footer>
        <p>&copy; 2026 LibraryPal. All rights reserved.</p>
    </footer>

</div>

</body>
</html>
