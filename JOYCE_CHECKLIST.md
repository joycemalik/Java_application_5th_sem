# ✅ Joyce's Deployment Checklist

**Project**: Car and Bike Rental System  
**Date**: November 13, 2025

---

## 📋 Pre-Deployment

### Supabase Setup
- [x] Supabase account created
- [x] Project created: zhkhkpwejfrqnjzskpgz
- [x] Project URL: https://zhkhkpwejfrqnjzskpgz.supabase.co
- [ ] Database password saved securely
- [ ] SQL script `supabase_setup.sql` executed
- [ ] Tables verified (users, vehicles, bookings)
- [ ] Sample data verified (3 users, 6 vehicles)

### GitHub Setup
- [x] GitHub account: joycemalik
- [x] Repository created: Java_application_5th_sem
- [x] Repository URL: https://github.com/joycemalik/Java_application_5th_sem
- [ ] Local git initialized
- [ ] Remote added
- [ ] Code pushed

### Railway Setup
- [ ] Railway account created
- [ ] Connected to GitHub
- [ ] Project created from repository
- [ ] Environment variables configured
- [ ] Public domain generated

---

## 🔑 Your Connection Details

### Supabase

**JDBC URL**:
```
jdbc:postgresql://aws-0-ap-south-1.pooler.supabase.com:5432/postgres?sslmode=require
```

**Database User**:
```
postgres.zhkhkpwejfrqnjzskpgz
```

**Database Password**:
```
[GET FROM SUPABASE DASHBOARD]
```
- Go to: Settings → Database → Connection string

---

## 📝 Step-by-Step Tasks

### Task 1: Setup Supabase Database ⏱️ 5 minutes

- [ ] Open https://app.supabase.com/project/zhkhkpwejfrqnjzskpgz
- [ ] Click "SQL Editor" in sidebar
- [ ] Click "New query"
- [ ] Open file: `supabase_setup.sql`
- [ ] Copy entire file contents
- [ ] Paste into SQL Editor
- [ ] Click "Run" button
- [ ] Verify success message
- [ ] Run verification query:
  ```sql
  SELECT COUNT(*) FROM public.users;
  SELECT COUNT(*) FROM public.vehicles;
  ```
- [ ] Confirm: 3 users, 6 vehicles

**Status**: [ ] Complete

---

### Task 2: Get Database Password ⏱️ 2 minutes

- [ ] In Supabase: Settings → Database
- [ ] Scroll to "Connection string"
- [ ] Click "URI" tab
- [ ] Copy the connection string
- [ ] Extract password (between `:` and `@`)
- [ ] Save password in secure location
- [ ] Test: Can you see the password?

**Your Password**: _________________ (write on paper, not commit!)

**Status**: [ ] Complete

---

### Task 3: Test Locally ⏱️ 10 minutes

- [ ] Open PowerShell
- [ ] Navigate to project:
  ```powershell
  cd "d:\ait\java\group assignment"
  ```
- [ ] Set environment variables:
  ```powershell
  $env:DB_URL = "jdbc:postgresql://aws-0-ap-south-1.pooler.supabase.com:5432/postgres?sslmode=require"
  $env:DB_USER = "postgres.zhkhkpwejfrqnjzskpgz"
  $env:DB_PASSWORD = "YOUR_PASSWORD_HERE"
  ```
- [ ] Build project:
  ```powershell
  mvn clean package
  ```
- [ ] Run server:
  ```powershell
  java -jar target/rental-system-1.0.0.jar
  ```
- [ ] Verify output:
  - [ ] "PostgreSQL JDBC Driver loaded successfully"
  - [ ] "Database connection successful"
  - [ ] "Server starting on port 5000"
- [ ] Open new terminal, run client:
  ```powershell
  java -cp bin com.rental.client.RentalClient
  ```
- [ ] Test login: joyce@demo.com / password
- [ ] List cars (should show 3)
- [ ] List bikes (should show 3)

**Status**: [ ] Complete

---

### Task 4: Push to GitHub ⏱️ 5 minutes

- [ ] In PowerShell, in project directory:
  ```powershell
  git init
  ```
- [ ] Add remote:
  ```powershell
  git remote add origin https://github.com/joycemalik/Java_application_5th_sem.git
  ```
- [ ] Stage files:
  ```powershell
  git add .
  ```
- [ ] Commit:
  ```powershell
  git commit -m "Car and Bike Rental System - Initial Commit"
  ```
- [ ] Push:
  ```powershell
  git branch -M main
  git push -u origin main
  ```
- [ ] Open GitHub: https://github.com/joycemalik/Java_application_5th_sem
- [ ] Verify files uploaded:
  - [ ] src/ folder
  - [ ] pom.xml
  - [ ] supabase_setup.sql
  - [ ] README files
  - [ ] All documentation

**Status**: [ ] Complete

---

### Task 5: Deploy to Railway ⏱️ 15 minutes

#### 5.1 Create Project

- [ ] Open https://railway.app
- [ ] Click "Login" → "Login with GitHub"
- [ ] Authorize Railway
- [ ] Click "New Project"
- [ ] Select "Deploy from GitHub repo"
- [ ] Choose: joycemalik/Java_application_5th_sem
- [ ] Click "Deploy"
- [ ] Wait for initial build to start

#### 5.2 Configure Environment Variables

