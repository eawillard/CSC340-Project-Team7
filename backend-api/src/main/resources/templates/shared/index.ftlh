
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/styles.css">
    <title>LibraryPal</title>
</head>

<body>

<main>

    <!-- Navigation -->
    <nav>
        <div class="container">
            <ul>
                <li><a href="/">Home</a></li>
                <li><a href="#how-it-works">How It Works</a></li>
                <li><a href="/login">Login</a></li>
                <li><a href="/register">Register</a></li>
            </ul>
        </div>
    </nav>

    <!-- Hero Section -->
    <section id="hero">
        <div class="container">

            <h1>Welcome to LibraryPal</h1>

            <p>
                Connect with your local library and discover, request,
                and manage your favorite books and resources more
                efficiently than ever before.
            </p>

            <a href="/login">Sign In as a Customer</a>
            <a href="/login">Sign In as a Provider</a>

        </div>
    </section>

    <!-- Local Libraries -->
    <section id="local-libraries">
        <div class="container">

            <h2>Local Libraries</h2>

            <p>
                Discover a library, browse its available books,
                and connect with it to request a pickup.
            </p>

            <div class="library-cards">

                <#if libraries?? && libraries?has_content>

                    <#list libraries as library>

                        <article class="library-card">

                            <#if library.image??>
                                <img src="/${library.image}"
                                     alt="${library.name}">
                            </#if>

                            <h3>${library.name}</h3>

                            <p>
                                ${library.address}
                            </p>

                            <p>
                                <strong>Services:</strong>
                                ${library.services}
                            </p>

                            <p>
                                <strong>Available books:</strong>
                                ${library.availableBooks}
                            </p>

                            <p>
                                <strong>Featured genres:</strong>
                                ${library.featuredGenres}
                            </p>

                            <a href="/libraries/${library.id}">
                                Browse books and connect
                            </a>

                        </article>

                    </#list>

                <#else>

                    <p>No libraries are currently available.</p>

                </#if>

            </div>
        </div>
    </section>

    <!-- How It Works -->
    <section id="how-it-works">
        <div class="container">

            <h2>How It Works</h2>

            <article>
                <h3>1. Create Your Profile</h3>
                <p>Create a LibraryPal profile and connect with your local library.</p>
            </article>

            <article>
                <h3>2. Browse Books</h3>
                <p>Search and browse books available through local libraries.</p>
            </article>

            <article>
                <h3>3. Request a Book</h3>
                <p>Select an available book and request it from your local library.</p>
            </article>

            <article>
                <h3>4. Schedule a Pickup</h3>
                <p>Choose an available pickup date that works for you.</p>
            </article>

            <article>
                <h3>5. Read and Review</h3>
                <p>Pick up your book, enjoy your reading, and share your experience with other readers.</p>
            </article>

        </div>
    </section>

</main>

<footer>
    <div class="container">
        <p>
            &copy; 2026 LibraryPal. All rights reserved.
            Making strong connections between readers and community libraries.
        </p>
    </div>
</footer>

</body>
</html>
