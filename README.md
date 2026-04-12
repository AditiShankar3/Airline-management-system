# ✈️ Airline Management System
**Course:** UE23CS352B — Object Oriented Analysis and Design
**Institution:** PES University | Academic Year 2025-26

---

## 👥 Team
| SRN | Name | Module | Pattern | Principle |
|---|---|---|---|---|
| PES1UG23CS029 | Aditi Shankar | Booking & Payment | Builder | SRP |
| PES1UG23CS053 | Alekhya Agaram | Flight Management | Factory | OCP |
| PES1UG23CS002 | A K Pranav | User Auth & Roles | Singleton (DB) | LSP |
| PES1UG23CS___ | [4th Member] | Reports & Admin | Observer + Facade | DIP |

---

## 🛠 Tech Stack
Java 17 · Spring Boot 3.2 · Spring Security · Spring Data JPA · MySQL · Maven · Lombok · Docker

---

## ⚡ Quickstart (Teammates — read this)

### Step 1 — Clone the repo
```bash
git clone https://github.com/YOUR_REPO_URL.git
cd system
```

### Step 2 — Add DB credentials
Get the file `application-local.properties` from **Aditi** (shared via WhatsApp).
Place it at:
```
src/main/resources/application-local.properties
```
> ⚠️ This file is gitignored. Never commit it.

### Step 3 — Run

#### Option A: Railway Cloud DB (Primary ✅ — needs internet)
```bash
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Dspring.config.additional-location=classpath:application-local.properties"
```

#### Option B: Local Docker DB (Offline fallback)
```bash
docker-compose up -d    # starts MySQL container on port 3306
mvn spring-boot:run     # uses localhost:3306 by default
```
Stop Docker DB: `docker-compose down`

### Step 4 — Verify it works
Open in browser or Postman:
```
GET http://localhost:8081/api/flights
```
Expected response: `[]` ✅ (empty array — project is running)

---

## 📁 Module Ownership — Who Touches What

> ⚠️ Only edit files assigned to you. Always `git pull` before you `git push`.

| Package | Files | Owner |
|---|---|---|
| `model/` | `Booking`, `Ticket`, `Payment`, `Seat`, `BookingRequest` | Aditi CS029 |
| `model/` | `Flight`, `FlightSchedule` | Alekhya CS053 |
| `model/` | `User`, `Passenger`, `Staff`, `Administrator` | Pranav CS002 |
| `model/` | `Report` | 4th Member |
| `service/` | `BookingService`, `PaymentService`, `SeatService` | Aditi CS029 |
| `service/` | `FlightService` | Alekhya CS053 |
| `service/` | `UserService` | Pranav CS002 |
| `service/` | `ReportService` | 4th Member |
| `controller/` | `BookingController`, `PaymentController`, `SeatController` | Aditi CS029 |
| `controller/` | `FlightController` | Alekhya CS053 |
| `controller/` | `AuthController` | Pranav CS002 |
| `controller/` | `ReportController` | 4th Member |
| `patterns/` | `BookingBuilder` | Aditi CS029 |
| `patterns/` | `UserFactory`, `SeatAvailabilityObserver` | Alekhya CS053 |
| `patterns/` | `BookingObserver`, `EmailNotificationObserver` | Pranav CS002 |
| `patterns/` | `ReportFacade` | 4th Member |
| `config/` | `SecurityConfig` | Pranav CS002 |
| `config/` | `DatabaseConfig` | 4th Member |

---

## 🌐 API Endpoints Reference

| Method | Endpoint | Who | Description |
|---|---|---|---|
| POST | `/api/auth/register` | Public | Register new user |
| POST | `/api/auth/login` | Public | Login |
| GET | `/api/flights` | All | Get all flights |
| GET | `/api/flights/search?src=&dest=&date=` | Passenger | Search flights |
| POST | `/api/flights` | Staff | Add flight |
| PUT | `/api/flights/{id}` | Staff | Update flight |
| DELETE | `/api/flights/{id}` | Staff | Delete flight |
| POST | `/api/bookings` | Passenger | Create booking |
| GET | `/api/bookings/passenger/{id}` | Passenger | Booking history |
| PUT | `/api/bookings/{id}/cancel` | Passenger | Cancel booking |
| GET | `/api/seats/{flightId}` | Passenger | Available seats |
| POST | `/api/payments` | Passenger | Process payment |
| PUT | `/api/payments/refund/{bookingId}` | Passenger | Refund |
| GET | `/api/reports/revenue?from=&to=` | Admin | Revenue report |
| GET | `/api/reports/cancellations?from=&to=` | Admin | Cancellation report |

---

## 🔁 Git Workflow for Teammates
```bash
# Before starting work each day
git pull origin main

# After finishing your changes
git add .
git commit -m "feat(module): what you did - YourName SRN"
git push origin main
```
