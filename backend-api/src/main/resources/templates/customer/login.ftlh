<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/styles.css">
    <link rel="stylesheet" href="/customer.css">
    <title>Customer Login | LibraryPal</title>
</head>
<body class="account-page">
    <header>
        <h1>LibraryPal</h1>
        <p>Connect with your local library.</p>
    </header>

    <main>
        <h2>Customer Login</h2>

        <#if successMessage??>
            <p class="success-message">${successMessage}</p>
        </#if>

        <#if errorMessage??>
            <p class="error-message">${errorMessage}</p>
        </#if>

        <form method="post" action="/customer/login">
            <label for="username">Username:</label>
            <input type="text" id="username" name="username"
                   value="${username!""}" required>

            <br><br>

            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required>

            <br><br>

            <button type="submit">Sign In</button>
        </form>

        <p>Don't have an account?
            <a href="/customer/register">Create an account</a>
        </p>
    </main>

    <footer>
        <p>&copy; 2026 LibraryPal</p>
    </footer>
</body>
</html>
