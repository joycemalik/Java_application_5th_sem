# 🚗🏍️ Car and Bike Rental Management System

> **A complete Java client-server application with cloud deployment support**

[![Java](https://img.shields.io/badge/Java-8%2B-orange.svg)](https://www.oracle.com/java/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Ready-blue.svg)](https://www.postgresql.org/)
[![Supabase](https://img.shields.io/badge/Supabase-Integrated-green.svg)](https://supabase.com/)
[![Railway](https://img.shields.io/badge/Railway-Deployable-purple.svg)](https://railway.app/)

---

## 🎯 **NEW! Cloud Deployment Ready**

This project now supports **two deployment modes**:

1. **☁️ Cloud Deployment** (Recommended) - Deploy to Railway with Supabase database
2. **💻 Local Deployment** - Traditional MySQL on localhost

---

## 🚀 Quick Start

### Want to Deploy to Cloud? (Recommended)

**👉 [START HERE](START_HERE.md) ← Click to begin!**

Or jump directly to:
- **[README_CLOUD.md](README_CLOUD.md)** - Cloud version overview
- **[CLOUD_DEPLOYMENT.md](CLOUD_DEPLOYMENT.md)** - Step-by-step cloud deployment
- **[DEPLOYMENT_CHECKLIST.md](DEPLOYMENT_CHECKLIST.md)** - Verify your deployment

### Want Local Setup?

Continue reading this document for traditional MySQL setup.

---

## 📚 Complete Documentation

| Document | Purpose | Audience |
|----------|---------|----------|
| **[START_HERE.md](START_HERE.md)** ⭐ | Choose your deployment path | Everyone |
| **[README_CLOUD.md](README_CLOUD.md)** | Cloud deployment README | Cloud users |
| **[CLOUD_DEPLOYMENT.md](CLOUD_DEPLOYMENT.md)** | Supabase + Railway guide | Cloud deployment |
| **[INDEX.md](INDEX.md)** | Complete documentation index | Reference |
| **[ARCHITECTURE.md](ARCHITECTURE.md)** | System architecture | Understanding |
| **README.md** (this file) | Local MySQL setup | Local development |

**Can't find something?** → See [INDEX.md](INDEX.md)

---

## 📋 Table of Contents (Local Setup)
- [Architecture](#architecture)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Prerequisites](#prerequisites)
- [Database Setup](#database-setup)
- [Project Structure](#project-structure)
- [How to Compile](#how-to-compile)
- [How to Run](#how-to-run)
- [Usage Guide](#usage-guide)
- [Protocol Documentation](#protocol-documentation)
- [Future Enhancements](#future-enhancements)

---

## 🏗️ Architecture

This application follows a **layered architecture**:

```
┌─────────────────────────────────────────────┐
│           Client (Console UI)               │
│         (RentalClient.java)                 │
└──────────────────┬──────────────────────────┘
                   │ TCP Socket
                   │ (Text Protocol)
┌──────────────────▼──────────────────────────┐
│     Multi-threaded Server                   │
│  (RentalServer + ClientHandler)             │
└──────────────────┬──────────────────────────┘
                   │
┌──────────────────▼──────────────────────────┐
│         DAO Layer (JDBC)                    │
│  (UserDAO, VehicleDAO)                      │
└──────────────────┬──────────────────────────┘
                   │
┌──────────────────▼──────────────────────────┐
│         MySQL Database                      │
│  (users, vehicles, bookings)                │
└─────────────────────────────────────────────┘
```

---

## ✨ Features

### Current Features:
- ✅ User Registration (Customer role)
- ✅ User Login (with authentication)
- ✅ List available Cars
- ✅ List available Bikes
- ✅ Multi-client support (multiple users can connect simultaneously)
- ✅ TCP-based client-server communication
- ✅ JDBC database integration with MySQL
- ✅ User session management

### Planned Features:
- 🔜 Book vehicles (with date range)
- 🔜 View booking history
- 🔜 Cancel bookings
- 🔜 Admin panel (add/remove vehicles)
- 🔜 Payment processing
- 🔜 GUI client (Swing/JavaFX)

---

## 🛠️ Technologies Used

- **Java SE 8+** - Core programming language
- **JDBC** - Database connectivity
- **MySQL 8.0** - Database server
- **TCP Sockets** - Client-server communication
- **Multi-threading** - Concurrent client handling

---

## 📦 Prerequisites

Before running this application, make sure you have:

1. **Java Development Kit (JDK) 8 or higher**
   - Download from: https://www.oracle.com/java/technologies/downloads/
   - Verify: `java -version` and `javac -version`

2. **MySQL Server 8.0 or higher**
   - Download from: https://dev.mysql.com/downloads/mysql/
   - Verify: `mysql --version`

3. **MySQL Connector/J (JDBC Driver)**
   - Download from: https://dev.mysql.com/downloads/connector/j/
   - Or use the JAR file: `mysql-connector-j-8.x.x.jar`

---

## 🗄️ Database Setup

### Step 1: Start MySQL Server

Make sure MySQL server is running on your machine.

### Step 2: Run the SQL Script

1. Open MySQL Workbench or command-line client:
   ```bash
   mysql -u root -p
   ```

2. Execute the database setup script:
   ```bash
   mysql -u root -p < database_setup.sql
   ```

   Or copy-paste the contents of `database_setup.sql` into MySQL Workbench.

### Step 3: Verify Database Creation

```sql
SHOW DATABASES;
USE rental_db;
SHOW TABLES;
SELECT * FROM users;
SELECT * FROM vehicles;
```

You should see:
- Database: `rental_db`
- Tables: `users`, `vehicles`, `bookings`
- Sample data in `users` and `vehicles` tables

### Step 4: Update Database Credentials

Edit `src/com/rental/util/DBUtil.java` and update these lines with your MySQL credentials:

```java
private static final String URL = "jdbc:mysql://localhost:3306/rental_db";
private static final String USER = "root";
private static final String PASSWORD = "your_mysql_password"; // CHANGE THIS
```

---

## 📁 Project Structure

```
d:\ait\java\group assignment\
│
├── database_setup.sql          # MySQL database creation script
│
├── lib/
│   └── mysql-connector-j-8.x.x.jar  # MySQL JDBC driver (download separately)
│
└── src/
    └── com/
        └── rental/
            ├── model/          # POJO classes
            │   ├── User.java
            │   ├── Vehicle.java
            │   └── Booking.java
            │
            ├── dao/            # Data Access Objects
            │   ├── UserDAO.java
            │   ├── UserDAOImpl.java
            │   ├── VehicleDAO.java
            │   └── VehicleDAOImpl.java
            │
            ├── util/           # Utility classes
            │   └── DBUtil.java
            │
            ├── server/         # Server components
            │   ├── RentalServer.java
            │   └── ClientHandler.java
            │
            └── client/         # Client application
                └── RentalClient.java
```

---

## 🔨 How to Compile

### Option 1: Using Command Line (Windows PowerShell)

1. **Download MySQL Connector/J** and place it in a `lib` folder in your project directory.

2. **Compile all Java files:**

```powershell
# Navigate to project directory
cd "d:\ait\java\group assignment"

# Create bin directory for compiled classes
New-Item -ItemType Directory -Force -Path bin

# Compile with MySQL driver in classpath
javac -d bin -cp "lib\mysql-connector-j-8.2.0.jar" src\com\rental\model\*.java src\com\rental\util\*.java src\com\rental\dao\*.java src\com\rental\server\*.java src\com\rental\client\*.java
```

### Option 2: Using an IDE (IntelliJ IDEA / Eclipse / VS Code)

1. **IntelliJ IDEA:**
   - Open project folder
   - Right-click on `lib/mysql-connector-j-8.x.x.jar` → Add as Library
   - Build → Build Project

2. **Eclipse:**
   - Import as Java Project
   - Right-click project → Build Path → Add External JARs → Select MySQL connector JAR
   - Project → Build All

3. **VS Code:**
   - Open folder in VS Code
   - Install "Extension Pack for Java"
   - Add MySQL JAR to referenced libraries in `.classpath`
   - Java: Compile Workspace

---

## ▶️ How to Run

### Step 1: Start the Server

Open a terminal/command prompt:

```powershell
cd "d:\ait\java\group assignment"

# Run the server
java -cp "bin;lib\mysql-connector-j-8.2.0.jar" com.rental.server.RentalServer
```

You should see:
```
===========================================
  Car & Bike Rental Management System
  Server starting on port 5000
===========================================
[SERVER] Waiting for client connections...
```

### Step 2: Start the Client

Open **another** terminal/command prompt:

```powershell
cd "d:\ait\java\group assignment"

# Run the client
java -cp "bin;lib\mysql-connector-j-8.2.0.jar" com.rental.client.RentalClient
```

You should see the client menu:
```
===========================================
  Car & Bike Rental Management System
  Client Application
===========================================

Connected to server at localhost:5000
Server: WELCOME

───────────────────────────────────────────
            MAIN MENU
───────────────────────────────────────────
  1. Register
  2. Login
  0. Exit
───────────────────────────────────────────
Enter your choice:
```

### Step 3: Multiple Clients

You can run multiple client instances simultaneously to test multi-client support!

---

## 📖 Usage Guide

### 1. Register a New Account

```
Choice: 1
Enter your name: John Smith
Enter your email: john@example.com
Enter your password: pass123

[SUCCESS] Registration successful! Your User ID is: 4
```

### 2. Login

```
Choice: 2
Enter your email: joyce@demo.com
Enter your password: password

[SUCCESS] Login successful!
Welcome, Joyce (CUSTOMER)!
```

### 3. List Available Cars

```
Choice: 3

──── AVAILABLE CARS ────
Found 3 available car(s):

┌──────┬─────────────────────────┬─────────────┬──────────────┐
│  ID  │      Brand & Model      │  Reg Number │ Price/Day(₹) │
├──────┼─────────────────────────┼─────────────┼──────────────┤
│ 1    │ Toyota Innova           │ KA01AB1234  │      2000.00 │
│ 2    │ Honda City              │ KA02CD5678  │      1500.00 │
│ 3    │ Hyundai Creta           │ KA03EF9012  │      1800.00 │
└──────┴─────────────────────────┴─────────────┴──────────────┘
```

### 4. List Available Bikes

```
Choice: 4

──── AVAILABLE BIKES ────
Found 3 available bike(s):

┌──────┬─────────────────────────┬─────────────┬──────────────┐
│  ID  │      Brand & Model      │  Reg Number │ Price/Day(₹) │
├──────┼─────────────────────────┼─────────────┼──────────────┤
│ 4    │ Yamaha FZ               │ KA02XY5678  │       700.00 │
│ 5    │ Honda CBR               │ KA04GH3456  │       900.00 │
│ 6    │ Royal Enfield Classic.. │ KA05IJ7890  │       800.00 │
└──────┴─────────────────────────┴─────────────┴──────────────┘
```

### 5. Logout

```
Choice: 5
[SUCCESS] Logged out successfully.
```

---

## 📡 Protocol Documentation

The client-server communication uses a simple **text-based protocol** with pipe (`|`) delimiters.

### Commands (Client → Server):

1. **REGISTER**
   ```
   Format: REGISTER|name|email|password
   Example: REGISTER|John Doe|john@example.com|pass123
   ```

2. **LOGIN**
   ```
   Format: LOGIN|email|password
   Example: LOGIN|joyce@demo.com|password
   ```

3. **LIST_VEHICLES**
   ```
   Format: LIST_VEHICLES|type
   Example: LIST_VEHICLES|CAR
   Example: LIST_VEHICLES|BIKE
   ```

4. **LOGOUT**
   ```
   Format: LOGOUT
   ```

### Responses (Server → Client):

1. **Success Response**
   ```
   OK|COMMAND|data1|data2|...
   ```

2. **Error Response**
   ```
   ERROR|error message
   ```

### Examples:

```
Client: LOGIN|joyce@demo.com|password
Server: OK|LOGIN|Joyce|CUSTOMER

Client: LIST_VEHICLES|CAR
Server: OK|LIST_VEHICLES|2|1,Toyota,Innova,KA01AB1234,2000.00|2,Honda,City,KA02CD5678,1500.00

Client: LOGIN|wrong@email.com|wrongpass
Server: ERROR|Login failed. Invalid email or password.
```

---

## 🚀 Future Enhancements

### Phase 2: Booking System
- Add `BOOK|vehicleId|startDate|endDate` command
- Implement `BookingDAO` and `BookingDAOImpl`
- Add date validation and conflict checking
- Calculate total price based on rental duration

### Phase 3: Admin Features
- Admin login and authentication
- Add/Edit/Delete vehicles
- View all bookings
- Generate reports

### Phase 4: GUI Client
- Replace console client with JavaFX or Swing
- Visual calendar for date selection
- Dashboard with statistics
- Image gallery for vehicles

### Phase 5: Advanced Features
- Payment gateway integration
- Email notifications
- Password encryption (BCrypt)
- Session tokens instead of password storage
- Database connection pooling
- RESTful API option

---

## 🐛 Troubleshooting

### Problem: "MySQL JDBC Driver not found"
**Solution:** Make sure `mysql-connector-j-8.x.x.jar` is in the classpath when compiling and running.

### Problem: "Could not connect to server"
**Solution:** 
1. Make sure the server is running first
2. Check firewall settings
3. Verify port 5000 is not being used by another application

### Problem: "Database connection failed"
**Solution:**
1. Check MySQL server is running
2. Verify database credentials in `DBUtil.java`
3. Ensure `rental_db` database exists
4. Test connection: `mysql -u root -p rental_db`

### Problem: "Package does not match expected package"
**Solution:** This is a VS Code warning. The code will compile and run correctly from the command line. Alternatively, configure VS Code Java project settings or use Maven/Gradle.

---

## 👥 Demo Accounts

Pre-created accounts in the database:

| Email | Password | Role |
|-------|----------|------|
| admin@rental.com | admin123 | ADMIN |
| joyce@demo.com | password | CUSTOMER |
| john@demo.com | pass123 | CUSTOMER |

---

## 📝 License

This is a student project for educational purposes.

---

## 📧 Contact

For questions or issues, please contact your project team members.

---

**Happy Coding! 🚗🏍️**
