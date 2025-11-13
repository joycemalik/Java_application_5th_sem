# Car and Bike Rental Management System - Cloud Edition

A complete Java-based client-server application with cloud database (Supabase PostgreSQL) and cloud hosting (Railway) support.

## 🌟 Two Deployment Options

| Feature | Local Setup | Cloud Setup |
|---------|-------------|-------------|
| Database | MySQL on localhost | PostgreSQL on Supabase |
| Server | Runs on your laptop | Hosted on Railway |
| Client | Local Java app | Local Java app (connects to cloud) |
| Internet Required | No | Yes |
| Cost | Free | Free (with limits) |
| Team Access | No | Yes |
| Setup Time | 10 min | 20 min |

**Choose Cloud if you want:**
- ✅ No local database installation
- ✅ Access from anywhere
- ✅ Easy team collaboration
- ✅ Professional deployment

**Choose Local if you want:**
- ✅ Offline development
- ✅ Complete control
- ✅ Learning database administration

---

## 🚀 Quick Start - Cloud Deployment (Recommended)

### Prerequisites
- GitHub account (free)
- Supabase account (free)
- Railway account (free)
- Java 8+ installed

### 5-Minute Setup

1. **Set up Supabase Database**
   ```
   • Go to https://supabase.com → New Project
   • Save your password!
   • SQL Editor → Run supabase_setup.sql
   • Get connection details from Settings → Database
   ```

2. **Deploy to Railway**
   ```
   • Push code to GitHub
   • Go to https://railway.app → New Project
   • Deploy from GitHub repo
   • Add environment variables:
     - DB_URL (from Supabase)
     - DB_USER (from Supabase)  
     - DB_PASSWORD (from Supabase)
   • Get your Railway URL
   ```

3. **Run Client**
   ```powershell
   • Edit RentalClient.java:
     - Change SERVER_HOST to your Railway URL
   • Compile and run:
     mvn package
     java -cp bin com.rental.client.RentalClient
   ```

**Detailed Guide**: See [CLOUD_DEPLOYMENT.md](CLOUD_DEPLOYMENT.md)

---

## 📖 Documentation

### Main Guides
- **[CLOUD_DEPLOYMENT.md](CLOUD_DEPLOYMENT.md)** - Complete cloud setup guide (Supabase + Railway)
- **[ENV_SETUP.md](ENV_SETUP.md)** - Environment variables configuration
- **[MIGRATION_GUIDE.md](MIGRATION_GUIDE.md)** - Migrate from local MySQL to cloud
- **[README_LOCAL.md](README.md)** - Original local setup guide

### Quick References
- **Demo Accounts**: See credentials below
- **Architecture**: See diagrams in CLOUD_DEPLOYMENT.md
- **Troubleshooting**: Each guide has a dedicated section

---

## 🏗️ Project Structure

```
d:\ait\java\group assignment\
│
├── 📄 pom.xml                        # Maven build configuration
├── 📄 Dockerfile                     # Docker deployment (optional)
├── 📄 railway.toml                   # Railway configuration
├── 📄 .gitignore                     # Git ignore rules
│
├── 📁 Documentation/
│   ├── CLOUD_DEPLOYMENT.md          # ⭐ Cloud setup guide
│   ├── ENV_SETUP.md                 # Environment variables
│   ├── MIGRATION_GUIDE.md           # MySQL → Supabase migration
│   └── README_LOCAL.md              # Local MySQL setup
│
├── 📁 Database/
│   ├── supabase_setup.sql           # PostgreSQL schema for Supabase
│   └── database_setup.sql           # MySQL schema (legacy)
│
└── 📁 src/com/rental/
    ├── 📁 model/                    # Data models (POJOs)
    │   ├── User.java
    │   ├── Vehicle.java
    │   └── Booking.java
    │
    ├── 📁 dao/                      # Database access layer
    │   ├── UserDAO.java
    │   ├── UserDAOImpl.java
    │   ├── VehicleDAO.java
    │   └── VehicleDAOImpl.java
    │
    ├── 📁 util/                     # Utilities
    │   └── DBUtil.java              # Database connection manager
    │
    ├── 📁 server/                   # Server application
    │   ├── RentalServer.java        # Main server
    │   └── ClientHandler.java       # Client connection handler
    │
    └── 📁 client/                   # Client application
        └── RentalClient.java        # Console-based client
```

