
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Books | LibraryPal</title>
    <link rel="stylesheet" href="/styles.css">
</head>

<body class="app-page">
<div class="container">

    <#include "/provider/provider-nav.ftlh">

    <main>

        <section class="section">
            <h1>Manage Your Books</h1>
            <p>Add, edit, and manage books across your libraries.</p>

            <a href="/provider/books/new" class="btn btn-primary">
                + Add New Book
            </a>
        </section>

        <#if successMessage??>
            <p class="success-message">${successMessage}</p>
        </#if>

        <#if errorMessage??>
            <p class="error-message">${errorMessage}</p>
        </#if>

        <section class="section">
            <h2>Collection Statistics</h2>

            <div class="dashboard-grid">

                <div class="stat-card">
                    <div class="stat-number">${totalBooks!0}</div>
                    <div class="stat-label">Book Records</div>
                </div>

                <div class="stat-card">
                    <div class="stat-number">${availableCopies!0}</div>
                    <div class="stat-label">Available Copies</div>
                </div>

                <div class="stat-card">
                    <div class="stat-number">${checkedOutCopies!0}</div>
                    <div class="stat-label">Checked Out Copies</div>
                </div>

            </div>
        </section>

        <section class="section">
            <h2>Your Library Collection</h2>

            <#if books?? && books?has_content>

                <#list books as book>

                    <article class="card" style="margin-bottom: 1.5rem;">
                        <div class="card-content">

                            <h3>${book.title}</h3>

                            <p>
                                <strong>Author:</strong>
                                ${book.author}
                            </p>

                            <p>
                                <strong>ISBN:</strong>
                                ${book.isbn}
                            </p>

                            <p>
                                <strong>Genre:</strong>
                                ${book.genre!'Not provided'}
                            </p>

                            <p>
                                <strong>Publisher:</strong>
                                ${book.publisher!'Not provided'}
                            </p>

                            <p>
                                <strong>Publication Year:</strong>
                                ${book.publicationYear!'Not provided'}
                            </p>

                            <p>
                                <strong>Library:</strong>
                                ${book.localLibrary.name!'Not provided'}
                            </p>

                            <p>
                                <strong>Total Copies:</strong>
                                ${book.totalCopies}
                            </p>

                            <p>
                                <strong>Available Copies:</strong>
                                ${book.availableCopies}
                            </p>

                            <p>
                                <strong>Status:</strong>

                                <#if book.availableCopies gt 0>
                                    <span class="success-message">Available</span>
                                <#else>
                                    <span class="error-message">Checked Out</span>
                                </#if>
                            </p>

                            <p>
                                <strong>Location:</strong>
                                ${book.location}
                            </p>

                            <p>
                                <strong>Description:</strong>
                                ${book.description}
                            </p>

                            <div style="display:flex; gap:1rem; flex-wrap:wrap; margin-top:1rem;">

                                <a href="/provider/books/${book.id}/edit"
                                   class="btn btn-primary">
                                    Edit Book
                                </a>

                                <form method="post"
                                      action="/provider/books/${book.id}/delete"
                                      onsubmit="return confirm('Are you sure you want to delete this book?');">

                                    <#if _csrf??>
                                        <input type="hidden"
                                               name="${_csrf.parameterName}"
                                               value="${_csrf.token}">
                                    </#if>

                                    <button type="submit" class="btn btn-outline">
                                        Delete Book
                                    </button>
                                </form>

                            </div>

                        </div>
                    </article>

                </#list>

            <#else>

                <div class="card">
                    <div class="card-content">
                        <h3>No Books Yet</h3>
                        <p>You haven't added any books to your collection.</p>

                        <a href="/provider/books/new"
                           class="btn btn-primary">
                            Add Your First Book
                        </a>
                    </div>
                </div>

            </#if>
        </section>

    </main>

    <footer>
        <p>&copy; 2026 LibraryPal. All rights reserved.</p>
    </footer>

</div>
</body>
</html>
