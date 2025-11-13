# 🚀 Complete Setup Instructions for Joyce's Rental System

Follow these steps in order to deploy your application to the cloud.

---

## ✅ Prerequisites Checklist

- [x] Supabase account created
- [x] Supabase project: https://zhkhkpwejfrqnjzskpgz.supabase.co
- [ ] Database tables created in Supabase
- [ ] GitHub repository: https://github.com/joycemalik/Java_application_5th_sem
- [ ] Railway account (create at https://railway.app)
- [ ] Git installed on your computer
- [ ] Maven installed (or use IDE)

---

## 📊 Step 1: Set Up Supabase Database

### 1.1 Open Supabase SQL Editor

1. Go to https://app.supabase.com
2. Open your project (zhkhkpwejfrqnjzskpgz)
3. Click on "SQL Editor" in left sidebar
4. Click "New query"

### 1.2 Run Database Setup Script

1. Open the file `supabase_setup.sql` in your project
2. Copy ALL the contents
3. Paste into Supabase SQL Editor
4. Click "Run" button (or press Ctrl+Enter)
5. Wait for "Success" message

### 1.3 Verify Tables Created

Run this query in SQL Editor:
```sql
SELECT table_name 
FROM information_schema.tables 
WHERE table_schema = 'public' 
ORDER BY table_name;
```

You should see:
- ✅ bookings
- ✅ users
- ✅ vehicles

### 1.4 Verify Sample Data

Run this query:
```sql
SELECT * FROM public.users;
SELECT * FROM public.vehicles;
```

You should see:
- ✅ 3 users (Admin, Joyce, John Doe)
- ✅ 6 vehicles (3 cars, 3 bikes)

---

## 🔑 Step 2: Get Supabase Connection Details

### 2.1 Get Database Password

**If you remember your password**: Skip to 2.2

**If you forgot your password**:
1. In Supabase Dashboard: Settings → Database
2. Scroll to "Database password"
3. Click "Reset database password"
4. Copy and save the new password (IMPORTANT!)

### 2.2 Get Connection String

1. In Supabase Dashboard: Settings → Database
2. Scroll to "Connection string"
3. Click "URI" tab
4. You'll see something like:
   ```
   postgresql://postgres.zhkhkpwejfrqnjzskpgz:[YOUR-PASSWORD]@aws-0-ap-south-1.pooler.supabase.com:5432/postgres
   ```
5. Note these values:
   - **Host**: `aws-0-ap-south-1.pooler.supabase.com`
   - **User**: `postgres.zhkhkpwejfrqnjzskpgz`
   - **Password**: (from step 2.1)

### 2.3 Build JDBC URL

Combine them into JDBC format:
```
jdbc:postgresql://aws-0-ap-south-1.pooler.supabase.com:5432/postgres?sslmode=require
```

**Save these three values** - you'll need them for Railway!

---

## 🧪 Step 3: Test Locally (Optional but Recommended)

Before deploying to Railway, test that everything works locally.

### 3.1 Set Environment Variables

**Windows PowerShell**:
```powershell
$env:DB_URL = "jdbc:postgresql://aws-0-ap-south-1.pooler.supabase.com:5432/postgres?sslmode=require"
$env:DB_USER = "postgres.zhkhkpwejfrqnjzskpgz"
$env:DB_PASSWORD = "YOUR_ACTUAL_PASSWORD"
```

Replace `YOUR_ACTUAL_PASSWORD` with your real Supabase password!

### 3.2 Build and Run Server

```powershell
# Build project
mvn clean package

# Run server
java -jar target/rental-system-1.0.0.jar
```

### 3.3 Expected Output

You should see:
```
PostgreSQL JDBC Driver loaded successfully.
===========================================
  Car & Bike Rental Management System
  Server starting on port 5000
  Environment: Local Development
===========================================
[SERVER] Successfully bound to port 5000
Database connection successful!
Connected to: jdbc:postgresql://aws-0-ap-south-1...
[SERVER] Waiting for client connections...
```

### 3.4 Test Client (Optional)

In another terminal:
```powershell
java -cp bin com.rental.client.RentalClient
```

Try:
- Login with: `joyce@demo.com` / `password`
- List cars
- List bikes

If everything works, continue to Railway deployment!

---

## 📤 Step 4: Push to GitHub

### 4.1 Initialize Git (if not already done)

```powershell
cd "d:\ait\java\group assignment"
git init
```

### 4.2 Add Remote Repository

```powershell
git remote add origin https://github.com/joycemalik/Java_application_5th_sem.git
```

### 4.3 Create .gitignore (already exists)

The `.gitignore` file is already created. It prevents sensitive files from being uploaded.

### 4.4 Stage All Files

```powershell
git add .
```

### 4.5 Commit Files

```powershell
git commit -m "Initial commit - Car and Bike Rental System with cloud support"
```

### 4.6 Push to GitHub

```powershell
git branch -M main
git push -u origin main
```

### 4.7 Verify on GitHub

1. Go to https://github.com/joycemalik/Java_application_5th_sem
2. Refresh the page
3. You should see all your files uploaded!

---

## 🚂 Step 5: Deploy to Railway

### 5.1 Create Railway Account

1. Go to https://railway.app
2. Click "Login" → "Login with GitHub"
3. Authorize Railway to access your GitHub account

### 5.2 Create New Project

1. Click "New Project"
2. Select "Deploy from GitHub repo"
3. Choose repository: `joycemalik/Java_application_5th_sem`
4. Click "Deploy"

Railway will automatically:
- ✅ Detect it's a Java/Maven project
- ✅ Start building
- ✅ Create a deployment

### 5.3 Configure Environment Variables

**IMPORTANT**: Do this BEFORE the deployment finishes!

1. Click on your service name in Railway
2. Go to "Variables" tab
3. Click "New Variable"
4. Add these three variables:

**Variable 1**:
- Name: `DB_URL`
- Value: `jdbc:postgresql://aws-0-ap-south-1.pooler.supabase.com:5432/postgres?sslmode=require`

**Variable 2**:
- Name: `DB_USER`
- Value: `postgres.zhkhkpwejfrqnjzskpgz`

**Variable 3**:
- Name: `DB_PASSWORD`
- Value: `your_actual_supabase_password`

5. Railway will automatically redeploy with new variables

### 5.4 Generate Public Domain

1. Go to "Settings" tab
2. Scroll to "Networking" section
3. Click "Generate Domain"
4. You'll get a URL like: `rental-system-production.up.railway.app`
5. **Save this URL!**

### 5.5 Check Deployment Status

1. Go to "Deployments" tab
2. Click on the latest deployment
3. Watch the logs
4. Wait for deployment to succeed (green checkmark ✅)

### 5.6 Verify Deployment Logs

Look for these lines in the logs:
```
PostgreSQL JDBC Driver loaded successfully.
Database connection successful!
Server starting on port XXXX
[SERVER] Successfully bound to port XXXX
[SERVER] Waiting for client connections...
```

If you see errors, check:
- ✅ Environment variables are set correctly
- ✅ No typos in DB_URL, DB_USER, DB_PASSWORD
- ✅ Supabase project is not paused

---

## 💻 Step 6: Configure Client to Connect to Railway

### 6.1 Get Railway Connection Details

From Railway logs, note:
- **Host**: Your Railway domain (e.g., `rental-system-production.up.railway.app`)
- **Port**: Check logs for "Server starting on port XXXX"

### 6.2 Update Client Code

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
private static final int SERVER_PORT = 443; // or the port from Railway logs
```

### 6.3 Recompile Client

```powershell
javac -d bin src/com/rental/model/*.java src/com/rental/client/*.java
```

### 6.4 Run Client

```powershell
java -cp bin com.rental.client.RentalClient
```

---

## 🎉 Step 7: Test Everything!

### 7.1 Test Connection

When you run the client, you should see:
```
Connected to server at rental-system-production.up.railway.app:443
Server: WELCOME
```

### 7.2 Test Login

1. Choose option 2 (Login)
2. Email: `joyce@demo.com`
3. Password: `password`
4. You should see: "Login successful! Welcome, Joyce (CUSTOMER)!"

### 7.3 Test Vehicle Listing

1. Choose option 3 (List Cars)
2. You should see 3 cars with details
3. Choose option 4 (List Bikes)
4. You should see 3 bikes with details

### 7.4 Test Registration

1. Choose option 1 (Register)
2. Enter your own details
3. You should get a user ID

### 7.5 Test Multi-Client

1. Open another terminal
2. Run the client again
3. Both clients should work simultaneously!

---

## ✅ Deployment Complete!

If all tests pass, congratulations! Your application is now:
- ✅ Database on Supabase (cloud)
- ✅ Server on Railway (cloud)
- ✅ Code on GitHub
- ✅ Accessible from anywhere!

---

## 🔗 Your Project URLs

**GitHub Repository**:
https://github.com/joycemalik/Java_application_5th_sem

**Supabase Dashboard**:
https://app.supabase.com/project/zhkhkpwejfrqnjzskpgz

**Railway Deployment**:
https://railway.app (your project dashboard)

**Public Server URL**:
`your-app-name.up.railway.app` (from Railway)

---

## 🐛 Troubleshooting

### Problem: Can't connect to Railway

**Check**:
1. Railway deployment is successful (green checkmark)
2. Railway domain is correct
3. Port number is correct
4. No firewall blocking the connection

### Problem: Database connection failed

**Check**:
1. Environment variables are set in Railway
2. No typos in DB_URL, DB_USER, DB_PASSWORD
3. Password is correct
4. Supabase project is active (not paused)

### Problem: Build failed on Railway

**Check**:
1. `pom.xml` is in repository
2. All Java files are committed
3. No compilation errors locally

### Problem: Client can't connect

**Check**:
1. Server is running on Railway
2. CLIENT_HOST matches Railway domain
3. CLIENT_PORT matches Railway logs

---

## 📞 Need Help?

1. Check Railway logs for errors
2. Check Supabase logs for database issues
3. Verify all steps were followed
4. Try local testing first before Railway

---

## 🎯 Next Steps

After successful deployment:
1. Share Railway URL with friends/classmates
2. Add more features (booking system)
3. Implement admin panel
4. Build GUI with JavaFX

---

**Congratulations on your cloud deployment! 🎊**
