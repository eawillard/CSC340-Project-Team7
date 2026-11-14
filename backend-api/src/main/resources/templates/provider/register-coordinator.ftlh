
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/styles.css">
    <title>Provider Registration - LibraryPal</title>
</head>

<body class="account-page">

<header>
    <h1>LibraryPal</h1>
    <p>Register your provider account.</p>
</header>

<main>
    <h2>Create Provider Account</h2>

    <#if error??>
        <p class="error-message">${error}</p>
    </#if>

    <form action="/provider/register" method="post">

        <div class="form-group">
            <label for="name">Full Name:</label>
            <input type="text"
                   id="name"
                   name="name"
                   placeholder="Enter your full name"
                   required>
        </div>

        <div class="form-group">
            <label for="email">Email:</label>
            <input type="email"
                   id="email"
                   name="email"
                   placeholder="you@example.com"
                   required>
        </div>

        <div class="form-group">
            <label for="password">Password:</label>
            <input type="password"
                   id="password"
                   name="password"
                   placeholder="Create a password"
                   required>
        </div>

        <button type="submit" class="btn btn-primary">
            Register
        </button>

    </form>

    <p>Already have an account?
        <a href="/login">Sign in</a>
    </p>

</main>

<footer>
    <p>&copy; 2026 LibraryPal</p>
</footer>

</body>
</html>
