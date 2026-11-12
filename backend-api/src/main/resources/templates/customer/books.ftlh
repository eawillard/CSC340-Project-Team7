<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/styles.css">
    <link rel="stylesheet" href="/customer.css">
    <title>Browse Books | LibraryPal</title>
</head>
<body class="app-page">
<div class="container">

    <#include "/customer/customer-nav.ftlh">

    <main>
        <section>
            <h1>Browse Books</h1>
            <p>Welcome, ${customer.name}. Browse books available through local libraries.</p>
        </section>

        <#if successMessage??>
            <p class="success-message">${successMessage}</p>
        </#if>

        <#if errorMessage??>
            <p class="error-message">${errorMessage}</p>
        </#if>

        <section>
            <form method="get" action="/customer/books">
                <label for="q">Search by title or author:</label>
                <input type="search" id="q" name="q" value="${q}">
                <button type="submit">Search</button>
            </form>
        </section>

        <section>
            <h2>Available Books</h2>

            <#if books?has_content>
                <#list books as book>
                    <article>
                        <h3>${book.title}</h3>
                        <p><strong>Author:</strong> ${book.author}</p>
                        <p><strong>Genre:</strong> ${book.genre}</p>
                        <p><strong>Library:</strong> ${book.localLibrary.name}</p>
                        <p>
                            <strong>Status:</strong>
                            <#if book.availableCopies gt 0>
                                Available (${book.availableCopies} of ${book.totalCopies} copies)
                            <#else>
                                Unavailable
                            </#if>
                        </p>
                        <a href="/customer/books/${book.id?c}">View Book</a>
                    </article>
                </#list>
            <#else>
                <p class="muted">
                    <#if q?has_content>
                        No books match "${q}".
                    <#else>
                        No books have been added yet.
                    </#if>
                </p>
            </#if>
        </section>
    </main>
</div>
</body>
</html>
