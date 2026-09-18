Requirements – LibraryPal

Project Name: LibraryPal
Team: Ashlyn Willard - Provider, Toni Graham - Customer
Course: CSC 340
Version: 1.0
Date: 2026-09-18
1. Overview

Vision. LibraryPal is designed to help readers or students to connect and use local libraries by providing more convenient ways to request, pick up/drop off, and donate books, as well as providers help people get use out of their community.

Glossary: Terms used in the project

    Provider: A person who connects customers to local libraries and adds/removes/lends books.
    Customer: A person seeking to connect to a library and search/browse/request/donate books.
    Profile: A collection of information about a user, including personal details and reviews made by them.

    

Primary Users / Roles.

    Customer (e.g., Student/Patient/Pet Owner/etc. ) — 1 line goal statement.
    Provider (Library Coordinator) — Attract readers/students and manage services.
  

Scope (this semester).

    User profiles (customers and providers)
    Search and browse books (both) 
    request and donate books (customer) 
    add and remove books (provider)
    Connecting to local libraries
    Reviews and ratings

Out of scope (deferred).

    <deferred 1>
    <deferred 2>

    This document is requirements‑level and solution‑neutral; design decisions (UI layouts, API endpoints, schemas) are documented separately.

2. Functional Requirements (User Stories)

Write each story as: As a <role>, I want <capability>, so that <benefit>. Each story includes at least one Given/When/Then scenario.
2.1 Customer Stories

    US‑1 —
    Story: As a customer, I want … so that …
    Acceptance:

    Scenario: <happy path>
      Given <preconditions>
      When  <action>
      Then  <observable outcome>

    US‑2 —
    Story: As a customer, I want … so that …
    Acceptance:

    Scenario: <happy path>
      Given <preconditions>
      When  <action>
      Then  <observable outcome>

2.2 Provider Stories
    US-21 - Register and manage profile
    Story: As a provider, I want to create my profile so that I can attract readers and help them use services.
    Acceptance:

    Scenario: Create provider profile
        Given I do not have a profile
        When  I provide my details and submit the form
        Then  my profile should be created
        And   the profile should be visible to customers

    US-22 - Define services
    Story: As a provider, I want to define the services I offer so that customers can understand what services are available to them.
    Acceptance:

    Scenario: Provider adds a service
      Given I am logged in as a provider
      When  I add and save a service offered
      Then  the services should be available to customers to view and use.

    US-23 — Manage Books
    Story: As a provider, I want to add and remove books from the collection so that customers can see whats currently available.
    Acceptance:

    Scenario: Provider adds a book
      Given I am logged in as a provider
      When  I am able to add/remove a book with the required information
      Then  the books appear/disappear in the available book collection.
    

    US-24 — Manage Requests
    Story: As a provider, I want to view and manage customer book requests so that I can respond to requests and help customers obtain available books.
    Acceptance:

    Scenario: Provider reviews a request
      Given I am logged in as a trainer
      When  I view customer book requests
      Then  I can see requested books and customer request information

    US-25 — View Customer Reviews
    Story: As a provider, I want to view reviews and ratings about my services so that I can understand customer experiences and improve my services.
    Acceptance:

    Scenario: Provider views reviews
      Given I am logged in as a trainer
      When  I can view customer profiles or reviews section
      Then  I can see ratings and reviews related to their services.
      
3. Non‑Functional Requirements (make them measurable)

    Performance: description
    Availability/Reliability: description
    Security/Privacy: description
    Usability: description

4. Assumptions, Constraints, and Policies

    Modern browsers (latest Chrome/Firefox/Edge/Safari) and stable connectivity
    Course timeline and campus infrastructure constraints apply.

5. Milestones (course‑aligned)

    M1 Requirements — this file + stories opened as issues.
    M2 High‑fidelity prototype — core customer/provider flows fully interactive.
    M3 Design — architecture, schema, API outline.
    M4 Backend API — key endpoints + tests.
    M5 Increment — ≥2 use cases end‑to‑end.
    M6 Final — complete system & documentation.

6. Change Management

    Stories are living artifacts; changes are tracked via repository issues and linked pull requests.
    Major changes should update this SRS.
