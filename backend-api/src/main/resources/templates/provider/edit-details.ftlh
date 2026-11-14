<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/styles.css">
    <title>Edit Provider Details - LibraryPal</title>
</head>

<body>

<header>
    <h1>LibraryPal</h1>
    <p>Update your provider account.</p>

    <nav>
        <a href="/provider/profile">Profile</a>
        <a href="/provider/books">Manage Books</a>
        <a href="/logout">Logout</a>
    </nav>
</header>

<main>

    <h2>Edit Account Details</h2>

    <#if error??>
        <p class="error-message">${error}</p>
    </#if>

    <form action="/provider/edit-details" method="post">

        <div class="form-group">
            <label for="name">Full Name:</label>
            <input type="text"
                   id="name"
                   name="name"
                   value="${coordinator.name}"
                   required>
        </div>

        <div class="form-group">
            <label for="email">Email:</label>
            <input type="email"
                   id="email"
                   name="email"
                   value="${coordinator.email}"
                   required>
        </div>

        <div class="form-group">
            <label for="password">New Password:</label>
            <input type="password"
                   id="password"
                   name="password"
                   placeholder="Leave blank to keep current password">
        </div>

        <button type="submit" class="btn btn-primary">
            Save Changes
        </button>

    </form>

    <p>
        <a href="/provider/profile">Cancel and return to profile</a>
    </p>

</main>

<footer>
    <p>&copy; 2026 LibraryPal</p>
</footer>

</body>
</html>
