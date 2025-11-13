# Cloud Deployment Guide - Supabase + Railway

This guide will help you deploy your Car and Bike Rental System to the cloud with **zero local database dependencies**.

## 🌐 Architecture (Cloud Setup)

```
┌─────────────────────────────────┐
│  Your Laptop                    │
│  └─ RentalClient.java           │
│     (Java console app)          │
└────────────┬────────────────────┘
             │ TCP Socket
             ▼
┌─────────────────────────────────┐
│  Railway.app (Cloud Server)     │
│  └─ RentalServer.java           │
│     └─ ClientHandler.java       │
│     └─ DAO Layer                │
└────────────┬────────────────────┘
             │ JDBC
             ▼
┌─────────────────────────────────┐
│  Supabase (Cloud PostgreSQL)    │
│  └─ users table                 │
│  └─ vehicles table              │
│  └─ bookings table              │
└─────────────────────────────────┘
```

**What runs where:**
- **Supabase**: PostgreSQL database (cloud)
- **Railway**: Java server (cloud)
- **Your Laptop**: Java client (local)

---

## 📋 Prerequisites

- GitHub account (free)
- Supabase account (free tier: https://supabase.com)
- Railway account (free tier: https://railway.app)
- Java 8+ installed locally (for running client)
- Maven installed (optional, Railway can build without it)

---

## Part 1: Set Up Supabase (Cloud Database)

### Step 1.1: Create Supabase Project

1. Go to https://supabase.com and sign in
2. Click **"New Project"**
3. Fill in:
   - **Name**: `rental-system-db`
   - **Database Password**: Choose a strong password (SAVE THIS!)
   - **Region**: Select closest to you
4. Wait 2-3 minutes for project to provision

### Step 1.2: Get Database Connection Details

1. In your Supabase project dashboard, click **"Project Settings"** (gear icon)
2. Go to **"Database"** section
3. Find **"Connection string"** and select **"URI"**
4. You'll see something like:
   ```
   postgresql://postgres.[PROJECT-REF]:[PASSWORD]@aws-0-[region].pooler.supabase.com:5432/postgres
   ```

5. Extract these values:
   - **Host**: `aws-0-[region].pooler.supabase.com`
   - **Port**: `5432`
   - **Database**: `postgres`
   - **User**: `postgres.[PROJECT-REF]`
   - **Password**: Your password from Step 1.1

6. Build your JDBC URL:
   ```
   jdbc:postgresql://aws-0-[region].pooler.supabase.com:5432/postgres?sslmode=require
   ```

### Step 1.3: Create Database Tables

1. In Supabase dashboard, go to **"SQL Editor"**
2. Click **"New query"**
3. Copy and paste the contents of `supabase_setup.sql`
4. Click **"Run"** or press `Ctrl+Enter`
5. You should see success messages

### Step 1.4: Verify Data

Run this in SQL Editor:
```sql
SELECT * FROM public.users;
SELECT * FROM public.vehicles;
```

You should see 3 users and 6 vehicles.

---

## Part 2: Prepare Code for Deployment

### Step 2.1: Update .gitignore (if not exists)

Create `.gitignore` in project root:
```
# Compiled files
bin/
target/
*.class

# IDE files
.vscode/
.idea/
*.iml

# OS files
.DS_Store
Thumbs.db

# Local scripts
compile.bat
run_server.bat
run_client.bat

# Logs
*.log
```

### Step 2.2: Test Locally with Supabase

Before deploying, test that your code can connect to Supabase:

**Windows PowerShell:**
```powershell
# Set environment variables (use your Supabase values)
$env:DB_URL = "jdbc:postgresql://aws-0-region.pooler.supabase.com:5432/postgres?sslmode=require"
$env:DB_USER = "postgres.your-project-ref"
$env:DB_PASSWORD = "your-supabase-password"

# Compile and run server
mvn clean package
java -jar target/rental-system-1.0.0.jar
```

If you see **"Database connection successful!"** and **"Connected to: jdbc:postgresql://..."**, you're good!

---

## Part 3: Deploy to Railway

### Step 3.1: Push Code to GitHub

1. Create a new GitHub repository (e.g., `rental-system`)
2. In your project folder:
   ```powershell
   git init
   git add .
   git commit -m "Initial commit - cloud deployment ready"
   git branch -M main
   git remote add origin https://github.com/YOUR-USERNAME/rental-system.git
   git push -u origin main
   ```

### Step 3.2: Create Railway Project

1. Go to https://railway.app and sign in with GitHub
2. Click **"New Project"**
3. Select **"Deploy from GitHub repo"**
4. Choose your `rental-system` repository
5. Railway will auto-detect Java/Maven and start building

### Step 3.3: Configure Environment Variables

1. In Railway dashboard, click your deployed service
2. Go to **"Variables"** tab
3. Click **"+ New Variable"** and add these:

   ```
   DB_URL = jdbc:postgresql://aws-0-region.pooler.supabase.com:5432/postgres?sslmode=require
   DB_USER = postgres.your-project-ref
   DB_PASSWORD = your-supabase-password
   ```

   **Important:** Use your actual Supabase connection details from Part 1!

4. Railway will automatically redeploy with new variables

### Step 3.4: Configure Networking

1. In Railway dashboard, go to **"Settings"** tab
2. Scroll to **"Networking"**
3. Click **"Generate Domain"**
4. You'll get a public URL like: `rental-system-production.up.railway.app`
5. **Note the port**: Railway will expose your TCP server on a specific port
   - Check deployment logs to see: `Server starting on port XXXXX`

### Step 3.5: Verify Deployment

Check the **"Deployments"** tab and view logs. You should see:
```
PostgreSQL JDBC Driver loaded successfully.
Server starting on port 12345
[SERVER] Successfully bound to port 12345
[SERVER] Waiting for client connections...
```

---

## Part 4: Connect Client to Cloud Server

### Step 4.1: Update Client Configuration

Edit `src/com/rental/client/RentalClient.java`:

Find these lines:
```java
// For LOCAL development:
private static final String SERVER_HOST = "localhost";
private static final int SERVER_PORT = 5000;
```

Change to:
```java
// For RAILWAY deployment:
private static final String SERVER_HOST = "rental-system-production.up.railway.app";
private static final int SERVER_PORT = 12345; // Use port from Railway logs
```

### Step 4.2: Compile and Run Client Locally

```powershell
# Compile client only
javac -d bin src/com/rental/model/*.java src/com/rental/client/*.java

# Run client (it will connect to Railway server)
java -cp bin com.rental.client.RentalClient
```

### Step 4.3: Test the Connection

If everything works, you should see:
```
Connected to server at rental-system-production.up.railway.app:12345
Server: WELCOME

───────────────────────────────────────────
            MAIN MENU
───────────────────────────────────────────
```

Try logging in:
- Email: `joyce@demo.com`
- Password: `password`

---

## 🎯 Quick Reference

### Local Development (with Supabase)

```powershell
# Set environment variables
$env:DB_URL = "jdbc:postgresql://your-supabase-host:5432/postgres?sslmode=require"
$env:DB_USER = "postgres.your-ref"
$env:DB_PASSWORD = "your-password"

# Run server locally
mvn clean package
java -jar target/rental-system-1.0.0.jar
```

### Client Configuration

| Environment | SERVER_HOST | SERVER_PORT |
|-------------|-------------|-------------|
| Local Server | `localhost` | `5000` |
| Railway Server | `your-app.up.railway.app` | Check Railway logs |

### Environment Variables (Railway)

| Variable | Example Value |
|----------|---------------|
| `DB_URL` | `jdbc:postgresql://aws-0-us-west-1.pooler.supabase.com:5432/postgres?sslmode=require` |
| `DB_USER` | `postgres.abc123xyz` |
| `DB_PASSWORD` | `your-secure-password` |
| `PORT` | *(Auto-set by Railway)* |

---

## 🐛 Troubleshooting

### Problem: "PostgreSQL JDBC Driver not found"

**Solution:**
```powershell
# Make sure you have pom.xml in your project
# Then run:
mvn clean install
```

### Problem: "DB_URL environment variable is not set"

**Solution:** 
- **Local**: Set environment variables before running
- **Railway**: Add variables in Railway dashboard → Variables tab

### Problem: Client can't connect to Railway server

**Solution:**
1. Check Railway deployment logs for the actual port
2. Verify the domain is correct (Railway → Settings → Networking)
3. Test with `telnet your-app.up.railway.app PORT` to verify TCP connectivity

### Problem: "Connection refused" from Railway

**Solution:**
- Make sure your server binds to `0.0.0.0` (it does by default with `ServerSocket(port)`)
- Check Railway logs for errors
- Verify environment variables are set in Railway

### Problem: Database connection fails from Railway

**Solution:**
1. Verify Supabase connection string in Railway variables
2. Make sure `?sslmode=require` is at the end of DB_URL
3. Test connection from Supabase dashboard first
4. Check Supabase isn't paused (free tier auto-pauses after inactivity)

---

## 💰 Cost Breakdown (Free Tier)

| Service | Free Tier | Limits |
|---------|-----------|--------|
| **Supabase** | ✅ Free | 500 MB database, 2 GB bandwidth/month |
| **Railway** | ✅ $5 credit/month | ~500 hours/month runtime |
| **Total** | **$0/month** | Perfect for development & demos |

---

## 🚀 Next Steps

### Phase 1: Test Everything
- [x] Database on Supabase
- [x] Server on Railway
- [x] Client on your laptop
- [ ] Register a new user from client
- [ ] Login and list vehicles
- [ ] Test with multiple clients simultaneously

### Phase 2: Add Features
- [ ] Implement booking functionality
- [ ] Add admin features (requires admin login)
- [ ] Email notifications (use Supabase Auth)

### Phase 3: Production-Ready
- [ ] Add password hashing (BCrypt)
- [ ] Use connection pooling (HikariCP)
- [ ] Add SSL/TLS for client-server connection
- [ ] Set up custom domain on Railway
- [ ] Add monitoring and logging

---

## 📚 Useful Links

- **Supabase Docs**: https://supabase.com/docs/guides/database
- **Railway Docs**: https://docs.railway.app
- **PostgreSQL JDBC**: https://jdbc.postgresql.org/documentation/
- **Project Repository**: (Your GitHub URL)

---

## 🆘 Need Help?

If you encounter issues:

1. **Check Railway Logs**: Railway Dashboard → Deployments → View Logs
2. **Check Supabase Status**: Supabase Dashboard → Database → Health
3. **Test Locally First**: Run server locally with Supabase before deploying
4. **Verify Environment Variables**: Railway → Variables (make sure no typos)

---

**Congratulations! Your Java app is now running in the cloud!** 🎉

No local MySQL, no local server—just pure cloud deployment with Supabase + Railway.
