<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/styles.css">
    <link rel="stylesheet" href="/customer.css">
    <title>My Requests | LibraryPal</title>
</head>
<body class="app-page">
<div class="container">

    <#include "/customer/customer-nav.ftlh">

    <main>
        <section>
            <h1>My Requests</h1>
            <p>Your scheduled pickups and drop-offs.</p>
        </section>

        <#if successMessage??>
            <p class="success-message">${successMessage}</p>
        </#if>

        <#if errorMessage??>
            <p class="error-message">${errorMessage}</p>
        </#if>

        <section>
            <#if requests?has_content>
                <#list requests as r>
                    <article>
                        <h3>
                            <#if r.book??>${r.book.title}<#else>Book request</#if>
                        </h3>
                        <p>
                            <strong>Type:</strong>
                            <#if r.requestType == "PICKUP">Pickup<#else>Drop-off</#if>
                        </p>
                        <p><strong>Library:</strong> ${r.location}</p>
                        <p><strong>Date:</strong> ${r.scheduleDate}</p>
                        <p><strong>Status:</strong> ${r.status}</p>

                        <#if r.status == "PENDING" || r.status == "APPROVED">
                            <form method="post"
                                  action="/customer/requests/${r.id?c}/cancel"
                                  class="inline-form">
                                <button type="submit">Cancel Request</button>
                            </form>
                        </#if>
                    </article>
                </#list>
            <#else>
                <p class="muted">
                    You have no requests yet.
                    <a href="/customer/books">Browse books</a> to schedule a pickup.
                </p>
            </#if>
        </section>
    </main>
</div>
</body>
</html>
