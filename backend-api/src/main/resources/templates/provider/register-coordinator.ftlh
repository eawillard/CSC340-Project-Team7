
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Provider Registration | LibraryPal</title>
    <link rel="stylesheet" href="/styles.css">
</head>

<body class="account-page">

<header>
    <h1>LibraryPal</h1>
    <p>Create your provider account.</p>
</header>

<main>

    <h2>Register as a Provider</h2>

    <#if error??>
    <p class="error-message">${error}</p>
</#if>

    <form action="/provider/register" method="post">

        <div class="form-group">
            <label for="name">Full Name</label>
            <input
                type="text"
                id="name"
                name="name"
                placeholder="Enter your full name"
                required>
        </div>

        <div class="form-group">
            <label for="email">Email Address</label>
            <input
                type="email"
                id="email"
                name="email"
                placeholder="you@example.com"
                required>
        </div>

        <div class="form-group">
            <label for="password">Password</label>
            <input
                type="password"
                id="password"
                name="password"
                placeholder="Create a password"
                required>
        </div>

        <button type="submit" class="btn btn-primary">
            Create Provider Account
        </button>

    </form>

    <p>
        Already have an account?
        <a href="/login">Sign in</a>
    </p>

    <p>
        <a href="/">Return to Home</a>
    </p>

</main>

<footer>
    <p>&copy; 2026 LibraryPal. All rights reserved.</p>
</footer>

</body>
</html>
