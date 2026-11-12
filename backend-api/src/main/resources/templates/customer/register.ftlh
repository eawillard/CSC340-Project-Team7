<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/styles.css">
    <link rel="stylesheet" href="/customer.css">
    <title>Create Account | LibraryPal</title>
</head>
<body class="account-page">
    <header>
        <h1>LibraryPal</h1>
        <p>Connect with your local library.</p>
    </header>

    <main>
        <h2>Create a Customer Account</h2>

        <#if errorMessage??>
            <p class="error-message">${errorMessage}</p>
        </#if>

        <form method="post" action="/customer/register">
            <label for="name">Full name:</label>
            <input type="text" id="name" name="name"
                   value="${name!""}" required>

            <br><br>

            <label for="username">Username:</label>
            <input type="text" id="username" name="username"
                   value="${username!""}" required>

            <br><br>

            <label for="password">Password (at least 6 characters):</label>
            <input type="password" id="password" name="password" required>

            <br><br>

            <label for="confirmPassword">Confirm password:</label>
            <input type="password" id="confirmPassword"
                   name="confirmPassword" required>

            <br><br>

            <button type="submit">Create Account</button>
        </form>

        <p>Already have an account?
            <a href="/customer/login">Sign in</a>
        </p>
    </main>

    <footer>
        <p>&copy; 2026 LibraryPal</p>
    </footer>
</body>
</html>
