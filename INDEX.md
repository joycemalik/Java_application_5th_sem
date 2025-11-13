# 📚 Documentation Index

Welcome to the Car and Bike Rental Management System documentation!

## 🎯 Start Here

**New to the project?** → Read [README_CLOUD.md](README_CLOUD.md)  
**Want to deploy?** → Follow [CLOUD_DEPLOYMENT.md](CLOUD_DEPLOYMENT.md)  
**Just browsing?** → Check [PROJECT_SUMMARY.md](PROJECT_SUMMARY.md)

---

## 📖 Complete Documentation

### 🚀 Getting Started

| Document | Purpose | Audience | Time |
|----------|---------|----------|------|
| [README_CLOUD.md](README_CLOUD.md) | Main README for cloud deployment | Everyone | 5 min |
| [PROJECT_SUMMARY.md](PROJECT_SUMMARY.md) | Overview of what's included | Everyone | 3 min |
| [QUICKSTART.md](QUICKSTART.md) | Original local setup (MySQL) | Local development | 5 min |

### ☁️ Cloud Deployment

| Document | Purpose | Audience | Time |
|----------|---------|----------|------|
| [CLOUD_DEPLOYMENT.md](CLOUD_DEPLOYMENT.md) | Complete Supabase + Railway guide | Deploying to cloud | 30 min |
| [ENV_SETUP.md](ENV_SETUP.md) | Environment variables setup | All deployments | 10 min |
| [MIGRATION_GUIDE.md](MIGRATION_GUIDE.md) | Migrate from MySQL to PostgreSQL | Existing projects | 15 min |

### 🏗️ Architecture & Design

| Document | Purpose | Audience | Time |
|----------|---------|----------|------|
| [ARCHITECTURE.md](ARCHITECTURE.md) | Visual architecture diagrams | Understanding system | 10 min |
| [README.md](README.md) | Original local MySQL documentation | Local setup | 15 min |

### 🗄️ Database

| File | Purpose | Database |
|------|---------|----------|
| [supabase_setup.sql](supabase_setup.sql) | PostgreSQL schema for Supabase | PostgreSQL |
| [database_setup.sql](database_setup.sql) | MySQL schema (legacy) | MySQL |

### 🛠️ Build & Run

| File | Purpose | Platform |
|------|---------|----------|
| [pom.xml](pom.xml) | Maven build configuration | All |
| [Dockerfile](Dockerfile) | Docker deployment | Docker |
| [railway.toml](railway.toml) | Railway configuration | Railway |
| [.gitignore](.gitignore) | Git ignore rules | Git |

### 🎮 Scripts

| File | Purpose | Platform |
|------|---------|----------|
| `run_server_cloud.bat` | Run server with Supabase | Windows |
| `run_server_cloud.sh` | Run server with Supabase | Linux/Mac |
| `compile.bat` | Compile project | Windows (legacy) |
| `run_server.bat` | Run local server | Windows (legacy) |
| `run_client.bat` | Run client | Windows (legacy) |

---

## 🗺️ Reading Path by Goal

### Goal 1: Deploy to Cloud (Recommended)

```
1. README_CLOUD.md ─────────▶ Overview & features
2. CLOUD_DEPLOYMENT.md ─────▶ Step-by-step deployment
3. ENV_SETUP.md ────────────▶ Configure environment
4. ARCHITECTURE.md ─────────▶ Understand the system
```

**Time**: ~1 hour to deploy and understand

### Goal 2: Run Locally with Cloud Database

```
1. PROJECT_SUMMARY.md ──────▶ Choose "Path B"
2. ENV_SETUP.md ────────────▶ Set up environment variables
3. run_server_cloud.bat ────▶ Run server
```

**Time**: ~20 minutes

### Goal 3: Keep Original Local Setup

```
1. README.md ───────────────▶ Original documentation
2. QUICKSTART.md ───────────▶ Fast setup guide
3. database_setup.sql ──────▶ MySQL setup
```

**Time**: ~15 minutes

### Goal 4: Migrate Existing Project

```
1. MIGRATION_GUIDE.md ──────▶ Migration steps
2. ENV_SETUP.md ────────────▶ New configuration
3. CLOUD_DEPLOYMENT.md ─────▶ Deploy to Railway
```

**Time**: ~30 minutes

### Goal 5: Understand Architecture

```
1. ARCHITECTURE.md ─────────▶ Visual diagrams
2. README_CLOUD.md ─────────▶ Feature overview
3. Source code ─────────────▶ Implementation
```

**Time**: ~30 minutes

---

