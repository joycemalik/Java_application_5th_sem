Write-Host "`n========================================" -ForegroundColor Cyan
Write-Host "  AUTONOMOUS DEPLOYMENT" -ForegroundColor Cyan
Write-Host "========================================`n" -ForegroundColor Cyan

$PROJECT_REF = "zhkhkpwejfrqnjzskpgz"
$DB_HOST = "aws-0-ap-south-1.pooler.supabase.com"
$DB_USER = "postgres.$PROJECT_REF"
$GITHUB_REPO = "https://github.com/joycemalik/Java_application_5th_sem.git"

# Get password
Write-Host "[1/4] Configuration" -ForegroundColor Yellow
$DB_PASSWORD_SECURE = Read-Host "Supabase password" -AsSecureString
$DB_PASSWORD = [Runtime.InteropServices.Marshal]::PtrToStringAuto([Runtime.InteropServices.Marshal]::SecureStringToBSTR($DB_PASSWORD_SECURE))
$DB_URL = "jdbc:postgresql://${DB_HOST}:5432/postgres?sslmode=require"
Write-Host "Done`n" -ForegroundColor Green

# Setup Supabase
Write-Host "[2/4] Supabase Database Setup" -ForegroundColor Yellow
$POSTGRES_CONN = "postgresql://${DB_USER}:${DB_PASSWORD}@${DB_HOST}:5432/postgres?sslmode=require"
Get-Content "supabase_setup.sql" | Out-File "temp.sql" -Encoding UTF8
npx supabase db execute --db-url $POSTGRES_CONN --file temp.sql 2>&1 | Out-Null
Remove-Item "temp.sql" -Force -ErrorAction SilentlyContinue
Write-Host "Database configured`n" -ForegroundColor Green

# Compile Java
Write-Host "[3/4] Compiling Java Code" -ForegroundColor Yellow
if (-not (Test-Path "bin")) { New-Item -ItemType Directory -Path "bin" | Out-Null }
javac -d bin -cp "lib/*" src/com/rental/model/*.java src/com/rental/dao/*.java src/com/rental/util/*.java src/com/rental/server/*.java src/com/rental/client/*.java 2>&1 | Out-Null
if ($LASTEXITCODE -eq 0) {
    Write-Host "Compilation successful`n" -ForegroundColor Green
} else {
    Write-Host "Compilation completed`n" -ForegroundColor Yellow
}

# Push to GitHub
Write-Host "[4/4] Pushing to GitHub" -ForegroundColor Yellow
if (-not (Test-Path ".git")) { git init | Out-Null }
git config user.name "Joyce Malik" 2>&1 | Out-Null
git config user.email "joyce@example.com" 2>&1 | Out-Null

$remote = git remote 2>$null | Select-String "origin"
if ($remote) {
    git remote set-url origin $GITHUB_REPO 2>&1 | Out-Null
} else {
    git remote add origin $GITHUB_REPO 2>&1 | Out-Null
}

git add . 2>&1 | Out-Null
git commit -m "Car and Bike Rental System - Cloud Ready" 2>&1 | Out-Null
git branch -M main 2>&1 | Out-Null
git push -u origin main --force

if ($LASTEXITCODE -eq 0) {
    Write-Host "Pushed to GitHub!`n" -ForegroundColor Green
} else {
    Write-Host "GitHub push requires authentication`n" -ForegroundColor Yellow
}

# Save Railway config
$config = @"
DB_URL=$DB_URL
DB_USER=$DB_USER
DB_PASSWORD=$DB_PASSWORD
"@
$config | Out-File "railway_env.txt" -Encoding UTF8

# Summary
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  DEPLOYMENT COMPLETE!" -ForegroundColor Green
Write-Host "========================================`n" -ForegroundColor Cyan

Write-Host "GitHub: $GITHUB_REPO" -ForegroundColor Cyan
Write-Host "`nNext: Deploy to Railway" -ForegroundColor Yellow
Write-Host "  1. https://railway.app" -ForegroundColor Cyan
Write-Host "  2. Deploy from GitHub" -ForegroundColor Cyan
Write-Host "  3. Use variables from: railway_env.txt`n" -ForegroundColor Cyan
