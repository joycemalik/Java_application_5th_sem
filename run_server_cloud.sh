#!/bin/bash
# Local development with Supabase - Server Launcher (Linux/Mac)
# Edit this file to add your Supabase connection details

echo "========================================"
echo "  Rental System - Local Dev Server"
echo "  (Connected to Supabase Cloud DB)"
echo "========================================"
echo ""

# ============================================================
# CONFIGURATION - Update these with your Supabase details
# ============================================================

# Get your Supabase connection details from:
# https://app.supabase.com → Your Project → Settings → Database

# Example format:
# export DB_URL="jdbc:postgresql://aws-0-us-west-1.pooler.supabase.com:5432/postgres?sslmode=require"
# export DB_USER="postgres.abc123xyz"
# export DB_PASSWORD="your-secure-password"

# Joyce's Supabase Configuration
# Update the DB_PASSWORD with your actual Supabase password!
export DB_URL="jdbc:postgresql://aws-0-ap-south-1.pooler.supabase.com:5432/postgres?sslmode=require"
export DB_USER="postgres.zhkhkpwejfrqnjzskpgz"
export DB_PASSWORD="YOUR_ACTUAL_PASSWORD_HERE"

# ============================================================

# Check if variables are set
if [ "$DB_URL" = "YOUR_SUPABASE_JDBC_URL_HERE" ]; then
    echo "ERROR: Database connection not configured!"
    echo ""
    echo "Please edit run_server_cloud.sh and set:"
    echo "  - DB_URL"
    echo "  - DB_USER"
    echo "  - DB_PASSWORD"
    echo ""
    echo "Get these from your Supabase dashboard:"
    echo "https://app.supabase.com > Settings > Database"
    echo ""
    exit 1
fi

echo "Configuration:"
echo "  DB_URL: $DB_URL"
echo "  DB_USER: $DB_USER"
echo "  DB_PASSWORD: [HIDDEN]"
echo ""
echo "Starting server..."
echo ""

# Set classpath
CLASSPATH="target/rental-system-1.0.0.jar"

# Check if JAR exists
if [ ! -f "$CLASSPATH" ]; then
    echo "ERROR: JAR file not found!"
    echo ""
    echo "Please build the project first:"
    echo "  mvn clean package"
    echo ""
    exit 1
fi

# Run the server
java -jar "$CLASSPATH"