## 📂 Source Code Structure

```
src/com/rental/
│
├── 📁 model/           # Data models (POJOs)
│   ├── User.java       # User entity
│   ├── Vehicle.java    # Vehicle entity
│   └── Booking.java    # Booking entity
│
├── 📁 dao/             # Data Access Layer
│   ├── UserDAO.java           # User DAO interface
│   ├── UserDAOImpl.java       # User DAO implementation
│   ├── VehicleDAO.java        # Vehicle DAO interface
│   └── VehicleDAOImpl.java    # Vehicle DAO implementation
│
├── 📁 util/            # Utilities
│   └── DBUtil.java     # Database connection manager
│
├── 📁 server/          # Server components
│   ├── RentalServer.java      # TCP server main class
│   └── ClientHandler.java     # Per-client request handler
│
└── 📁 client/          # Client application
    └── RentalClient.java      # Console-based client
```

**Start reading from**: `RentalClient.java` (simplest) or `RentalServer.java` (main entry)

---

## 🎓 Learning Path

### Week 1: Understanding the Basics
- [ ] Read README_CLOUD.md
- [ ] Study ARCHITECTURE.md diagrams
- [ ] Run locally with Supabase (Hybrid mode)
- [ ] Test with demo accounts

### Week 2: Cloud Deployment
- [ ] Follow CLOUD_DEPLOYMENT.md
- [ ] Set up Supabase account
- [ ] Deploy to Railway
- [ ] Test remote access

### Week 3: Enhancements
- [ ] Add booking functionality
- [ ] Implement admin features
- [ ] Add password hashing
- [ ] Build GUI (optional)

---

## 🔍 Quick Reference

### Demo Accounts
```
Email: joyce@demo.com
Password: password
Role: CUSTOMER

Email: admin@rental.com
Password: admin123
Role: ADMIN
```

### Important URLs
- **Supabase**: https://supabase.com
- **Railway**: https://railway.app
- **PostgreSQL JDBC**: https://jdbc.postgresql.org
- **Maven Central**: https://search.maven.org

### Key Concepts
- **JDBC**: Java Database Connectivity
- **DAO**: Data Access Object pattern
- **TCP Socket**: Network communication protocol
- **Environment Variables**: Configuration management
- **Multi-threading**: Concurrent client handling

---

## 🆘 Troubleshooting

### Can't find what you need?

| Problem | Solution |
|---------|----------|
| "How do I deploy?" | → [CLOUD_DEPLOYMENT.md](CLOUD_DEPLOYMENT.md) |
| "Environment variable errors" | → [ENV_SETUP.md](ENV_SETUP.md) |
| "Migrating from MySQL" | → [MIGRATION_GUIDE.md](MIGRATION_GUIDE.md) |
| "Understanding the code" | → [ARCHITECTURE.md](ARCHITECTURE.md) |
| "Local setup" | → [README.md](README.md) |

### Still stuck?
1. Check the troubleshooting section in relevant guide
2. Review PROJECT_SUMMARY.md for overview
3. Verify all prerequisites are installed

---

## 📊 Documentation Status

| Document | Status | Last Updated | Version |
|----------|--------|--------------|---------|
| README_CLOUD.md | ✅ Complete | 2025-11-13 | 1.0 |
| CLOUD_DEPLOYMENT.md | ✅ Complete | 2025-11-13 | 1.0 |
| ENV_SETUP.md | ✅ Complete | 2025-11-13 | 1.0 |
| MIGRATION_GUIDE.md | ✅ Complete | 2025-11-13 | 1.0 |
| ARCHITECTURE.md | ✅ Complete | 2025-11-13 | 1.0 |
| PROJECT_SUMMARY.md | ✅ Complete | 2025-11-13 | 1.0 |
| README.md | ✅ Complete | 2025-11-13 | 1.0 |
| QUICKSTART.md | ✅ Complete | 2025-11-13 | 1.0 |

---

## 🎯 Next Steps

After reading documentation:

1. **Choose deployment option** (Cloud vs Local)
2. **Follow relevant guide** (See Reading Path above)
3. **Test the system** (Use demo accounts)
4. **Extend features** (Add booking, admin panel, etc.)

---

## 📝 Contributing to Docs

If you find issues or want to improve documentation:

1. Document should be clear and concise
2. Include examples and code snippets
3. Add troubleshooting sections
4. Keep table of contents updated
5. Use consistent formatting

---

**Happy Learning! 🚀**

Questions? Start with [PROJECT_SUMMARY.md](PROJECT_SUMMARY.md) for a quick overview.
