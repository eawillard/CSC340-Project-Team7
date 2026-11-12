!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Profile | LibraryPal</title>
    <link rel="stylesheet" href="/styles.css">
</head>

<body class="app-page">

<div class="container">

    <!-- Navigation -->
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

        <!-- Profile Header -->
        <section>
            <h1>My Profile</h1>
            <p>Manage your LibraryPal account information.</p>

            <#if RequestParameters.updated??>
                <p class="success-message">
                    Your profile was successfully updated!
                </p>
            </#if>

            <#if RequestParameters.accountDeleted??>
                <p class="success-message">
                    Your account has been deleted.
                </p>
            </#if>
        </section>

        <!-- Personal Information -->
        <section>
            <h2>Personal Information</h2>

            <article>
                <h3>Personal Details</h3>

                <p><strong>Name:</strong> ${coordinator.name}</p>
                <p><strong>Email:</strong> ${coordinator.email}</p>
                <p><strong>Account Status:</strong> ${coordinator.accountStatus}</p>

                <a href="/provider/edit-details">
                    Edit Personal Information
                </a>
            </article>
        </section>

        <!-- Account Settings -->
        <section>
            <h2>Account Settings</h2>

            <article>
                <h3>Manage Account</h3>

                <p>
                    Update your account information or permanently
                    delete your account.
                </p>

                <p>
                    <a href="/provider/edit-details">
                        Edit Account
                    </a>
                </p>

                <form action="/provider/delete-account"
                      method="post"
                      onsubmit="return confirm('Are you sure you want to permanently delete your account?');">

                    <button type="submit">
                        Delete Account
                    </button>

                </form>
            </article>
        </section>

    </main>

    <!-- Footer -->
    <footer>
        <p>&copy; 2026 LibraryPal. All rights reserved.</p>
    </footer>

</div>

</body>
</html>