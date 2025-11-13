# 🔐 Your Supabase Connection Details

**Project**: Joyce's Rental System  
**Project URL**: https://zhkhkpwejfrqnjzskpgz.supabase.co

---

## 📊 Database Connection String

### For JDBC (Java Application)

**Direct Connection (Pooler)**:
```
Host: aws-0-ap-south-1.pooler.supabase.com
Port: 5432
Database: postgres
```

**Full JDBC URL**:
```
jdbc:postgresql://aws-0-ap-south-1.pooler.supabase.com:5432/postgres?sslmode=require
```

> **Note**: The exact host will be shown in your Supabase Dashboard under Settings → Database

---

## 🔑 Environment Variables

### For Local Development (Windows PowerShell)

```powershell
# Set these environment variables before running locally
$env:DB_URL = "jdbc:postgresql://aws-0-ap-south-1.pooler.supabase.com:5432/postgres?sslmode=require"
$env:DB_USER = "postgres.zhkhkpwejfrqnjzskpgz"
$env:DB_PASSWORD = "YOUR_DATABASE_PASSWORD_HERE"
```

Replace `YOUR_DATABASE_PASSWORD_HERE` with the password you set when creating the Supabase project.

### For Railway Deployment

Add these variables in Railway Dashboard → Variables:

```
DB_URL = jdbc:postgresql://aws-0-ap-south-1.pooler.supabase.com:5432/postgres?sslmode=require
DB_USER = postgres.zhkhkpwejfrqnjzskpgz
DB_PASSWORD = your_actual_password
```

---

## 📝 Getting Your Connection Details

### Step 1: Get Database Password
1. Go to https://app.supabase.com
2. Open your project
3. If you forgot password: Settings → Database → Reset Database Password

### Step 2: Get Full Connection String
1. In Supabase Dashboard: Settings → Database
2. Scroll to "Connection string"
3. Select "URI" tab
4. Copy the connection string (looks like):
   ```
   postgresql://postgres.zhkhkpwejfrqnjzskpgz:[YOUR-PASSWORD]@aws-0-ap-south-1.pooler.supabase.com:5432/postgres
   ```

### Step 3: Convert to JDBC Format
Change `postgresql://` to `jdbc:postgresql://` and add `?sslmode=require`:

```
jdbc:postgresql://aws-0-ap-south-1.pooler.supabase.com:5432/postgres?sslmode=require
```

---

## ✅ Quick Test

### Test Database Connection

1. Set environment variables (see above)
2. Run:
   ```powershell
   mvn clean package
   java -jar target/rental-system-1.0.0.jar
   ```
3. Look for:
   ```
   PostgreSQL JDBC Driver loaded successfully.
   Database connection successful!
   Connected to: jdbc:postgresql://aws-0-ap-south-1...
   ```

---

## 🚨 Security Notes

- ✅ **DO**: Keep password secret
- ✅ **DO**: Use environment variables
- ❌ **DON'T**: Commit password to GitHub
- ❌ **DON'T**: Share password in chat/email

---

## 📞 Support

If connection fails:
1. Verify password is correct
2. Check Supabase project is not paused
3. Ensure `?sslmode=require` is at end of URL
4. Test connection in Supabase SQL Editor first

---

**Next Step**: Follow [SETUP_INSTRUCTIONS.md](SETUP_INSTRUCTIONS.md) to complete setup!
