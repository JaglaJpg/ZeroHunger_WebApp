ZeroHunger WebApp

A full-stack web application designed to streamline food, appliance, and clothing donations through an anonymous, geolocation-based platform. Built using a React frontend and Spring Boot backend, ZeroHunger connects donors with nearby distribution centers such as food banks and soup kitchens, offering a clean and intuitive user experience.

""Live Stack""

Frontend: React + Vite + Tailwind CSS

Backend: Spring Boot (Java 21)

Database: H2 (development)

Security: JWT-based authentication

Data Handling: REST APIs, JSON

🔧 Setup Instructions

Backend

cd back-end/zerohunger
mvn install
mvn spring-boot:run

Frontend

cd front-end/donation-tracker
npm install
npm run dev

📦 Project Structure

zerohunger/
├── back-end/zerohunger          # Spring Boot Backend
│   ├── src/
│   ├── pom.xml
│   └── ...
├── front-end/donation-tracker  # React Frontend
│   ├── src/
│   ├── vite.config.js
│   └── package.json
└── .gitignore

🌟 Features

🔐 Authentication

JWT-based login and registration.

Address geocoding stored on signup for distance-based logic.

📍 Donation Listings

Donors can post listings for food, clothing, and appliances.

Listings are paired with the 10 nearest charities from the database based on the donor's location.

🚚 Donation Tracker

Donors can update status (e.g. ready, in transit, delivered).

Recipients can view status in real time.

📊 Dashboard & Analytics

View how much you've donated (quantity, type).

Log personal food and track waste vs. saved items.

📁 My Fridge

Feature for users to log their food manually to help build personalized waste statistics.

🤖 Tech Details

Backend

Spring Boot with JPA for ORM.

H2 in-memory DB for development.

Controllers extract userId from JWT on every request to tailor results.

Modular structure with clear service and repository separation.

Frontend

Vite-powered React app with Tailwind UI.

Uses react-router-dom for routing.

fetch with credentials: include for session management.

Charts rendered using recharts.

📌 Contributions

This project was built as part of a university group project. While collaboration was intended, I took ownership of the majority of the backend, including:

All backend integration

JWT Authentication system

Geolocation-based logic

Donation tracking and dashboard APIs

Food waste tracker (My Fridge component)

Frontend routing structure and some UI polishing

I also handled much of the deployment-ready structuring and cleanups for group-wide integration.

🔭 Future Improvements

Allow users to change address after registration.

Fix image upload functionality on listings.

Add donation history per user.

Improve responsive design and component polish.

E2E testing suite and backend validation enhancements.

🛠️ Tooling

Version control via Git

Deployed with GitHub Pages (frontend only preview, backend local/dev only)

ESLint config is present but not actively used

📁 .gitignore Excerpts

# === React/Vite Frontend ===
node_modules/
dist/
.vite/
.env
.env.*.local

# === Spring Boot Backend ===
/target/
/bin/
/build/
*.iml
*.classpath
*.project
.settings/
*.log
*.tmp
*.swp

# === IDE-specific ===
.vscode/
*.launch

🧠 Author

Wiktor Jagla
GitHub Portfolio
📫 wiktorjagla@gmail.com
