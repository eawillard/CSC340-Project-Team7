# LibraryPal Backend API Documentation

## UML Class Diagram

![LibraryPal UML Class Diagram](../doc/uml-class-diagram.png)



## API Endpoints

The following table documents the endpoints implemented for the Customer, BookService, and Review features.

| HTTP Method | Endpoint | Description / Use Case | Success Status | Implemented By |
|---|---|---|---|---|
| POST | `/api/customers/register` | Register a new customer account | `201 Created` | Toni — Customer |
| GET | `/api/customers/{id}` | Retrieve a customer's profile | `200 OK` | Toni — Customer |
| PUT | `/api/customers/{id}` | Update a customer's profile | `200 OK` | Toni — Customer |
| GET | `/api/book-services` | List available book services | `200 OK` | Toni — BookService |
| GET | `/api/book-services/{id}` | Retrieve a book service by ID | `200 OK` | Toni — BookService |
| POST | `/api/book-services` | Create a book service | `201 Created` | Toni — BookService |
| PUT | `/api/book-services/{id}` | Update a book service | `200 OK` | Toni — BookService |
| DELETE | `/api/book-services/{id}?customerId={customerId}` | Delete a book service associated with the specified customer | Confirm in controller or API client | Toni — BookService |
| POST | `/api/reviews` | Submit a review for a customer | `201 Created` | Toni — Review |
| PUT | `/api/reviews/{reviewId}` | Update a review | `200 OK` | Toni — Review |


## Example Request Bodies

The following examples demonstrate the JSON request bodies for each POST and PUT endpoint.

### 1. Register a Customer

**Endpoint:** `POST /api/customers/register`

```json
{
  "name": "Example Customer",
  "username": "examplecustomer",
  "password": "ExamplePassword123!"
}
```

**Expected success status:** `201 Created`

**Use case:** Allows a new customer to register an account.

### 2. Update a Customer

**Endpoint:** `PUT /api/customers/{id}`

Example endpoint: `PUT /api/customers/1`

```json
{
  "name": "Updated Example Customer",
  "username": "examplecustomer"
}
```

**Expected success status:** `200 OK`

**Use case:** Updates an existing customer's profile.

### 3. Create a Book Service

**Endpoint:** `POST /api/book-services`

```json
{
  "serviceName": "Book Repair",
  "description": "Repair and restore damaged books",
  "customerId": 1
}
```

**Expected success status:** `201 Created`

**Use case:** Creates a new book service.

### 4. Update a Book Service

**Endpoint:** `PUT /api/book-services/{id}`

Example endpoint: `PUT /api/book-services/1`

```json
{
  "serviceName": "Updated Book Repair",
  "description": "Repair, restore, and preserve damaged books",
  "customerId": 1
}
```

**Expected success status:** `200 OK`

**Use case:** Updates an existing book service.

### 5. Submit a Review

**Endpoint:** `POST /api/reviews`

```json
{
  "customerId": 1,
  "rating": 5,
  "comment": "Testing the LibraryPal review API"
}
```

**Expected success status:** `201 Created`

**Use case:** Allows a customer to submit a review.

### 6. Update a Review

**Endpoint:** `PUT /api/reviews/{reviewId}`

Example endpoint: `PUT /api/reviews/2`

```json
{
  "customerId": 1,
  "rating": 4,
  "comment": "Updated review using EchoAPI"
}
```

**Expected success status:** `200 OK`

**Use case:** Updates an existing review.

## Deployment

**Live Backend API:** [LibraryPal Backend](PASTE_ToniR_ACTUAL_RENDER_URL_HERE)


For example, if Tonir actual Render URL is `https://Tonir-service.onrender.com`, the Markdown would be:

```markdown
**Live Backend API:** [LibraryPal Backend](https://Tonir-service.onrender.com)
```

The example URL above is only a placeholder; do not submit it as Tonir actual deployment URL.

### Cold-Start Note

If the backend is hosted on Render's free web service plan, it may spin down after 15 minutes without incoming traffic. The first request after inactivity can take approximately one minute while the service starts again. Subsequent requests should be faster once the service is running.