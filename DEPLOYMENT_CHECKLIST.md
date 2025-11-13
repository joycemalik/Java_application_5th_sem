# ✅ Deployment Checklist

Use this checklist to ensure successful deployment to the cloud.

---

## 📋 Pre-Deployment Checklist

### Prerequisites
- [ ] Java JDK 8+ installed
- [ ] Maven installed (or IDE with Maven support)
- [ ] Git installed
- [ ] GitHub account created
- [ ] Supabase account created (free tier)
- [ ] Railway account created (free tier)

### Code Verification
- [ ] Code compiles without errors
  ```powershell
  mvn clean compile
  ```
- [ ] No hardcoded credentials in code
- [ ] `.gitignore` file includes sensitive files
- [ ] All DAO implementations are complete
- [ ] Model classes have all getters/setters

---

## ☁️ Supabase Setup Checklist

### 1. Create Supabase Project
- [ ] Logged into https://supabase.com
- [ ] Created new project
- [ ] Saved database password (IMPORTANT!)
- [ ] Selected region (closest to you)
- [ ] Waited for project to finish provisioning

### 2. Database Setup
- [ ] Opened SQL Editor in Supabase dashboard
- [ ] Copied contents of `supabase_setup.sql`
- [ ] Executed SQL script successfully
- [ ] Verified tables created:
  - [ ] `public.users` table exists
  - [ ] `public.vehicles` table exists
  - [ ] `public.bookings` table exists

### 3. Verify Sample Data
- [ ] Run: `SELECT COUNT(*) FROM public.users;` (should return 3)
- [ ] Run: `SELECT COUNT(*) FROM public.vehicles;` (should return 6)
- [ ] Run: `SELECT * FROM public.users;` (see demo accounts)

### 4. Get Connection Details
- [ ] Copied connection string from Settings → Database
- [ ] Extracted these values:
  - [ ] Host: `aws-0-[region].pooler.supabase.com`
  - [ ] Port: `5432`
  - [ ] Database: `postgres`
  - [ ] User: `postgres.[project-ref]`
  - [ ] Password: (from step 1)
- [ ] Built JDBC URL:
  ```
  jdbc:postgresql://[host]:5432/postgres?sslmode=require
  ```

---

## 🔧 Local Testing Checklist

### Test with Supabase (Before Railway)
- [ ] Set environment variables:
  ```powershell
  $env:DB_URL = "jdbc:postgresql://..."
  $env:DB_USER = "postgres...."
  $env:DB_PASSWORD = "your-password"
  ```
- [ ] Compiled project: `mvn clean package`
- [ ] Server starts without errors
- [ ] Verified database connection in logs
- [ ] Client connects to local server
- [ ] Login with demo account works
- [ ] List cars/bikes returns data

### Test Results
- [ ] No "Driver not found" errors
- [ ] No "Connection refused" errors
- [ ] No "Authentication failed" errors
- [ ] User registration works
- [ ] User login works
- [ ] Vehicle listing works

---

## 🚂 Railway Deployment Checklist

### 1. GitHub Setup
- [ ] Created GitHub repository
- [ ] Added `.gitignore` file
- [ ] Committed all files:
  ```bash
  git add .
  git commit -m "Initial commit"
  git push origin main
  ```
- [ ] Verified all files uploaded to GitHub
- [ ] Confirmed `pom.xml` is present

### 2. Railway Project Setup
- [ ] Logged into https://railway.app
- [ ] Created new project
- [ ] Selected "Deploy from GitHub repo"
- [ ] Authorized Railway to access repository
- [ ] Selected correct repository

### 3. Railway Configuration
- [ ] Railway detected Java/Maven project
- [ ] Build command set (auto or manual):
  ```
  mvn clean package -DskipTests
  ```
- [ ] Start command set (auto or manual):
  ```
  java -jar target/rental-system-1.0.0.jar
  ```

### 4. Environment Variables
- [ ] Added `DB_URL` variable (from Supabase)
- [ ] Added `DB_USER` variable (from Supabase)
- [ ] Added `DB_PASSWORD` variable (from Supabase)
- [ ] DID NOT add `PORT` (Railway sets this automatically)
- [ ] Verified no typos in variable names
- [ ] Verified no extra spaces in values

### 5. Deployment
- [ ] Railway started building
- [ ] Build completed successfully
- [ ] Deployment shows green status
- [ ] Checked deployment logs for errors

### 6. Networking
- [ ] Generated public domain in Railway
- [ ] Noted Railway URL: `____________.up.railway.app`
- [ ] Noted port from logs: `Port: _____`
- [ ] Tested URL is accessible

---

## 💻 Client Configuration Checklist

### Update Client Code
- [ ] Opened `src/com/rental/client/RentalClient.java`
- [ ] Updated `SERVER_HOST`:
  ```java
  private static final String SERVER_HOST = "your-app.up.railway.app";
  ```
