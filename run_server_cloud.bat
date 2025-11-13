@echo off
REM Local development with Supabase - Server Launcher
REM Edit this file to add your Supabase connection details

echo ========================================
echo   Rental System - Local Dev Server
echo   (Connected to Supabase Cloud DB)
echo ========================================
echo.

REM ============================================================
REM CONFIGURATION - Update these with your Supabase details
REM ============================================================

REM Get your Supabase connection details from:
REM https://app.supabase.com → Your Project → Settings → Database

REM Example format:
REM set DB_URL=jdbc:postgresql://aws-0-us-west-1.pooler.supabase.com:5432/postgres?sslmode=require
REM set DB_USER=postgres.abc123xyz
REM set DB_PASSWORD=your-secure-password

REM Joyce's Supabase Configuration
REM Update the DB_PASSWORD with your actual Supabase password!
set DB_URL=jdbc:postgresql://aws-0-ap-south-1.pooler.supabase.com:5432/postgres?sslmode=require
set DB_USER=postgres.zhkhkpwejfrqnjzskpgz
set DB_PASSWORD=YOUR_ACTUAL_PASSWORD_HERE

REM ============================================================

REM Check if variables are set
if "%DB_URL%"=="YOUR_SUPABASE_JDBC_URL_HERE" (
    echo ERROR: Database connection not configured!
    echo.
    echo Please edit run_server_cloud.bat and set:
    echo   - DB_URL
    echo   - DB_USER
    echo   - DB_PASSWORD
    echo.
    echo Get these from your Supabase dashboard:
    echo https://app.supabase.com ^> Settings ^> Database
    echo.
    pause
    exit /b 1
)

echo Configuration:
echo   DB_URL: %DB_URL%
echo   DB_USER: %DB_USER%
echo   DB_PASSWORD: [HIDDEN]
echo.
echo Starting server...
echo.

REM Set classpath
set CLASSPATH=target\rental-system-1.0.0.jar

REM Check if JAR exists
if not exist "%CLASSPATH%" (
    echo ERROR: JAR file not found!
    echo.
    echo Please build the project first:
    echo   mvn clean package
    echo.
    pause
    exit /b 1
)

REM Run the server
java -jar "%CLASSPATH%"

pause