---

## ✨ Features

### Current Implementation
- ✅ User Registration & Authentication
- ✅ Login/Logout System
- ✅ List Available Cars
- ✅ List Available Bikes
- ✅ Multi-client TCP Server
- ✅ Cloud Database Support (Supabase)
- ✅ Cloud Hosting Support (Railway)
- ✅ Environment-based Configuration
- ✅ Connection Pooling Ready

### Coming Soon
- 🔜 Vehicle Booking System
- 🔜 Booking History
- 🔜 Admin Panel (Vehicle Management)
- 🔜 Payment Processing
- 🔜 GUI Client (JavaFX)

---

## 🧪 Demo Accounts

Pre-created accounts in the database:

| Email | Password | Role | Notes |
|-------|----------|------|-------|
| admin@rental.com | admin123 | ADMIN | Full access (future) |
| joyce@demo.com | password | CUSTOMER | Test account |
| john@demo.com | pass123 | CUSTOMER | Test account |

---

## 🛠️ Technologies

### Backend
- **Java SE 8+** - Core language
- **JDBC** - Database connectivity
- **PostgreSQL** - Database (via Supabase)
- **TCP Sockets** - Client-server communication
- **Multi-threading** - Concurrent client handling

### Cloud Services
- **Supabase** - Managed PostgreSQL database
- **Railway** - Application hosting platform
- **GitHub** - Version control & deployment source

### Build Tools
- **Maven** - Dependency management & build
- **Docker** - Containerization (optional)

---

## 📊 Database Schema

### Tables

**users** - Customer and admin accounts
```sql
id (SERIAL), name, email (UNIQUE), password, role
```

**vehicles** - Car and bike inventory
```sql
id (SERIAL), type, brand, model, reg_number (UNIQUE), 
price_per_day, available
```

**bookings** - Rental reservations
```sql
id (SERIAL), user_id, vehicle_id, start_date, end_date,
total_price, status
```

**Relationships:**
- bookings.user_id → users.id
- bookings.vehicle_id → vehicles.id

---

## 🌐 Network Protocol

Simple text-based protocol using pipe (`|`) delimiters.

### Client → Server Commands

```
REGISTER|name|email|password
LOGIN|email|password
LIST_VEHICLES|CAR
LIST_VEHICLES|BIKE
LOGOUT
```

### Server → Client Responses

```
OK|COMMAND|data1|data2|...
ERROR|error message
```

**Example Flow:**
```
Client: LOGIN|joyce@demo.com|password
Server: OK|LOGIN|Joyce|CUSTOMER

Client: LIST_VEHICLES|CAR
Server: OK|LIST_VEHICLES|3|1,Toyota,Innova,KA01AB1234,2000.00|...
```

---

## 💻 Development Setup

### Option 1: Cloud (Recommended)

```powershell
# 1. Set environment variables
$env:DB_URL = "jdbc:postgresql://your-supabase-url:5432/postgres?sslmode=require"
$env:DB_USER = "postgres.your-ref"
$env:DB_PASSWORD = "your-password"

# 2. Build with Maven
mvn clean package

# 3. Run server locally (connects to Supabase)
java -jar target/rental-system-1.0.0.jar

# 4. Run client (in another terminal)
java -cp bin com.rental.client.RentalClient
```

### Option 2: Local MySQL

See [README_LOCAL.md](README.md) for local MySQL setup.

---

## 🚢 Deployment

### Deploy Server to Railway

1. **Prepare Repository**
   ```bash
   git add .
   git commit -m "Ready for deployment"
   git push origin main
   ```

2. **Configure Railway**
   - Create new project from GitHub repo
   - Set environment variables (DB_URL, DB_USER, DB_PASSWORD)
   - Railway auto-builds and deploys

3. **Get Public URL**
   - Railway dashboard → Networking → Generate Domain
   - Note: `your-app.up.railway.app`

4. **Update Client**
   ```java
   // In RentalClient.java
   private static final String SERVER_HOST = "your-app.up.railway.app";
   private static final int SERVER_PORT = 443; // from Railway logs
   ```

**Full Guide**: [CLOUD_DEPLOYMENT.md](CLOUD_DEPLOYMENT.md)

---

## 🧪 Testing

### Test Cloud Connection

