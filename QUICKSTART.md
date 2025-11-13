# Quick Start Guide

## Prerequisites Checklist
- [ ] Java JDK 8+ installed
- [ ] MySQL Server 8.0+ installed and running
- [ ] MySQL Connector/J downloaded (place in `lib/` folder)

## Setup Steps

### 1. Database Setup (5 minutes)
```bash
# Login to MySQL
mysql -u root -p

# Run the SQL script
mysql -u root -p < database_setup.sql

# Verify
mysql -u root -p -e "USE rental_db; SHOW TABLES;"
```

### 2. Configure Database Connection
Edit `src/com/rental/util/DBUtil.java`:
```java
private static final String PASSWORD = "your_mysql_password"; // CHANGE THIS
```

### 3. Download MySQL Connector
- Download: https://dev.mysql.com/downloads/connector/j/
- Extract `mysql-connector-j-8.2.0.jar`
- Place in: `d:\ait\java\group assignment\lib\`

### 4. Compile (Windows)
```powershell
# Double-click compile.bat
# OR run in PowerShell:
.\compile.bat
```

### 5. Run

**Terminal 1 - Start Server:**
```powershell
# Double-click run_server.bat
# OR run in PowerShell:
.\run_server.bat
```

**Terminal 2 - Start Client:**
```powershell
# Double-click run_client.bat
# OR run in PowerShell:
.\run_client.bat
```

## Testing

### Test Account
```
Email: joyce@demo.com
Password: password
```

### Test Flow
1. Run client
2. Choose option 2 (Login)
3. Enter credentials above
4. Choose option 3 (List Cars)
5. View available cars from database!

## Common Issues

**"Driver not found"**
→ Check `lib/mysql-connector-j-8.2.0.jar` exists

**"Connection refused"**
→ Start server before client

**"Database connection failed"**
→ Check MySQL is running and password is correct in DBUtil.java

## Project Structure Quick Reference
```
├── database_setup.sql      # Run this first in MySQL
├── compile.bat             # Compile all Java files
├── run_server.bat          # Start the server
├── run_client.bat          # Start the client
├── README.md               # Full documentation
│
├── lib/
│   └── mysql-connector-j-8.2.0.jar  # Download this
│
└── src/com/rental/
    ├── model/      # User, Vehicle, Booking
    ├── dao/        # Database access
    ├── util/       # DBUtil (configure password here)
    ├── server/     # Server code
    └── client/     # Client code
```

## Next Steps

After basic setup works:
- [ ] Add booking functionality
- [ ] Add admin features
- [ ] Build GUI client
- [ ] Add more vehicles to database

---

**Need Help?** Check README.md for detailed documentation.
