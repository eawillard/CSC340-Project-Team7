
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="stylesheet" href="/styles.css">

    <title>Profile | LibraryPal</title>
</head>

<body class="app-page">

<div class="container">

    <#include "/provider/provider-nav.ftlh">
  

    <main>

        <section>
            <h1>My Profile</h1>
            <p>Manage your LibraryPal account information.</p>
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
                <h3>Personal Details</h3>

                <p>
                    <strong>Name:</strong>
                    ${coordinator.name?html}
                </p>

                <p>
                    <strong>Email:</strong>
                    ${coordinator.email?html}
                </p>

                <p>
                    <strong>Account Status:</strong>
                    ${coordinator.accountStatus?html}
                </p>

                <a href="/provider/profile/edit">
                    Edit Personal Information
                </a>
            </article>
        </section>

        <section>
            <h2>Library Information</h2>

            <article>
                <h3>Library Details</h3>

                <#if library??>
                    <p>
                        <strong>Library:</strong>
                        ${library.name?html}
                    </p>

                    <p>
                        <strong>Address:</strong>
                        ${library.address?html}
                    </p>
                <#else>
                    <p>No library information available.</p>
                </#if>
            </article>
        </section>

        <section>
            <h2>Library Statistics</h2>

            <article>
                <h3>Current Statistics</h3>

                <p>
                    <strong>Total Books:</strong>
                    ${totalBooks!0}
                </p>

                <p>
                    <strong>Available Books:</strong>
                    ${availableBooks!0}
                </p>

                <p>
                    <strong>Checked Out Books:</strong>
                    ${checkedOutBooks!0}
                </p>
            </article>
        </section>

        <section>
            <h2>Account Settings</h2>

            <article>
                <h3>Manage Account</h3>

                <a href="/provider/profile/edit">
                    Edit Profile
                </a>

                <form method="post"
                      action="/provider/profile/delete"
                      onsubmit="return confirm('Are you sure you want to delete your account?');">

                    <button type="submit">
                        Delete Account
                    </button>

                </form>
            </article>
        </section>

    </main>

    <footer>
        <p>&copy; 2026 LibraryPal. All rights reserved.</p>
    </footer>

</div>

</body>
</html>
