# ✈️ Airline Management System
### UE23CS352B — Object Oriented Analysis & Design | PES University | 2025–26

---

## What is this project?

This is a full-stack **Airline Management System** built as part of our OOAD mini-project. The idea came from wanting to model a real-world system that naturally maps to the concepts we studied — multiple user roles, complex object relationships, and interactions that lend themselves well to design patterns and SOLID principles.

The system handles three types of users: **Passengers** who search and book flights, **Staff** who manage flight operations, and **Admins** who oversee users and generate reports. Each role has its own set of use cases, and the entire backend is built with Spring Boot following the MVC architecture.

The reason we picked this domain is honestly because it's complex enough to justify proper OOP design — you've got inheritance (different user types), behavioral patterns (booking triggers multiple reactions), structural patterns (reports need data from multiple services), and creational patterns (object construction that varies by type). It wasn't just a project to submit — it ended up being a decent exercise in actually applying what we learned in class.

---

## Team

| SRN | Name | Major Use Case | Design Pattern | SOLID Principle |
|---|---|---|---|---|
| PES1UG23CS029 | Aditi Shankar | Booking & Payment | Builder | SRP |
| PES1UG23CS053 | Alekhya Agaram | Flight Management + Reports & Admin | Factory + Observer + Facade | OCP + DIP |
| PES1UG23CS002 | A K Pranav | User Auth & Roles | Singleton (DB) | LSP |

---

## OOAD Concepts Applied

This section maps the actual guidelines to what we implemented. We tried to make sure each concept was meaningfully used rather than just bolted on.

### MVC Architecture

The entire backend follows Spring Boot's MVC structure strictly:

