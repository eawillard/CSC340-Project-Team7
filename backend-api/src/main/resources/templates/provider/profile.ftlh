
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Provider Profile | LibraryPal</title>
    <link rel="stylesheet" href="/styles.css">
</head>

<body class="app-page">

<div class="container">

    <#include "/provider/provider-nav.ftlh">

    <main>

        <section class="section">

            <h1>Welcome, ${coordinator.name}!</h1>

            <#if RequestParameters.updated??>
                <p class="success-message">
                    Your profile has been updated successfully.
                </p>
            </#if>

            <h2>Account Information</h2>

            <div class="card">
                <div class="card-content">

                    <p>
                        <strong>Name:</strong>
                        ${coordinator.name}
                    </p>

                    <p>
                        <strong>Email:</strong>
                        ${coordinator.email}
                    </p>

                    <p>
                        <strong>Account Status:</strong>
                        ${coordinator.accountStatus}
                    </p>

                    <a href="/provider/edit-details"
                       class="btn btn-primary">
                        Edit Account Details
                    </a>

                </div>
            </div>

        </section>

        <section class="section">

            <h2>Your Libraries</h2>

            <#if libraries?? && libraries?has_content>

                <#list libraries as library>

                    <article class="card">
                        <div class="card-content">

                            <h3>${library.name}</h3>

                            <p>${library.address}</p>

                        </div>
                    </article>

                </#list>

            <#else>

                <p>
                    You do not currently have any libraries registered.
                </p>

            </#if>

        </section>

        <section class="section">

            <h2>Book Management</h2>

            <p>Manage the books in your collection.</p>

            <a href="/provider/books"
               class="btn btn-primary">
                Manage Your Books
            </a>

            <a href="/provider/books/new"
               class="btn btn-outline">
                Add a Book
            </a>

        </section>

        <section class="section">

            <h2>Account Management</h2>

            <p>
                Permanently delete your provider account.
            </p>

            <form action="/provider/delete-account"
                  method="post"
                  onsubmit="return confirm('Are you sure you want to delete your account?');">

                <button type="submit" class="btn btn-outline">
                    Delete Account
                </button>

            </form>

        </section>

    </main>

    <footer>
        <p>&copy; 2026 LibraryPal. All rights reserved.</p>
    </footer>

</div>

</body>
</html>
