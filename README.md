# DotCode User API Documentation

This document provides a detailed description of the User API endpoints, including request bodies, response bodies, status codes, and their purposes.

## Base URL
The base URL for all API endpoints is:
```
/users
```

## Endpoints

### 1. Get All Users

**Endpoint:** `GET /users`

**Description:** Retrieves a paginated list of users.

**Query Parameters:**
- `page` (integer): The page number (default is 0).
- `size` (integer): The size of the page (default is 10).

**Response:**
```json
[
  {
    "id": 1,
    "firstName": "John",
    "lastName": "Doe",
    "email": "johndoe@example.com"
  },
  {
    "id": 2,
    "firstName": "Jane",
    "lastName": "Smith",
    "email": "janesmith@example.com"
  }
]
```
**Headers:**
- `X-Total-Pages`: Total number of pages.

**Status Codes:**
- `200 OK`: Request was successful.

---

### 2. Get User by ID

**Endpoint:** `GET /users/{id}`

**Description:** Retrieves a user by their ID.

**Path Parameters:**
- `id` (long): The ID of the user.

**Response:**
```json
{
  "id": 1,
  "firstName": "John",
  "lastName": "Doe",
  "email": "johndoe@example.com"
}
```

**Status Codes:**
- `200 OK`: User found successfully.
- `404 Not Found`: User with the given ID does not exist.

---

### 3. Create a New User

**Endpoint:** `POST /users`

**Description:** Creates a new user.

**Request Body:**
```json
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com"
}
```

**Response:**
```json
{
  "id": 1,
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com"
}
```

**Status Codes:**
- `201 Created`: User was successfully created.
- `400 Bad Request`: Validation error in the request body.
- `409 Conflict`: If user with such email already exist.

---

### 4. Update an Existing User

**Endpoint:** `PUT /users/{id}`

**Description:** Updates an existing user by ID.

**Path Parameters:**
- `id` (long): The ID of the user to update.

**Request Body:**
```json
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "johndoe@example.com"
}
```

**Response:**
```json
{
  "id": 1,
  "firstName": "John",
  "lastName": "Doe",
  "email": "johndoe@example.com"
}
```

**Status Codes:**
- `200 OK`: User was successfully updated.
- `400 Bad Request`: Validation error in the request body.
- `404 Not Found`: User with the given ID does not exist.
- `409 Conflict`: If user with such email already exist.

---

### 5. Delete a User

**Endpoint:** `DELETE /users/{id}`

**Description:** Deletes a user by their ID.

**Path Parameters:**
- `id` (long): The ID of the user to delete.

**Response:**
No response body.

**Status Codes:**
- `204 No Content`: User was successfully deleted.
- `404 Not Found`: User with the given ID does not exist.

---

## User Validation
The `UserDto` object includes the following fields:

| Field       | Type   | Constraints                          |
|-------------|--------|--------------------------------------|
| `id`        | Long   | Auto-generated, not required in POST |
| `firstName` | String | Max 255 characters, cannot be blank  |
| `lastName`  | String | Max 255 characters, cannot be blank  |
| `email`     | String | Valid email format, cannot be blank  |

---

## Error Responses
In case of an error, the API returns a JSON object with the following structure:

```json
{
  "timestamp": "2024-12-30T12:34:56.789",
  "status": 404,
  "error": "Not Found",
  "message": "User with id: 1 does not exist",
  "path": "/users/1"
}
```

