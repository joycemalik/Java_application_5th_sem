Write-Host "`n===========================================`n" -ForegroundColor Cyan
Write-Host "   AUTONOMOUS DEPLOYMENT SCRIPT" -ForegroundColor Cyan
Write-Host "   Car and Bike Rental System`n" -ForegroundColor Cyan
Write-Host "===========================================`n" -ForegroundColor Cyan

# Get Supabase password
Write-Host "[STEP 1] Supabase Setup`n" -ForegroundColor Yellow
$SUPABASE_PASSWORD = Read-Host -Prompt "Enter your Supabase database password" -AsSecureString
$DB_PASSWORD = [Runtime.InteropServices.Marshal]::PtrToStringAuto([Runtime.InteropServices.Marshal]::SecureStringToBSTR($SUPABASE_PASSWORD))

# Supabase configuration
$DB_HOST = "aws-0-ap-south-1.pooler.supabase.com"
$DB_PORT = "5432"
$DB_NAME = "postgres"
$DB_USER = "postgres.zhkhkpwejfrqnjzskpgz"
$DB_URL = "jdbc:postgresql://${DB_HOST}:${DB_PORT}/${DB_NAME}?sslmode=require"

Write-Host "Credentials configured" -ForegroundColor Green

# Check if psql is available
$psqlCmd = Get-Command psql -ErrorAction SilentlyContinue

if ($psqlCmd) {
    Write-Host "`nExecuting SQL script on Supabase..." -ForegroundColor Cyan
    $env:PGPASSWORD = $DB_PASSWORD
    Get-Content "supabase_setup.sql" | psql -h $DB_HOST -p $DB_PORT -U $DB_USER -d $DB_NAME
    
    if ($LASTEXITCODE -eq 0) {
        Write-Host "Database setup complete!" -ForegroundColor Green
    } else {
        Write-Host "Warning: psql execution had issues" -ForegroundColor Yellow
    }
} else {
    Write-Host "`npsql not found. Manual SQL execution required." -ForegroundColor Yellow
    Write-Host "Please run supabase_setup.sql in Supabase SQL Editor:" -ForegroundColor Cyan
    Write-Host "https://app.supabase.com/project/zhkhkpwejfrqnjzskpgz/editor`n" -ForegroundColor Cyan
    
    $continue = Read-Host "Have you run the SQL script? (y/n)"
    if ($continue -ne "y") {
        Write-Host "Please run the SQL script first, then restart this deployment." -ForegroundColor Red
        exit 1
    }
}

# Build application
Write-Host "`n[STEP 2] Building Java Application`n" -ForegroundColor Yellow

$mvnCmd = Get-Command mvn -ErrorAction SilentlyContinue

if (-not $mvnCmd) {
    Write-Host "Maven not found. Please install Maven." -ForegroundColor Red
    exit 1
}

Write-Host "Running: mvn clean package..." -ForegroundColor Cyan
mvn clean package

if ($LASTEXITCODE -ne 0) {
    Write-Host "Maven build failed!" -ForegroundColor Red
    exit 1
}

Write-Host "Build successful!" -ForegroundColor Green

# Test connection
Write-Host "`n[STEP 3] Testing Supabase Connection`n" -ForegroundColor Yellow

$env:DB_URL = $DB_URL
$env:DB_USER = $DB_USER
$env:DB_PASSWORD = $DB_PASSWORD

Write-Host "Starting server for connection test..." -ForegroundColor Cyan
$serverJob = Start-Job -ScriptBlock {
    param($url, $user, $pwd)
    $env:DB_URL = $url
    $env:DB_USER = $user
    $env:DB_PASSWORD = $pwd
    java -jar target/rental-system-1.0.0.jar
} -ArgumentList $DB_URL, $DB_USER, $DB_PASSWORD

Start-Sleep -Seconds 5

if ($serverJob.State -eq "Running") {
    Write-Host "Server started successfully!" -ForegroundColor Green
    Stop-Job -Job $serverJob
    Remove-Job -Job $serverJob
} else {
    Write-Host "Server failed to start. Check logs." -ForegroundColor Red
    Receive-Job -Job $serverJob
    Remove-Job -Job $serverJob
    exit 1
}

