<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/styles.css">
    <title>Provider Profile - LibraryPal</title>
</head>

<body>

<header>
    <h1>LibraryPal</h1>
    <p>Provider Dashboard</p>

    <nav>
        <a href="/provider/profile">Profile</a>
        <a href="/provider/books">Manage Books</a>
        <a href="/provider/edit-details">Edit Details</a>
        <a href="/logout">Logout</a>
    </nav>
</header>

<main>

    <h2>Welcome, ${coordinator.name}!</h2>

    <#if RequestParameters.updated??>
        <p class="success-message">
            Your profile has been updated successfully.
        </p>
    </#if>

    <section>
        <h3>Account Information</h3>

        <p><strong>Name:</strong> ${coordinator.name}</p>
        <p><strong>Email:</strong> ${coordinator.email}</p>
        <p><strong>Account Status:</strong> ${coordinator.accountStatus}</p>

        <a href="/provider/edit-details">
            Edit Account Details
        </a>
    </section>

    <section>
        <h3>Your Libraries</h3>

        <#if libraries?has_content>

            <#list libraries as library>
                <article>
                    <h4>${library.name}</h4>
                    <p>${library.address}</p>
                </article>
            </#list>

        <#else>
            <p>You do not currently have any libraries registered.</p>
        </#if>

    </section>

    <section>
        <h3>Book Management</h3>

        <p>Manage the books in your collection.</p>

        <a href="/provider/books">
            Manage Your Books
        </a>

    </section>

    <section>
        <h3>Account Management</h3>

        <form action="/provider/delete-account" method="post"
              onsubmit="return confirm('Are you sure you want to delete your account?');">

            <button type="submit" class="btn">
                Delete Account
            </button>

        </form>
    </section>

</main>

<footer>
    <p>&copy; 2026 LibraryPal</p>
</footer>

</body>
</html>