```powershell
# Test database connection
mvn test-compile
java -cp target/test-classes com.rental.util.DBUtil

# Expected output:
# PostgreSQL JDBC Driver loaded successfully.
# Database connection successful!
# Connected to: jdbc:postgresql://...
```

### Test Server

```powershell
# Start server
java -jar target/rental-system-1.0.0.jar

# Expected output:
# Server starting on port 5000
# [SERVER] Waiting for client connections...
```

### Test Client

```powershell
# Run client
java -cp bin com.rental.client.RentalClient

# Try these actions:
# 1. Login with joyce@demo.com / password
# 2. List cars (should show 3 vehicles)
# 3. List bikes (should show 3 vehicles)
```

---

## 🔒 Security Notes

### Current Implementation
- ✅ Passwords stored as plain text (for learning)
- ✅ Environment variables for credentials
- ✅ SSL required for database (Supabase)

### Production Recommendations
- 🔐 Hash passwords (BCrypt/Argon2)
- 🔐 Use JWT tokens for sessions
- 🔐 Add TLS/SSL for client-server
- 🔐 Implement rate limiting
- 🔐 Add input validation/sanitization

---

## 💰 Cost Analysis

### Free Tier Limits

| Service | Free Tier | Limits |
|---------|-----------|--------|
| **Supabase** | ✅ Free forever | 500 MB database, 2 GB bandwidth/mo |
| **Railway** | $5 credit/mo | ~500 hours runtime |
| **GitHub** | ✅ Free forever | Unlimited public repos |

**Estimated Monthly Cost**: $0 (stays within free tiers)

**Upgrade Path**: When you exceed free tier, costs are reasonable:
- Supabase Pro: $25/mo (8 GB database)
- Railway: Pay-as-you-go (~$5-10/mo for small apps)

---

## 🐛 Troubleshooting

### Common Issues

**"PostgreSQL JDBC Driver not found"**
```powershell
# Solution: Install dependencies
mvn clean install
```

**"DB_URL environment variable is not set"**
```powershell
# Solution: Set environment variables
$env:DB_URL = "jdbc:postgresql://..."
$env:DB_USER = "postgres.your-ref"
$env:DB_PASSWORD = "your-password"
```

**"Connection refused" from Railway**
```
# Solution: 
1. Check Railway deployment status (should be green)
2. Verify domain and port in client
3. Check Railway logs for errors
```

**"SSL is required"**
```
# Solution: Add ?sslmode=require to DB_URL
jdbc:postgresql://host:5432/postgres?sslmode=require
```

**More Help**: See troubleshooting sections in:
- [CLOUD_DEPLOYMENT.md](CLOUD_DEPLOYMENT.md#-troubleshooting)
- [ENV_SETUP.md](ENV_SETUP.md#troubleshooting)

---

## 📚 Learning Resources

### Understanding the Stack
- **Supabase**: https://supabase.com/docs
- **Railway**: https://docs.railway.app
- **PostgreSQL JDBC**: https://jdbc.postgresql.org/documentation/
- **Java Sockets**: https://docs.oracle.com/javase/tutorial/networking/sockets/

### Extending the Project
- **Add Password Hashing**: BCrypt tutorial
- **Build GUI**: JavaFX getting started
- **Add REST API**: Spring Boot basics
- **Database Pooling**: HikariCP documentation

---

## 🤝 Contributing

This is a student project. Feel free to:
- Add new features (booking system, admin panel)
- Improve UI (JavaFX client)
- Add tests (JUnit)
- Enhance security
- Optimize performance

---

## 📄 License

Educational project - free to use and modify.

---

## 🎯 Next Steps

### For Beginners
1. ✅ Deploy to cloud (follow CLOUD_DEPLOYMENT.md)
2. ✅ Test with demo accounts
3. ✅ Register your own account
4. ✅ Share Railway URL with friends to test multi-client

### For Advanced
1. 🚀 Implement booking system
2. 🚀 Add admin panel
3. 🚀 Build JavaFX GUI
4. 🚀 Add password hashing
5. 🚀 Implement JWT authentication

---

**Questions or Issues?**
- Check documentation in CLOUD_DEPLOYMENT.md
- Review ENV_SETUP.md for configuration help
- See MIGRATION_GUIDE.md if migrating from MySQL

**Happy Coding! 🚗🏍️☁️**
