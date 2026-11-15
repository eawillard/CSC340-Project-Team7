
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Provider Dashboard | LibraryPal</title>
    <link rel="stylesheet" href="/styles.css">
</head>

<body class="app-page">

<div class="container">

    <#include "/provider/provider-nav.ftlh">

    <main>

        <section class="section">

            <div class="dashboard-header">

                <div class="dashboard-title">
                    Welcome Back, ${provider.name}
                </div>

                <div class="dashboard-subtitle">
                    Manage your library collection and help customers
                    discover available books.
                </div>

            </div>

            <div class="dashboard-grid">

                <div class="stat-card">
                    <div class="stat-number">
                        ${totalBooks!0}
                    </div>
                    <div class="stat-label">
                        Books in Collection
                    </div>
                </div>

                <div class="stat-card">
                    <div class="stat-number">
                        ${availableBooks!0}
                    </div>
                    <div class="stat-label">
                        Available Copies
                    </div>
                </div>

                <div class="stat-card">
                    <div class="stat-number">
                        ${pendingRequests!0}
                    </div>
                    <div class="stat-label">
                        Pending Requests
                    </div>
                </div>

                <div class="stat-card">
                    <div class="stat-number">
                        ${totalCustomers!0}
                    </div>
                    <div class="stat-label">
                        Customers
                    </div>
                </div>

            </div>

        </section>

        <section class="section">

            <h2>Quick Actions</h2>

            <div class="dashboard-grid">

                <div class="card">
                    <div class="card-content">

                        <h3>Manage Books</h3>

                        <p>
                            View and update your existing book collection.
                        </p>

                        <a href="/provider/books"
                           class="btn btn-primary">
                            Manage Books
                        </a>

                    </div>
                </div>

                <div class="card">
                    <div class="card-content">

                        <h3>Add New Book</h3>

                        <p>
                            Add a new book to your collection.
                        </p>

                        <a href="/provider/books/new"
                           class="btn btn-primary">
                            Add Book
                        </a>

                    </div>
                </div>

                <div class="card">
                    <div class="card-content">

                        <h3>My Profile</h3>

                        <p>
                            View and update your provider information.
                        </p>

                        <a href="/provider/profile"
                           class="btn btn-primary">
                            View Profile
                        </a>

                    </div>
                </div>

            </div>

        </section>

        <section class="section">

            <h2>Your Book Collection</h2>

            <#if books?? && books?has_content>

                <#list books as book>

                    <article class="card">

                        <div class="card-content">

                            <h3>${book.title}</h3>

                            <p>
                                Author: ${book.author}
                            </p>

                            <p>
                                Available Copies:
                                ${book.availableCopies}
                            </p>

                            <a href="/provider/books/${book.id}/edit"
                               class="btn btn-outline">
                                Edit Book
                            </a>

                        </div>

                    </article>

                </#list>

            <#else>

                <p>
                    You haven't added any books yet.
                </p>

                <a href="/provider/books/new"
                   class="btn btn-primary">
                    Add Your First Book
                </a>

            </#if>

        </section>

    </main>

    <footer>
        <p>&copy; 2026 LibraryPal. All rights reserved.</p>
    </footer>

</div>

</body>
</html>
