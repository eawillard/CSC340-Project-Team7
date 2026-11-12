
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/styles.css">
    <title>LibraryPal | Provider Dashboard</title>
</head>

<body class="app-page">

<div class="container">

    <nav>
        <div class="navbar">

            <a href="/" class="navbar-brand">LibraryPal</a>

            <ul class="nav-links">
                <li><a href="/provider/dashboard">Dashboard</a></li>
                <li><a href="/provider/books">Manage Books</a></li>
                <li><a href="/provider/customers">View Members</a></li>
                <li><a href="/provider/profile">Profile</a></li>
                <li><a href="/login" class="btn btn-outline">Logout</a></li>
            </ul>

        </div>
    </nav>

    <!-- Dashboard Header -->
    <section class="section">

        <div class="container">

            <div class="dashboard-header">

                <div class="dashboard-title">
                    Welcome Back, ${provider.firstName}!
                </div>

                <div class="dashboard-subtitle">
                    Manage your library and help customers connect
                    with available books and services.
                </div>

            </div>

            <!-- Statistics -->
            <div class="dashboard-grid">

                <div class="stat-card">
                    <div class="stat-number">
                        ${totalBooks}
                    </div>
                    <div class="stat-label">
                        Books in Collection
                    </div>
                </div>

                <div class="stat-card">
                    <div class="stat-number">
                        ${availableBooks}
                    </div>
                    <div class="stat-label">
                        Available Books
                    </div>
                </div>

                <div class="stat-card">
                    <div class="stat-number">
                        ${pendingRequests}
                    </div>
                    <div class="stat-label">
                        Pending Requests
                    </div>
                </div>

                <div class="stat-card">
                    <div class="stat-number">
                        ${totalCustomers}
                    </div>
                    <div class="stat-label">
                        Customers
                    </div>
                </div>

            </div>

        </div>

    </section>

    <!-- Upcoming Pickups -->
    <section class="section">

        <div class="container">

            <h2>Upcoming Pickups</h2>

            <#if pickups?? && pickups?has_content>

                <#list pickups as pickup>

                    <div class="session-item">

                        <div class="session-header">

                            <div>
                                <div class="session-title">
                                    ${pickup.customerName} — ${pickup.bookTitle}
                                </div>

                                <span class="session-status status-upcoming">
                                    ${pickup.status}
                                </span>
                            </div>

                        </div>

                        <div class="session-details">

                            <div class="detail-item">
                                <span class="detail-label">Pickup Date:</span>
                                ${pickup.pickupDate}
                            </div>

                            <div class="detail-item">
                                <span class="detail-label">Customer:</span>
                                ${pickup.customerName}
                            </div>

                            <div class="detail-item">
                                <span class="detail-label">Book:</span>
                                ${pickup.bookTitle}
                            </div>

                        </div>

                        <div style="display: flex; gap: 1rem; margin-top: 1rem;">

                            <a href="/provider/customers/${pickup.customerId}"
                               class="btn btn-primary">
                                View Customer
                            </a>

                            <#if pickup.status == "Confirmed">

                                <form action="/provider/pickups/${pickup.id}/ready"
                                      method="post">

                                    <button type="submit" class="btn btn-outline">
                                        Mark Ready
                                    </button>

                                </form>

                            </#if>

                            <#if pickup.status == "Pending">

                                <form action="/provider/requests/${pickup.id}/approve"
                                      method="post">

                                    <button type="submit" class="btn btn-primary">
                                        Approve Request
                                    </button>

                                </form>

                            </#if>

                        </div>

                    </div>

                </#list>

            <#else>

                <p>No upcoming pickups.</p>

            </#if>

        </div>

    </section>

    <!-- Recent Reviews -->
    <section class="section">

        <div class="container">

            <h2>Recent Customer Reviews</h2>

            <#if reviews?? && reviews?has_content>

                <#list reviews as review>

                    <div class="card" style="margin-bottom: 1rem;">

                        <div class="card-content">

                            <p>
                                <strong>${review.customerName}</strong>
                            </p>

                            <p>
                                ${review.rating}/5
                            </p>

                            <p>
                                "${review.comment}"
                            </p>

                        </div>

                    </div>

                </#list>

            <#else>

                <p>No customer reviews yet.</p>

            </#if>

        </div>

    </section>

    <footer>
        <p>&copy; 2026 LibraryPal. All rights reserved.</p>
    </footer>

</div>

</body>
</html>
