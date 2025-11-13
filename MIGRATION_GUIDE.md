# Quick Migration Guide - MySQL to Supabase

For those already running the local MySQL version and want to migrate to cloud.

## What Changed?

### 1. Database Driver
- **Before**: MySQL JDBC Driver (`mysql-connector-j`)
- **After**: PostgreSQL JDBC Driver (`org.postgresql:postgresql`)

### 2. Connection Configuration
- **Before**: Hardcoded in `DBUtil.java`
  ```java
  private static final String URL = "jdbc:mysql://localhost:3306/rental_db";
  private static final String USER = "root";
  private static final String PASSWORD = "root";
  ```

- **After**: Environment variables
  ```java
  private static final String URL = System.getenv("DB_URL");
  private static final String USER = System.getenv("DB_USER");
  private static final String PASSWORD = System.getenv("DB_PASSWORD");
  ```

### 3. Server Port
- **Before**: Fixed port 5000
  ```java
  private static final int PORT = 5000;
  ```

- **After**: Reads from environment (Railway compatible)
  ```java
  String portEnv = System.getenv("PORT");
  int port = (portEnv != null) ? Integer.parseInt(portEnv) : 5000;
  ```

### 4. Client Connection
- **Before**: Always connects to localhost
- **After**: Configurable for local or Railway deployment

## Migration Steps

### Step 1: Export Data from MySQL (Optional)

If you have custom data you want to keep:

```sql
-- In MySQL
SELECT * FROM users INTO OUTFILE '/tmp/users.csv' 
FIELDS TERMINATED BY ',' 
ENCLOSED BY '"' 
LINES TERMINATED BY '\n';

-- Repeat for vehicles and bookings
```

### Step 2: Set Up Supabase

Follow `CLOUD_DEPLOYMENT.md` Part 1 to:
1. Create Supabase project
2. Run `supabase_setup.sql` to create tables
3. Get connection details

### Step 3: Update Dependencies

**If using Maven** (recommended):
- Already done - see `pom.xml`

**If using manual classpath**:
- Remove: `lib/mysql-connector-j-8.2.0.jar`
- Add: Download PostgreSQL JDBC from https://jdbc.postgresql.org/download/
- Place: `lib/postgresql-42.7.4.jar`

### Step 4: Update Build Scripts

**compile.bat** - change classpath:
```batch
REM Before:
set CLASSPATH=lib\mysql-connector-j-8.2.0.jar

REM After:
set CLASSPATH=lib\postgresql-42.7.4.jar
```

**run_server.bat** - add environment variables:
```batch
@echo off
REM Set Supabase connection
set DB_URL=jdbc:postgresql://your-supabase-host:5432/postgres?sslmode=require
set DB_USER=postgres.your-ref
set DB_PASSWORD=your-password

REM Run server
set CLASSPATH=bin;lib\postgresql-42.7.4.jar
java -cp "%CLASSPATH%" com.rental.server.RentalServer
pause
```

### Step 5: Test Locally First

Before deploying to Railway, test locally with Supabase:

```powershell
# Set environment variables
$env:DB_URL = "jdbc:postgresql://your-supabase-host:5432/postgres?sslmode=require"
$env:DB_USER = "postgres.your-ref"
$env:DB_PASSWORD = "your-password"

# Compile with Maven
mvn clean package

# Run server
java -jar target/rental-system-1.0.0.jar
```

In another terminal:
```powershell
# Run client (still pointing to localhost)
java -cp bin com.rental.client.RentalClient
```

### Step 6: Import Custom Data (if needed)

If you exported data from MySQL:

1. Convert CSV to SQL INSERT statements
2. Run in Supabase SQL Editor

Or use Supabase's CSV import:
1. Go to Table Editor
2. Click table → Import data → Upload CSV

### Step 7: Deploy to Railway

Follow `CLOUD_DEPLOYMENT.md` Part 3.

## SQL Compatibility Notes

Good news: Your DAO code doesn't need changes! Here's why:

### Compatible SQL

These work the same in MySQL and PostgreSQL:
- ✅ `SELECT * FROM users WHERE email = ?`
- ✅ `INSERT INTO users (name, email, password, role) VALUES (?, ?, ?, ?)`
- ✅ `UPDATE vehicles SET available = ? WHERE id = ?`
- ✅ `DELETE FROM bookings WHERE id = ?`

### Minor Differences (already handled)

| Feature | MySQL | PostgreSQL | Status |
|---------|-------|------------|--------|
| Auto-increment | `AUTO_INCREMENT` | `SERIAL` | ✅ Fixed in `supabase_setup.sql` |
| Boolean | `BOOLEAN` or `TINYINT(1)` | `BOOLEAN` | ✅ Both supported |
| Decimal | `DECIMAL(10,2)` | `NUMERIC(10,2)` | ✅ Both work |
| Date | `DATE` | `DATE` | ✅ Same |

## Rollback Plan

If you want to go back to local MySQL:

1. Keep the old `database_setup.sql` file
2. Change `DBUtil.java` back to MySQL driver
3. Update classpath to use `mysql-connector-j.jar`
4. Set environment variables to MySQL:
   ```powershell
   $env:DB_URL = "jdbc:mysql://localhost:3306/rental_db"
   $env:DB_USER = "root"
   $env:DB_PASSWORD = "root"
   ```

## Side-by-Side Comparison

| Aspect | Local (MySQL) | Cloud (Supabase) |
|--------|---------------|------------------|
| **Database** | MySQL on localhost | PostgreSQL on Supabase |
| **Server** | Runs on your laptop | Runs on Railway.app |
| **Client** | Connects to localhost:5000 | Connects to Railway URL |
| **Setup Time** | 10 minutes | 20 minutes |
| **Cost** | Free (uses your PC) | Free tier (500MB DB) |
| **Internet Required** | No | Yes |
| **Accessible Anywhere** | No | Yes |
| **Team Collaboration** | Difficult | Easy (share Railway URL) |

## Benefits of Cloud Migration

### For Development
- ✅ No local database to maintain
- ✅ Same data across all team members
- ✅ Easy to demo to others (just share URL)
- ✅ Automatic backups (Supabase)

### For Production
- ✅ Scalable (Supabase handles DB scaling)
- ✅ Always available (no "my laptop is off")
- ✅ Professional deployment
- ✅ Easy to add monitoring/logging

## Common Migration Issues

### Issue: "SSL is required"

**Solution**: Make sure `?sslmode=require` is at end of `DB_URL`

```
jdbc:postgresql://host:5432/postgres?sslmode=require
```

### Issue: "Password authentication failed"

**Solution**: 
- Supabase passwords are case-sensitive
- Check for extra spaces
- Use connection string from Supabase dashboard directly

### Issue: "Type mismatch: SERIAL vs INT"

**Solution**: 
- Use `supabase_setup.sql` (not `database_setup.sql`)
- SERIAL is PostgreSQL's auto-increment

### Issue: "Can't connect to Railway from client"

**Solution**:
- Check Railway is deployed and running (green status)
- Verify domain in client: `your-app.up.railway.app`
- Use correct port from Railway logs

## Testing Checklist

After migration, verify:

- [ ] Register new user works
- [ ] Login with existing user works
- [ ] List cars returns data
- [ ] List bikes returns data
- [ ] Multiple clients can connect simultaneously
- [ ] Server logs show correct database connection
- [ ] Client can connect from different network (if using Railway)

---

**Questions?** See `CLOUD_DEPLOYMENT.md` for full details or `ENV_SETUP.md` for environment variable help.
