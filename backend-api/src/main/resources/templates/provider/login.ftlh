
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="stylesheet" href="/styles.css">

    <title>Provider Login | LibraryPal</title>
</head>

<body class="account-page">

<header>
    <h1>LibraryPal</h1>
    <p>Manage your local library with LibraryPal.</p>
</header>

<main>

    <h2>Provider Login</h2>

    <#if errorMessage??>
        <p class="error-message">${errorMessage}</p>
    </#if>

    <#if successMessage??>
        <p class="success-message">${successMessage}</p>
    </#if>

    <form action="/login" method="post">

        <div class="form-group">
            <label for="identifier">Email:</label>

            <input
                type="email"
                id="identifier"
                name="identifier"
                value="${identifier!''}"
                placeholder="Enter your email"
                required>
        </div>

        <div class="form-group">
            <label for="password">Password:</label>

            <input
                type="password"
                id="password"
                name="password"
                placeholder="Enter your password"
                required>
        </div>

        <button type="submit" class="btn btn-primary">
            Sign In
        </button>

    </form>

    <p>Don't have a provider account?
        <a href="/provider/register">Register here</a>
    </p>

    <p>Are you a customer?
        <a href="/customer/login">Customer Login</a>
    </p>

</main>

<footer>
    <p>&copy; 2026 LibraryPal. All rights reserved.</p>
</footer>

</body>
</html>