- [ ] Click on your service name
- [ ] Go to "Variables" tab
- [ ] Click "New Variable"
- [ ] Add Variable 1:
  - Name: `DB_URL`
  - Value: `jdbc:postgresql://aws-0-ap-south-1.pooler.supabase.com:5432/postgres?sslmode=require`
- [ ] Add Variable 2:
  - Name: `DB_USER`
  - Value: `postgres.zhkhkpwejfrqnjzskpgz`
- [ ] Add Variable 3:
  - Name: `DB_PASSWORD`
  - Value: [YOUR ACTUAL PASSWORD]
- [ ] Verify no typos in any variable
- [ ] Railway will auto-redeploy

#### 5.3 Generate Domain

- [ ] Go to "Settings" tab
- [ ] Scroll to "Networking"
- [ ] Click "Generate Domain"
- [ ] Copy your domain (e.g., xyz.up.railway.app)
- [ ] Save domain: _______________________

#### 5.4 Verify Deployment

- [ ] Go to "Deployments" tab
- [ ] Click latest deployment
- [ ] Check logs for:
  - [ ] "PostgreSQL JDBC Driver loaded successfully"
  - [ ] "Database connection successful"
  - [ ] "Server starting on port XXXX"
  - [ ] No errors
- [ ] Status shows green checkmark ✅

**Status**: [ ] Complete

---

### Task 6: Connect Client to Railway ⏱️ 5 minutes

- [ ] Open: `src/com/rental/client/RentalClient.java`
- [ ] Find lines (around line 18):
  ```java
  private static final String SERVER_HOST = "localhost";
  private static final int SERVER_PORT = 5000;
  ```
- [ ] Replace with:
  ```java
  private static final String SERVER_HOST = "YOUR_RAILWAY_DOMAIN";
  private static final int SERVER_PORT = 443;
  ```
- [ ] Save file
- [ ] Recompile:
  ```powershell
  javac -d bin src/com/rental/model/*.java src/com/rental/client/*.java
  ```
- [ ] Run client:
  ```powershell
  java -cp bin com.rental.client.RentalClient
  ```
- [ ] Verify connection message
- [ ] Test login with joyce@demo.com / password
- [ ] Test listing vehicles

**Status**: [ ] Complete

---

## 🎉 Final Verification

### End-to-End Test

- [ ] Client connects to Railway server
- [ ] Login works with demo account
- [ ] List cars shows 3 vehicles
- [ ] List bikes shows 3 vehicles
- [ ] Register new user works
- [ ] Multiple clients can connect simultaneously

### Documentation Check

- [ ] SETUP_INSTRUCTIONS.md is clear
- [ ] COMMANDS.md has all commands
- [ ] SUPABASE_CONFIG.md has connection details
- [ ] START_HERE.md guides to right place

---

## 📊 Deployment Summary

### What's Running Where

| Component | Location | Status |
|-----------|----------|--------|
| Database | Supabase (cloud) | [ ] Active |
| Server | Railway (cloud) | [ ] Running |
| Code | GitHub | [ ] Uploaded |
| Client | Your laptop | [ ] Working |

### URLs to Save

```
Supabase Dashboard:
https://app.supabase.com/project/zhkhkpwejfrqnjzskpgz

GitHub Repository:
https://github.com/joycemalik/Java_application_5th_sem

Railway Project:
https://railway.app/project/[YOUR_PROJECT_ID]

Public Server:
https://[YOUR_DOMAIN].up.railway.app
```

---

## 🐛 If Something Goes Wrong

### Database Connection Failed
→ Check password is correct
→ Verify Supabase project is active
→ Ensure `?sslmode=require` is in URL

### Railway Build Failed
→ Check all files are in GitHub
→ Verify pom.xml is correct
→ Review Railway build logs

### Client Can't Connect
→ Verify Railway deployment is green
→ Check Railway domain is correct
→ Ensure server is running (check logs)

---

## 🎯 Next Steps After Deployment

- [ ] Share Railway URL with friends/classmates
- [ ] Test from different devices
- [ ] Add booking functionality
- [ ] Implement admin features
- [ ] Build GUI with JavaFX
- [ ] Add password hashing

---

## 📱 Demo Information

**To share with others:**

```
Server: [Your Railway URL]
Demo Account: joyce@demo.com
Password: password

Features:
✅ User registration
✅ User login
✅ List available cars
✅ List available bikes
✅ Multi-client support
```

---

## ✅ Completion Status

**Overall Progress**: _____ / 6 tasks complete

- [ ] Task 1: Supabase Database Setup
- [ ] Task 2: Get Database Password
- [ ] Task 3: Test Locally
- [ ] Task 4: Push to GitHub
- [ ] Task 5: Deploy to Railway
- [ ] Task 6: Connect Client

**Estimated Total Time**: 42 minutes

**Actual Time Taken**: _______ minutes

---

## 🏆 Success Criteria

Your deployment is successful when:

✅ Supabase database has 3 users and 6 vehicles  
✅ Local server connects to Supabase  
✅ Code is on GitHub  
✅ Railway deployment shows green status  
✅ Client connects to Railway server  
✅ Login, list cars, list bikes all work  
✅ No errors in Railway logs  

---

**Ready to start?** Follow Task 1 above! 🚀

**Questions?** See [SETUP_INSTRUCTIONS.md](SETUP_INSTRUCTIONS.md) for detailed help.