- **Controller layer** — handles HTTP requests, does zero business logic, just delegates to services
- **Service layer** — all business rules live here (e.g. a booking can't be made if seats aren't available, a flight can't be deleted if it has active bookings)
- **Repository layer** — Spring Data JPA, no raw SQL, auto-generated queries
- **Model layer** — JPA entities mapped to MySQL tables

No controller ever talks to a repository directly. No repository has business logic. This separation made it easy to test and debug individual layers.

### Design Patterns

We implemented 4 design patterns spanning all three GoF categories (which is the requirement for a 4-concept team — we covered Creational, Structural, and Behavioral):

**Builder Pattern — Creational (Aditi)**
`patterns/BookingBuilder.java`

A `Booking` object has a lot of fields — flight ID, passenger ID, seat type, passenger count, PNR, booking date, status. Using a single constructor would mean passing 8+ parameters in order, which is unreadable and error-prone. Builder lets the booking be assembled step by step as the passenger goes through the booking flow, and `build()` creates the final valid object with sensible defaults (status = PENDING, date = now, auto-generated PNR).

**Factory Pattern — Creational (Alekhya)**
`patterns/FlightFactory.java` + `patterns/UserFactory.java`

Two factory implementations. `UserFactory` centralises the creation of `Passenger`, `Staff`, or `Administrator` objects based on role — without it, every registration endpoint would have if-else chains. `FlightFactory` uses a strategy map to handle DOMESTIC vs INTERNATIONAL flight rules (international gets a 20% surcharge applied automatically), meaning new flight types can be added with zero changes to existing code.

**Facade Pattern — Structural (Alekhya)**
`patterns/ReportFacade.java`

Generating an admin report means querying `BookingService`, `FlightService`, and `PaymentService`, then aggregating the results. Without Facade, the controller would be tightly coupled to all three services. `ReportFacade` hides all of that behind two clean methods: `generateRevenueReport()` and `generateCancellationReport()`. The controller calls one method and gets back a complete report.

**Observer Pattern — Behavioral (Pranav + Alekhya)**
`patterns/BookingObserver.java` and implementations

When a booking is confirmed, two things need to happen independently: send an email notification, and decrement the available seat count on the flight. Without Observer, `BookingService` would have to call both services directly — tight coupling. Instead, `BookingService` notifies all registered observers, and `EmailNotificationObserver` and `SeatAvailabilityObserver` react independently. Adding a new reaction (e.g. generating a ticket) just means adding a new observer — `BookingService` never changes.

### SOLID Principles

**SRP — Single Responsibility (Aditi)**
`BookingService` only handles booking lifecycle. `PaymentService` only handles payment processing. They're kept strictly separate even though a booking triggers a payment — because the reason to change each class is different.

**OCP — Open/Closed (Alekhya)**
The `FlightType` interface + `DomesticFlight` / `InternationalFlight` implementations mean flight types are open for extension but closed for modification. Adding a `CharterFlight` type means writing one new class and adding one line to the registry map in `FlightFactory` — no existing code changes.

**LSP — Liskov Substitution (Pranav)**
`Passenger`, `Staff`, and `Administrator` all extend the abstract `User` class. Any method that accepts a `User` works correctly with any subtype. Spring Security's `UserDetailsService` operates on `User` objects without knowing the concrete type. No subclass throws `UnsupportedOperationException` for inherited methods.

**DIP — Dependency Inversion (Alekhya)**
`ReportFacade` depends on `BookingService`, `FlightService`, and `PaymentService` as abstractions. Spring injects the concrete implementations at runtime via constructor injection. Controllers declare service dependencies as interfaces, not implementations — making unit testing with mocks straightforward.

---

## Tech Stack

- **Backend:** Java 17, Spring Boot 3.2, Spring Security, Spring Data JPA
- **Database:** MySQL (Railway cloud or local Docker)
- **Build:** Maven, Lombok
- **Frontend:** Vanilla HTML/CSS/JS served as a static file from Spring Boot

---

## Setup & Running the Project

You have two options for the database — Railway (shared cloud, recommended) or local Docker.

---

### Option A — Railway Cloud DB (Recommended)

This is the shared database. Everyone on the team connects to the same instance — no local MySQL needed.

**Step 1 — Clone the repo**
```bash
git clone https://github.com/AditiShankar3/Airline-management-system.git
cd Airline-management-system
```

**Step 2 — Get credentials**
Get `application-local.properties` from Aditi (sent via WhatsApp). Place it here:
```
src/main/resources/application-local.properties
```
It looks like this — fill in the actual Railway values:
```properties
DB_URL=jdbc:mysql://YOUR_RAILWAY_HOST:PORT/railway
DB_USERNAME=root
DB_PASSWORD=YOUR_RAILWAY_PASSWORD
```
> ⚠️ This file is in `.gitignore` — never commit it.

**Step 3 — Run**
```bash
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Dspring.config.additional-location=classpath:application-local.properties"
```

**Step 4 — Open the app**
```
http://localhost:8081/index.html
```

---

### Option B — Local MySQL via Docker (Offline fallback)

Use this if you're offline or don't have the Railway credentials yet.

**Step 1 — Start MySQL container**
```bash
docker-compose up -d
```
This starts a MySQL instance on port 3306 with:
- Database: `airline_db`
- Username: `root`
- Password: `airline123`

**Step 2 — Run the app**
```bash
mvn spring-boot:run
```
Spring Boot will use `localhost:3306` by default (no local properties file needed).

**Step 3 — Open the app**
```
http://localhost:8081/index.html
```

**Stop Docker when done:**
```bash
docker-compose down
```

---

### Option C — Your own MySQL

If you have MySQL installed locally, just update `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/airline_db
spring.datasource.username=root
spring.datasource.password=yourpassword
```
Then create the database:
```sql
CREATE DATABASE airline_db;
```
Spring Boot will create all tables automatically on first run (`ddl-auto=update`).

---

## Setting up Railway from scratch (for reference)

If you need to create a new Railway MySQL instance:

1. Go to [railway.app](https://railway.app) → sign in with GitHub
2. New Project → Deploy a Template → select MySQL → Deploy
3. Click the MySQL service → Variables tab
4. Copy `MYSQL_HOST`, `MYSQLPORT`, `MYSQL_DATABASE`, `MYSQL_USER`, `MYSQL_PASSWORD`
5. Build your `application-local.properties` from those values:
   ```
   DB_URL=jdbc:mysql://MYSQL_HOST:MYSQLPORT/MYSQL_DATABASE
   DB_USERNAME=MYSQL_USER
   DB_PASSWORD=MYSQL_PASSWORD
   ```
6. Run the app — tables are auto-created on first startup

**Note:** Railway free tier uses a non-standard port (not 3306). Make sure you use the port from the Variables tab, not 3306.

---

## API Endpoints Reference

### Auth
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/auth/register` | Register new user (role in body: PASSENGER / STAFF / ADMIN) |
| POST | `/api/auth/login` | Login — returns userId, username, role |
| GET | `/api/auth/users` | List all users (admin) |
| PUT | `/api/auth/users/{id}/status?active=true` | Activate or deactivate a user |

### Flights
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/flights` | Get all flights |
| GET | `/api/flights/search?src=&dest=&date=` | Search by route and date |
| POST | `/api/flights` | Add new flight (requires `type`: DOMESTIC or INTERNATIONAL) |
| PUT | `/api/flights/{id}` | Update flight details |
| DELETE | `/api/flights/{id}` | Delete a flight |

### Bookings
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/bookings` | Create a booking |
| GET | `/api/bookings/passenger/{id}` | Booking history for a passenger |
| GET | `/api/bookings/passenger/{id}/details` | Enriched booking info with flight details |
| PUT | `/api/bookings/{id}/cancel` | Cancel a booking |

### Payments
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/payments` | Process payment for a booking |
| PUT | `/api/payments/refund/{bookingId}` | Refund a payment |

### Seats
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/seats/{flightId}` | Available seats for a flight |
| GET | `/api/seats/{flightId}/all` | All seats (available + taken) |
| GET | `/api/seats/{flightId}/type?seatType=ECONOMY` | Filter by seat type |

### Reports (Admin only)
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/reports/revenue?from=YYYY-MM-DD&to=YYYY-MM-DD` | Revenue report |
| GET | `/api/reports/cancellations?from=YYYY-MM-DD&to=YYYY-MM-DD` | Cancellation report |

---

## Sample Postman requests

**Register a passenger:**
```json
POST /api/auth/register
{
  "username": "alice",
  "email": "alice@demo.com",
  "password": "pass123",
  "role": "PASSENGER"
}
```

**Add a domestic flight (Staff):**
```json
POST /api/flights
{
  "flightNumber": "AI101",
  "airlineName": "Air India",
  "source": "Bangalore",
  "destination": "Mumbai",
  "departureTime": "2026-05-01T10:00:00",
  "arrivalTime": "2026-05-01T12:00:00",
  "totalSeats": 180,
  "basePrice": 3500.00,
  "type": "DOMESTIC"
}
```

**Book a flight:**
```json
POST /api/bookings
{
  "flightId": "<flight-id>",
  "passengerId": "<user-id>",
  "seatType": "ECONOMY",
  "passengerCount": 1
}
```

**Process payment:**
```json
POST /api/payments
{
  "bookingId": "<booking-id>",
  "amount": 3500.00,
  "paymentMethod": "UPI"
}
```

---

## Git workflow

```bash
# Always pull before starting
git pull origin main

# After your changes
git add .
git commit -m "feat(module): description - Name SRN"
git push origin main
```

---

## GitHub

[github.com/AditiShankar3/Airline-management-system](https://github.com/AditiShankar3/Airline-management-system)
