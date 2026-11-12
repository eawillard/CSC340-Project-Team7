
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="stylesheet" href="/styles.css">

    <title>Edit Provider Profile | LibraryPal</title>
</head>

<body class="app-page">

<div class="container">

    <#include "/fragments/provider-nav.ftlh">

    <main>

        <section>
            <h1>Edit Provider Profile</h1>
            <p>Update your personal and library information.</p>
        </section>

        <#if errorMessage??>
            <p class="error-message">${errorMessage}</p>
        </#if>

        <section>
            <div class="card">
                <div class="card-content">

                    <form method="post"
                          action="/provider/profile/edit">

                        <h2>Personal Information</h2>

                        <div class="form-group">
                            <label for="name">Full Name</label>

                            <input
                                type="text"
                                id="name"
                                name="name"
                                value="${coordinator.name?html}"
                                required>
                        </div>

                        <div class="form-group">
                            <label for="email">Email</label>

                            <input
                                type="email"
                                id="email"
                                name="email"
                                value="${coordinator.email?html}"
                                required>
                        </div>

                        <div class="form-group">
                            <label for="password">
                                New Password (optional)
                            </label>

                            <input
                                type="password"
                                id="password"
                                name="password"
                                placeholder="Leave blank to keep current password">
                        </div>

                        <div class="form-group">
                            <label for="accountStatus">
                                Account Status
                            </label>

                            <input
                                type="text"
                                id="accountStatus"
                                name="accountStatus"
                                value="${coordinator.accountStatus?html}"
                                required>
                        </div>

                        <#if library??>
                            <h2>Library Information</h2>

                            <div class="form-group">
                                <label for="libraryName">
                                    Library Name
                                </label>

                                <input
                                    type="text"
                                    id="libraryName"
                                    name="libraryName"
                                    value="${library.name?html}"
                                    required>
                            </div>

                            <div class="form-group">
                                <label for="libraryAddress">
                                    Library Address
                                </label>

                                <input
                                    type="text"
                                    id="libraryAddress"
                                    name="libraryAddress"
                                    value="${library.address?html}"
                                    required>
                            </div>
                        </#if>

                        <button type="submit" class="btn btn-primary">
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
