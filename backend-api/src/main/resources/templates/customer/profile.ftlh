<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/styles.css">
    <link rel="stylesheet" href="/customer.css">
    <title>My Profile | LibraryPal</title>
</head>
<body class="app-page">
<div class="container">

    <#include "/customer/customer-nav.ftlh">

    <main>
        <section>
            <h1>My Profile</h1>
            <p>View and update your LibraryPal account information.</p>
        </section>

        <#if successMessage??>
            <p class="success-message">${successMessage}</p>
        </#if>

        <#if errorMessage??>
            <p class="error-message">${errorMessage}</p>
        </#if>

        <section>
            <h2>Personal Information</h2>

            <article>
                <p><strong>Name:</strong> ${customer.name}</p>
                <p><strong>Username:</strong> ${customer.username}</p>
            </article>
        </section>

        <section>
            <h2>Edit Profile</h2>

            <form method="post" action="/customer/profile">
                <label for="name">Name:</label>
                <input type="text" id="name" name="name"
                       value="${editName!customer.name}" required>

                <label for="username">Username:</label>
                <input type="text" id="username" name="username"
                       value="${editUsername!customer.username}" required>

                <button type="submit">Save Changes</button>
            </form>
        </section>
    </main>
</div>
</body>
</html>
