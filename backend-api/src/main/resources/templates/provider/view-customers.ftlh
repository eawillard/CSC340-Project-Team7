
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/styles.css">
    <title>View Customers | LibraryPal</title>
</head>

<body class="app-page">

<div class="container">

    <nav>
        <div class="navbar">
            <a href="/" class="navbar-brand">LibraryPal</a>

            <ul class="nav-links">
                <li><a href="/provider/dashboard">Dashboard</a></li>
                <li><a href="/provider/manage-books">Manage Books</a></li>
                <li><a href="/provider/customers">View Members</a></li>
                <li><a href="/provider/profile">Profile</a></li>
                <li><a href="/login" class="btn btn-outline">Logout</a></li>
            </ul>
        </div>
    </nav>

    <main>

        <section>
            <h1>Library Members</h1>
            <p>View member information and manage your library members.</p>
        </section>

        <!-- Search -->
        <section>
            <h2>Search Members</h2>

            <form action="/provider/customers" method="get">

                <label for="search">Search Members</label>

                <input type="text"
                       id="search"
                       name="search"
                       value="${search!''}"
                       placeholder="Search members...">

                <label for="status">Member Status</label>

                <select id="status" name="status">

                    <option value="All"
                        <#if !status?? || status == "All">selected</#if>>
                        All Members
                    </option>

                    <option value="Active"
                        <#if status?? && status == "Active">selected</#if>>
                        Active
                    </option>

                    <option value="Inactive"
                        <#if status?? && status == "Inactive">selected</#if>>
                        Inactive
                    </option>

                    <option value="Past Members"
                        <#if status?? && status == "Past Members">selected</#if>>
                        Past Members
                    </option>

                </select>

                <button type="submit">Search</button>

            </form>
        </section>

        <!-- Members -->
        <section>

            <h2>Members</h2>

            <#if customers?? && customers?has_content>

                <#list customers as customer>

                    <article>

                        <h3>${customer.firstName} ${customer.lastName}</h3>

                        <p>
                            <strong>Status:</strong>
                            ${customer.status}
                        </p>

                        <p>
                            <strong>Books Checked Out:</strong>
                            ${customer.booksCheckedOut}
                        </p>

                        <p>
                            <strong>Books Returned:</strong>
                            ${customer.booksReturned}
                        </p>

                        <p>
                            <strong>Member Since:</strong>
                            ${customer.memberSince}
                        </p>

                        <p>
                            <strong>Email:</strong>
                            ${customer.email}
                        </p>

                        <p>
                            <strong>Phone:</strong>
                            ${customer.phone!''}
                        </p>

                        <a href="/provider/customers/${customer.id}">
                            View Profile
                        </a>

                        <a href="/provider/customers/${customer.id}/message">
                            Message
                        </a>

                    </article>

                    <hr>

                </#list>

            <#else>

                <p>No members were found.</p>

            </#if>

        </section>

    </main>

    <footer>
        <p>&copy; 2026 LibraryPal. All rights reserved.</p>
    </footer>

</div>

</body>
</html>
