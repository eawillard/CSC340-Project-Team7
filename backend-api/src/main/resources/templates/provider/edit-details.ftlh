
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Details | LibraryPal</title>
    <link rel="stylesheet" href="/styles.css">
</head>
<body class="app-page">
<div class="container">

    <nav>
        <div class="navbar">
            <a href="/" class="navbar-brand">LibraryPal</a>
            <ul class="nav-links">
                <li><a href="/provider/profile">Profile</a></li>
                <li><a href="/provider/edit-details">Edit Details</a></li>
                <li><a href="/provider/register">Register Coordinator</a></li>
            </ul>
        </div>
    </nav>

    <main>
        <h1>Edit Personal Information</h1>
        <p>Update your LibraryPal account information.</p>

        <#if error??>
            <p class="error-message">${error}</p>
        </#if>

        <form action="/provider/edit-details" method="post">

            <#if _csrf??>
                <input type="hidden" name="_csrf" value="${_csrf.token}">
            </#if>

            <label for="name">Full Name</label>
            <input
                type="text"
                id="name"
                name="name"
                value="${coordinator.name}"
                required>

            <label for="email">Email Address</label>
            <input
                type="email"
                id="email"
                name="email"
                value="${coordinator.email}"
                required>

            <label for="password">New Password</label>
            <input
                type="password"
                id="password"
                name="password"
                placeholder="Leave blank to keep your current password">

            <button type="submit">Save Changes</button>

            <a href="/provider/profile">Cancel</a>
        </form>
    </main>

    <footer>
        <p>&copy; 2026 LibraryPal. All rights reserved.</p>
    </footer>

</div>
</body>
</html>
