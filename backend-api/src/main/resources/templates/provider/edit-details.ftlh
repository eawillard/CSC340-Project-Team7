
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Provider Details | LibraryPal</title>
    <link rel="stylesheet" href="/styles.css">
</head>

<body class="app-page">

<div class="container">

    <#include "/provider/provider-nav.ftlh">

    <main>

        <section class="section">

            <h1>Edit Account Details</h1>

            <p>Update your provider account information.</p>

            <#if error??>
                <p class="error-message">${error}</p>
            </#if>

            <div class="card">
                <div class="card-content">

                    <form action="/provider/edit-details" method="post">

                        <div class="form-group">

                            <label for="name">Full Name</label>

                            <input
                                type="text"
                                id="name"
                                name="name"
                                value="${coordinator.name}"
                                required>

                        </div>

                        <div class="form-group">

                            <label for="email">Email Address</label>

                            <input
                                type="email"
                                id="email"
                                name="email"
                                value="${coordinator.email}"
                                required>

                        </div>

                        <div class="form-group">

                            <label for="password">New Password</label>

                            <input
                                type="password"
                                id="password"
                                name="password"
                                placeholder="Leave blank to keep current password">

                        </div>

                        <button type="submit"
                                class="btn btn-primary">
                            Save Changes
                        </button>

                        <a href="/provider/profile"
                           class="btn btn-outline">
                            Cancel
                        </a>

                    </form>

                </div>
            </div>

        </section>

    </main>

    <footer>
        <p>&copy; 2026 LibraryPal. All rights reserved.</p>
    </footer>

</div>

</body>
</html>
