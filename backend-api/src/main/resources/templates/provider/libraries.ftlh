
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Libraries | LibraryPal</title>
    <link rel="stylesheet" href="/styles.css">
</head>

<body class="app-page">

<div class="container">

    <#include "/provider/provider-nav.ftlh">

    <main>

        <section class="section">
            <h1>Library Network</h1>
            <p>
                Browse all registered libraries and join the
                libraries you want to help manage.
            </p>
        </section>

        <#if errorMessage??>
            <p class="error-message">${errorMessage}</p>
        </#if>

        <section class="section">
            <h2>All Registered Libraries</h2>

            <#if libraries?? && libraries?has_content>
                <#list libraries as library>
                    <article class="card">
                        <div class="card-content">
                            <h3>${library.name}</h3>
                            <p>${library.address}</p>

                            <div class="card-actions">
                                <#if assignedLibraryIds?? && assignedLibraryIds?seq_contains(library.id)>
                                    <p class="success-message">
                                        You are assigned to this library.
                                    </p>

                                    <button type="button" class="btn btn-outline" disabled>
                                        Assigned
                                    </button>
                                <#else>
                                    <form method="post" action="/provider/libraries/${library.id}/assign">
                                        <button type="submit" class="btn btn-primary">
                                            Assign Myself
                                        </button>
                                    </form>
                                </#if>

                                <form method="post"
                                      action="/provider/libraries/${library.id}/delete"
                                      onsubmit="return confirm('Are you sure you want to delete this library?');">
                                    <button type="submit" class="btn btn-outline">
                                        Delete Library
                                    </button>
                                </form>
                            </div>
                        </div>
                    </article>
                </#list>
            <#else>
                <p>No libraries have been registered yet.</p>
            </#if>
        </section>

        <section class="section">
            <h2>Register a New Library</h2>

            <div class="card">
                <div class="card-content">
                    <form action="/provider/libraries" method="post">
                        <div class="form-group">
                            <label for="name">Library Name</label>
                            <input type="text" id="name" name="name" required>
                        </div>

                        <div class="form-group">
                            <label for="address">Library Address</label>
                            <input type="text" id="address" name="address" required>
                        </div>

                        <button type="submit" class="btn btn-primary">
                            Register Library
                        </button>
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
