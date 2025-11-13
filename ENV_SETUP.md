# Environment Variables Setup Guide

This file documents the environment variables needed for the application.

## Required Environment Variables

### For Database Connection (Supabase)

```bash
# PostgreSQL JDBC connection URL
DB_URL=jdbc:postgresql://aws-0-region.pooler.supabase.com:5432/postgres?sslmode=require

# Database username (from Supabase)
DB_USER=postgres.your-project-ref

# Database password (set when creating Supabase project)
DB_PASSWORD=your-secure-password
```

### For Railway Deployment

```bash
# Railway automatically sets this - DO NOT set manually
PORT=<automatically-assigned-by-railway>
```

---

## Setting Up Locally (Windows PowerShell)

### Temporary (for current session only)

```powershell
$env:DB_URL = "jdbc:postgresql://your-supabase-host:5432/postgres?sslmode=require"
$env:DB_USER = "postgres.your-ref"
$env:DB_PASSWORD = "your-password"
```

### Permanent (add to PowerShell profile)

```powershell
# Edit profile
notepad $PROFILE

# Add these lines:
$env:DB_URL = "jdbc:postgresql://your-supabase-host:5432/postgres?sslmode=require"
$env:DB_USER = "postgres.your-ref"
$env:DB_PASSWORD = "your-password"
```

---

## Setting Up Locally (Linux/Mac)

### Temporary

```bash
export DB_URL="jdbc:postgresql://your-supabase-host:5432/postgres?sslmode=require"
export DB_USER="postgres.your-ref"
export DB_PASSWORD="your-password"
```

### Permanent

```bash
# Add to ~/.bashrc or ~/.zshrc
echo 'export DB_URL="jdbc:postgresql://your-supabase-host:5432/postgres?sslmode=require"' >> ~/.bashrc
echo 'export DB_USER="postgres.your-ref"' >> ~/.bashrc
echo 'export DB_PASSWORD="your-password"' >> ~/.bashrc

# Reload
source ~/.bashrc
```

---

## Setting Up in Railway

1. Go to your Railway project dashboard
2. Click on your service
3. Go to **"Variables"** tab
4. Click **"+ New Variable"**
5. Add each variable:
   - Variable: `DB_URL`
   - Value: `jdbc:postgresql://...`
   - Click "Add"
6. Repeat for `DB_USER` and `DB_PASSWORD`
7. **DO NOT** set `PORT` - Railway sets this automatically

---

## How to Get Supabase Connection Details

### Method 1: From Supabase Dashboard

1. Open your Supabase project
2. Click **Settings** (gear icon) → **Database**
3. Scroll to **Connection string**
4. Select **URI** tab
5. You'll see: `postgresql://postgres.[REF]:[PASSWORD]@[HOST]:5432/postgres`

### Method 2: From Connection Pooler

For better performance on Railway:

1. In Supabase Dashboard → **Database** → **Connection pooling**
2. Use the **Transaction** mode connection string
3. Format: `postgres://postgres.[REF]:[PASSWORD]@aws-0-[region].pooler.supabase.com:5432/postgres`

### Convert to JDBC URL

Replace `postgresql://` with `jdbc:postgresql://` and add `?sslmode=require`:

```
Before:  postgresql://postgres.abc123:[PASSWORD]@aws-0-us-west-1.pooler.supabase.com:5432/postgres
After:   jdbc:postgresql://aws-0-us-west-1.pooler.supabase.com:5432/postgres?sslmode=require
```

Then split into:
- **DB_URL**: `jdbc:postgresql://aws-0-us-west-1.pooler.supabase.com:5432/postgres?sslmode=require`
- **DB_USER**: `postgres.abc123`
- **DB_PASSWORD**: `your-password`

---

## Verification

### Test Database Connection

Create a simple test file `TestConnection.java`:

```java
package com.rental.util;

public class TestConnection {
    public static void main(String[] args) {
        System.out.println("Testing database connection...");
        boolean success = DBUtil.testConnection();
        System.exit(success ? 0 : 1);
    }
}
```

Run it:

```powershell
# Set env vars first, then:
javac -cp target/classes src/com/rental/util/TestConnection.java
java -cp target/classes com.rental.util.TestConnection
```

Expected output:
```
Testing database connection...
PostgreSQL JDBC Driver loaded successfully.
Database connection successful!
Connected to: jdbc:postgresql://aws-0-us-west-1.pooler.supabase.com:5432/postgres
```

---

## Security Best Practices

### ✅ DO:
- Use environment variables for all sensitive data
- Use different passwords for local and production
- Rotate passwords regularly
- Use connection pooling for production

### ❌ DON'T:
- Hardcode credentials in source code
- Commit `.env` files to Git
- Share credentials in chat/email
- Use weak passwords

---

## Example .env File (for reference only - DO NOT COMMIT)

Create `.env` file in project root:

```ini
# Supabase Database Connection
DB_URL=jdbc:postgresql://aws-0-us-west-1.pooler.supabase.com:5432/postgres?sslmode=require
DB_USER=postgres.abc123xyz
DB_PASSWORD=your-secure-password-here

# Railway (auto-set, documented here for reference)
# PORT=5000
```

Add to `.gitignore`:
```
.env
*.env
```

---

## Troubleshooting

### "DB_URL environment variable is not set"

- **Cause**: Environment variables not loaded
- **Solution**: 
  - Windows: Run `$env:DB_URL` to verify it's set
  - Linux/Mac: Run `echo $DB_URL`
  - If empty, set them as shown above

### "Connection refused"

- **Cause**: Wrong host or port
- **Solution**: 
  - Verify Supabase connection string
  - Check if `5432` is the correct port
  - Ensure `?sslmode=require` is at the end

### "Authentication failed"

- **Cause**: Wrong username or password
- **Solution**:
  - Double-check password (no extra spaces)
  - Verify username format: `postgres.your-ref`
  - Reset password in Supabase if needed

---

**Next Steps**: See `CLOUD_DEPLOYMENT.md` for full deployment guide.
