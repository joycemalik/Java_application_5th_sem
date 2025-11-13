# 🚀 Quick Command Reference

All commands you need to deploy your application.

---

## 📊 Step 1: Setup Supabase Database

### Run SQL Script in Supabase

1. Go to: https://app.supabase.com/project/zhkhkpwejfrqnjzskpgz
2. Click "SQL Editor"
3. Copy all contents of `supabase_setup.sql`
4. Paste and click "Run"

---

## 🔑 Step 2: Set Your Supabase Password

**Get your database password** from Supabase:
- Dashboard → Settings → Database → Connection string

**Save it securely!** You'll need it for:
- Local testing
- Railway deployment

---

## 🧪 Step 3: Test Locally (Optional)

### Set Environment Variables (Windows PowerShell)

```powershell
# Navigate to project folder
cd "d:\ait\java\group assignment"

# Set Supabase connection (REPLACE YOUR_PASSWORD!)
$env:DB_URL = "jdbc:postgresql://aws-0-ap-south-1.pooler.supabase.com:5432/postgres?sslmode=require"
$env:DB_USER = "postgres.zhkhkpwejfrqnjzskpgz"
$env:DB_PASSWORD = "YOUR_ACTUAL_PASSWORD"
```

### Build Project

```powershell
mvn clean package
```

### Run Server

```powershell
java -jar target/rental-system-1.0.0.jar
```

**Expected**: Server starts, connects to Supabase successfully

### Run Client (in new terminal)

```powershell
cd "d:\ait\java\group assignment"
java -cp bin com.rental.client.RentalClient
```

**Test**: Login with `joyce@demo.com` / `password`

---

## 📤 Step 4: Push to GitHub

### Initialize and Push

```powershell
cd "d:\ait\java\group assignment"

# Initialize git (if not already done)
git init

# Add remote
git remote add origin https://github.com/joycemalik/Java_application_5th_sem.git

# Stage all files
git add .

# Commit
git commit -m "Car and Bike Rental System - Cloud Ready"

# Push to GitHub
git branch -M main
git push -u origin main
```

### Verify Upload

Go to: https://github.com/joycemalik/Java_application_5th_sem
- Refresh page
- Check all files are there

---

## 🚂 Step 5: Deploy to Railway

### 5.1 Create Project

1. Go to: https://railway.app
2. Login with GitHub
3. New Project → Deploy from GitHub repo
4. Select: `joycemalik/Java_application_5th_sem`

### 5.2 Set Environment Variables

In Railway Dashboard → Variables tab:

```
Variable 1:
Name:  DB_URL
Value: jdbc:postgresql://aws-0-ap-south-1.pooler.supabase.com:5432/postgres?sslmode=require

Variable 2:
Name:  DB_USER
Value: postgres.zhkhkpwejfrqnjzskpgz

Variable 3:
Name:  DB_PASSWORD
Value: your_actual_supabase_password
```

### 5.3 Generate Domain

Settings → Networking → Generate Domain

**Save your Railway URL!**

---

## 💻 Step 6: Connect Client to Railway

### Update Client Code

Edit `src/com/rental/client/RentalClient.java`:

**Find line ~18:**
```java
private static final String SERVER_HOST = "localhost";
private static final int SERVER_PORT = 5000;
```

**Replace with** (use your Railway domain):
```java
private static final String SERVER_HOST = "your-app.up.railway.app";
private static final int SERVER_PORT = 443;
```

### Recompile Client

```powershell
javac -d bin src/com/rental/model/*.java src/com/rental/client/*.java
```

### Run Client

```powershell
java -cp bin com.rental.client.RentalClient
```

---

## ✅ Test Your Deployment

### Login Test
- Email: `joyce@demo.com`
- Password: `password`

### List Vehicles
- Option 3: List Cars → Should show 3 cars
- Option 4: List Bikes → Should show 3 bikes

### Register New User
- Option 1: Register
- Create your own account

---

## 🔄 Update Code Later

### Make Changes

1. Edit your code locally
2. Test locally first

### Push Updates

```powershell
git add .
git commit -m "Description of changes"
git push origin main
```

Railway will **automatically redeploy**!

---

## 🐛 Quick Troubleshooting

### Local Server Won't Start

```powershell
# Check environment variables are set
echo $env:DB_URL
echo $env:DB_USER
echo $env:DB_PASSWORD

# If empty, set them again
```

### Railway Build Failed

- Check `pom.xml` is in repository
- Verify all Java files committed
- Check Railway logs for errors

### Client Can't Connect

- Verify Railway deployment is successful (green ✅)
- Check SERVER_HOST matches Railway domain
- Check Railway server is running (logs show "Waiting for connections")

### Database Connection Failed

- Verify password is correct
- Check Supabase project is active
- Ensure `?sslmode=require` is in DB_URL

---

## 📋 Complete Command Flow

**Quick deployment from scratch:**

```powershell
# 1. Test locally
cd "d:\ait\java\group assignment"
$env:DB_URL = "jdbc:postgresql://aws-0-ap-south-1.pooler.supabase.com:5432/postgres?sslmode=require"
$env:DB_USER = "postgres.zhkhkpwejfrqnjzskpgz"
$env:DB_PASSWORD = "YOUR_PASSWORD"
mvn clean package
java -jar target/rental-system-1.0.0.jar

# 2. Push to GitHub (new terminal)
git add .
git commit -m "Deploy ready"
git push origin main

# 3. Deploy to Railway (web browser)
# - railway.app → New Project → GitHub repo
# - Add environment variables
# - Generate domain

# 4. Run client
javac -d bin src/com/rental/model/*.java src/com/rental/client/*.java
java -cp bin com.rental.client.RentalClient
```

---

## 🔗 Important URLs

**Your Supabase**:
https://app.supabase.com/project/zhkhkpwejfrqnjzskpgz

**Your GitHub**:
https://github.com/joycemalik/Java_application_5th_sem

**Railway**:
https://railway.app (your dashboard)

---

## 📱 Share Your App

After deployment, share:
- **Server URL**: Your Railway domain
- **Demo Account**: `joyce@demo.com` / `password`

Anyone can connect their client to your Railway server!

---

**Need detailed help?** See [SETUP_INSTRUCTIONS.md](SETUP_INSTRUCTIONS.md)