- [ ] Updated `SERVER_PORT` (from Railway logs):
  ```java
  private static final int SERVER_PORT = 12345; // your port
  ```
- [ ] Saved file

### Compile Client
- [ ] Compiled client:
  ```powershell
  javac -d bin src/com/rental/model/*.java src/com/rental/client/*.java
  ```
- [ ] No compilation errors

### Test Client
- [ ] Ran client:
  ```powershell
  java -cp bin com.rental.client.RentalClient
  ```
- [ ] Saw "Connected to server" message
- [ ] Saw "Server: WELCOME" message
- [ ] Menu displayed correctly

---

## 🧪 End-to-End Testing Checklist

### Test 1: User Registration
- [ ] Selected "Register" from menu
- [ ] Entered test user details
- [ ] Registration successful
- [ ] Received user ID

### Test 2: User Login
- [ ] Selected "Login" from menu
- [ ] Entered `joyce@demo.com` / `password`
- [ ] Login successful
- [ ] Welcome message displayed

### Test 3: List Vehicles
- [ ] Logged in first
- [ ] Selected "List Available Cars"
- [ ] Saw 3 cars displayed
- [ ] Selected "List Available Bikes"
- [ ] Saw 3 bikes displayed

### Test 4: Multi-Client
- [ ] Opened second terminal
- [ ] Ran second client instance
- [ ] Both clients connected simultaneously
- [ ] Both could login independently
- [ ] Both could list vehicles

### Test 5: Error Handling
- [ ] Tried login with wrong password (got error)
- [ ] Tried listing vehicles without login (got error)
- [ ] Server didn't crash on errors

---

## 📊 Verification Checklist

### Railway Dashboard
- [ ] Deployment status: Green ✅
- [ ] Latest deployment successful
- [ ] No crash loops
- [ ] Metrics showing activity
- [ ] Logs showing client connections

### Supabase Dashboard
- [ ] Database online and active
- [ ] No connection errors
- [ ] Query logs showing activity
- [ ] Data integrity maintained

### Application Logs
- [ ] Server logs show:
  - [ ] "PostgreSQL JDBC Driver loaded successfully"
  - [ ] "Database connection successful"
  - [ ] "Server starting on port..."
  - [ ] Client connection messages
  - [ ] No SQL errors
  - [ ] No authentication failures

---

## 🎉 Success Criteria

Your deployment is successful if:

- ✅ Server running on Railway without errors
- ✅ Database on Supabase accessible
- ✅ Client can connect from your laptop
- ✅ Login works with demo accounts
- ✅ Vehicle listing returns data
- ✅ Multiple clients can connect
- ✅ No crashes or errors in logs

---

## 🐛 If Something Goes Wrong

### Server won't start on Railway
→ Check deployment logs for errors  
→ Verify `pom.xml` is correct  
→ Check environment variables are set  

### Can't connect from client
→ Verify Railway domain is correct  
→ Check port number from logs  
→ Ensure Railway deployment is running  

### Database connection fails
→ Double-check DB_URL has `?sslmode=require`  
→ Verify username format: `postgres.project-ref`  
→ Test connection from Supabase dashboard  

### Authentication errors
→ Verify password is correct (case-sensitive)  
→ Check no extra spaces in environment variables  
→ Try resetting Supabase database password  

---

## 📝 Post-Deployment Tasks

After successful deployment:

- [ ] Document your Railway URL
- [ ] Document your Supabase connection (securely)
- [ ] Share Railway URL with team (if applicable)
- [ ] Test from different network/location
- [ ] Monitor Railway logs for errors
- [ ] Check Supabase usage metrics
- [ ] Plan next features (booking, admin panel)

---

## 🔒 Security Checklist

- [ ] No passwords in source code
- [ ] No credentials in GitHub
- [ ] `.gitignore` includes `.env` files
- [ ] Supabase password is strong
- [ ] Railway environment variables are private
- [ ] Not using default passwords

---

## 📱 Share with Others

Ready to demo? Share:

**Server URL**: `https://your-app.up.railway.app:PORT`

**Demo Accounts**:
- Email: `joyce@demo.com` Password: `password`
- Email: `john@demo.com` Password: `pass123`

**How to connect**:
1. Download client JAR
2. Update `SERVER_HOST` in code
3. Compile and run client
4. Login and explore!

---

## ✨ Deployment Complete!

Congratulations! Your Java application is now:
- ✅ Running in the cloud (Railway)
- ✅ Using cloud database (Supabase)
- ✅ Accessible from anywhere
- ✅ Production-ready

**Next**: Add booking functionality, admin features, or build a GUI!

---

**Questions?** See [CLOUD_DEPLOYMENT.md](CLOUD_DEPLOYMENT.md) for detailed guides.
