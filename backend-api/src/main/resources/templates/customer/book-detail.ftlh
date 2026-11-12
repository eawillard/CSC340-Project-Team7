<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/styles.css">
    <link rel="stylesheet" href="/customer.css">
    <title>${book.title} | LibraryPal</title>
</head>
<body class="app-page">
<div class="container">

    <#include "/customer/customer-nav.ftlh">

    <main>
        <section>
            <h1>${book.title}</h1>
            <p><a href="/customer/books">&larr; Back to all books</a></p>
        </section>

        <#if errorMessage??>
            <p class="error-message">${errorMessage}</p>
        </#if>

        <section>
            <article>
                <p><strong>Author:</strong> ${book.author}</p>
                <p><strong>Genre:</strong> ${book.genre}</p>
                <p><strong>ISBN:</strong> ${book.isbn}</p>
                <p><strong>Library:</strong> ${book.localLibrary.name}</p>
                <p><strong>Shelf location:</strong> ${book.location}</p>
                <p>
                    <strong>Status:</strong>
                    <#if book.availableCopies gt 0>
                        Available (${book.availableCopies} of ${book.totalCopies} copies)
                    <#else>
                        Unavailable (all ${book.totalCopies} copies are checked out)
                    </#if>
                </p>
                <p>${book.description}</p>
            </article>
        </section>

        <section>
            <h2>Schedule a Pickup or Drop-off</h2>

            <form method="post" action="/customer/books/${book.id?c}/request">
                <label for="requestType">Request type:</label>
                <select id="requestType" name="requestType" required>
                    <option value="PICKUP">Pick up this book</option>
                    <option value="DROP_OFF">Drop off this book</option>
                </select>

                <label for="scheduleDate">Date:</label>
                <input type="date" id="scheduleDate" name="scheduleDate"
                       min="${today}" required>

                <button type="submit">Schedule</button>
            </form>
        </section>
    </main>
</div>
</body>
</html>