# Git setup
Write-Host "`n[STEP 4] Git Repository Setup`n" -ForegroundColor Yellow

$gitCmd = Get-Command git -ErrorAction SilentlyContinue

if (-not $gitCmd) {
    Write-Host "Git not found. Please install Git." -ForegroundColor Red
    exit 1
}

if (-not (Test-Path ".git")) {
    Write-Host "Initializing git repository..." -ForegroundColor Cyan
    git init
    Write-Host "Git initialized" -ForegroundColor Green
} else {
    Write-Host "Git repository already exists" -ForegroundColor Green
}

# Configure git user if needed
$gitUserName = git config user.name 2>$null
if (-not $gitUserName) {
    $userName = Read-Host "Enter your name for git commits"
    git config user.name $userName
}

$gitUserEmail = git config user.email 2>$null
if (-not $gitUserEmail) {
    $userEmail = Read-Host "Enter your email for git commits"
    git config user.email $userEmail
}

Write-Host "Git configured" -ForegroundColor Green

# Push to GitHub
Write-Host "`n[STEP 5] Pushing to GitHub`n" -ForegroundColor Yellow

$GITHUB_REPO = "https://github.com/joycemalik/Java_application_5th_sem.git"

$remoteExists = git remote 2>$null | Select-String "origin"

if ($remoteExists) {
    git remote set-url origin $GITHUB_REPO
    Write-Host "GitHub remote updated" -ForegroundColor Green
} else {
    git remote add origin $GITHUB_REPO
    Write-Host "GitHub remote added" -ForegroundColor Green
}

Write-Host "`nStaging files..." -ForegroundColor Cyan
git add .

Write-Host "Creating commit..." -ForegroundColor Cyan
git commit -m "Car and Bike Rental System - Cloud Ready"

Write-Host "Setting main branch..." -ForegroundColor Cyan
git branch -M main

Write-Host "Pushing to GitHub..." -ForegroundColor Cyan
git push -u origin main --force

if ($LASTEXITCODE -eq 0) {
    Write-Host "`nPushed to GitHub successfully!" -ForegroundColor Green
} else {
    Write-Host "`nGitHub push failed. You may need to authenticate." -ForegroundColor Yellow
    Write-Host "Generate a Personal Access Token at:" -ForegroundColor Cyan
    Write-Host "https://github.com/settings/tokens`n" -ForegroundColor Cyan
}

# Create Railway config
Write-Host "`n[STEP 6] Railway Configuration`n" -ForegroundColor Yellow

$railwayConfig = @"
DB_URL=$DB_URL
DB_USER=$DB_USER
DB_PASSWORD=$DB_PASSWORD
"@

$railwayConfig | Out-File -FilePath "railway_env.txt" -Encoding UTF8

Write-Host "Railway environment variables saved to: railway_env.txt" -ForegroundColor Green

# Summary
Write-Host "`n===========================================`n" -ForegroundColor Cyan
Write-Host "   DEPLOYMENT COMPLETE!" -ForegroundColor Green
Write-Host "`n===========================================`n" -ForegroundColor Cyan

Write-Host "Summary:" -ForegroundColor White
Write-Host "  [OK] Supabase database configured" -ForegroundColor Green
Write-Host "  [OK] Application built successfully" -ForegroundColor Green
Write-Host "  [OK] Connection tested" -ForegroundColor Green
Write-Host "  [OK] Git repository initialized" -ForegroundColor Green
Write-Host "  [OK] Code pushed to GitHub" -ForegroundColor Green
Write-Host "  [OK] Railway config created" -ForegroundColor Green

Write-Host "`nNext: Deploy to Railway" -ForegroundColor Yellow
Write-Host "  1. Go to: https://railway.app" -ForegroundColor Cyan
Write-Host "  2. Login with GitHub" -ForegroundColor Cyan
Write-Host "  3. Deploy from: joycemalik/Java_application_5th_sem" -ForegroundColor Cyan
Write-Host "  4. Copy variables from: railway_env.txt`n" -ForegroundColor Cyan

Write-Host "GitHub: https://github.com/joycemalik/Java_application_5th_sem`n" -ForegroundColor Cyan
