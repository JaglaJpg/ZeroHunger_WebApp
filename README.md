# ZeroHunger WebApp

A full-stack web application designed to simplify the process of donating food, appliances, and clothing through an anonymous, geolocation-based platform. Built with a React frontend and Spring Boot backend, ZeroHunger connects donors with nearby distribution centres such as food banks and soup kitchens, offering an intuitive and streamlined experience.

## Live Stack

**Frontend:** React, Vite, Tailwind CSS  
**Backend:** Spring Boot (Java 21)  
**Database:** H2 (development)  
**Security:** JWT authentication  
**Data Handling:** REST APIs, JSON

## Setup Instructions

### Backend
cd back-end/zerohunger  
mvn install  
mvn spring-boot:run  

### Frontend
cd front-end/donation-tracker  
npm install  
npm run dev  

## Project Structure
zerohunger/  
├── back-end/zerohunger          # Spring Boot backend  
│   ├── src/  
│   ├── pom.xml  
│   └── ...  
├── front-end/donation-tracker   # React frontend  
│   ├── src/  
│   ├── vite.config.js  
│   └── package.json  
└── .gitignore  

## Features

### Authentication
- JWT-based login and registration.
- User address is geocoded at signup for distance-based charity recommendations.

### Donation Listings
- Donors can post food, clothing, and appliance listings.
- Each listing is matched with the 10 nearest charities based on the donor’s coordinates.

### Donation Tracker
- Donors can update listing status (ready, in transit, delivered).
- Recipients can check status in real time.

### Dashboard & Analytics
- Tracks total donations by quantity and type.
- Food logging and waste tracking features.

### My Fridge
- Users can manually log food items to build personalised waste-reduction statistics.

## Tech Details

### Backend
- Spring Boot with JPA/Hibernate.
- H2 in-memory database for development.
- Controllers obtain userId from JWT per request to return user-specific results.
- Clear modular separation between controllers, services, and repositories.

### Frontend
- Vite-powered React app with Tailwind.
- Routing handled via react-router-dom.
- fetch with `credentials: "include"` for authenticated sessions.
- Recharts used for dashboard visualisations.

## Contributions

This project was built as part of a university group assignment. I took responsibility for the majority of the backend, including:

- Full backend integration and service architecture  
- JWT authentication system  
- Geolocation and nearest-charity logic  
- Donation tracking and dashboard endpoints  
- Food-waste tracker (My Fridge)  
- Frontend routing structure  
- UI polish and integration fixes  
- Deployment-ready cleanup and project structuring

## Future Improvements
- Allow users to change their address post-registration.
- Fix image upload for listings.
- Add per-user donation history.
- Improve responsive design and UI consistency.
- Add E2E testing and stronger backend validation.

## Tooling
- Git for version control  
- GitHub Pages for frontend preview (backend runs locally)  
- ESLint configuration present but not enforced  

## .gitignore Excerpts
# React/Vite
node_modules/  
dist/  
.vite/  
.env  
.env.*.local  

# Spring Boot
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

# IDE
.vscode/  
*.launch  

## Author
**Wiktor Jagla**  
GitHub Portfolio  
wiktorjagla@gmail.com
