<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/styles.css">
    <title>Login - LibraryPal</title>
</head>

<body class="account-page">

<header>
    <h1>LibraryPal</h1>
    <p>Connect with your local library.</p>
</header>

<main>
    <h2>Login</h2>

    <#if errorMessage??>
        <p class="error-message">${errorMessage}</p>
    </#if>

    <#if successMessage??>
        <p class="success-message">${successMessage}</p>
    </#if>

    <form action="/login" method="post">

        <div class="form-group">
            <label for="accountType">Account Type:</label>
            <select id="accountType" name="accountType" required>
                <option value="CUSTOMER">Customer</option>
                <option value="PROVIDER">Provider</option>
            </select>
        </div>

        <div class="form-group">
            <label for="identifier">Email or Username:</label>
            <input
                type="text"
                id="identifier"
                name="identifier"
                placeholder="Enter your email or username"
                value="${identifier!''}"
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

    <p>Don't have an account?</p>
    <p>
        Customer:
        <a href="/customer/register">Create a customer account</a>
    </p>
    <p>
        Provider:
        <a href="/provider/register">Create a provider account</a>
    </p>

</main>

<footer>
    <p>&copy; 2026 LibraryPal. All rights reserved.</p>
</footer>

</body>
</html>
