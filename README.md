# My Movies Lists — Full-Stack Movie Review List Manager

## Introduction / Summary

**My Movies Lists** is a full-stack web application built with **React, Vite, and JavaScript** on the frontend and **Java Spring Boot** on the backend, using **MySQL** as the database. Motivated by my passion for movies and reviewing, this app enables users to explore a vast catalog of movies, view detailed information, rate films, and organize their ratings into personalized custom lists.

## Demo

[Click here to watch the demo](https://youtu.be/9AYR0rqFDvc)  
*(A short video showcasing the app’s core features and usage)*

## Tech Stack

| Layer      | Technologies |
|------------|--------------|
| **Frontend**  | React, Vite, Material UI |
| **Backend**   | Java, Spring Boot, Spring Security (JWT) |
| **Database**  | MySQL (via MySQL Workbench) |
| **Tools**     | IntelliJ IDEA, VSCode |
| **Build**     | Maven (backend), npm (frontend) |

## Core Features

- User registration & login with secure JWT authentication  
- Movie search & detailed view powered by the OMDb API  
- Stateless authentication securing all API requests with JWT via Spring Security
- Responsive UI styled with MUI  
- RESTful API for managing movie reviews & custom lists  

## Installation Instructions

### Prerequisites

- Java 17+  
- Maven  
- Node.js 16+  
- npm  
- MySQL Server running

### 1. Download & Open the Project

```
1. Download the ZIP file: My-Movies-Lists-main.zip
2. Unzip the file
3. Open the unzipped folder in IntelliJ IDEA or VSCode
```

### 2. Set Up the Database

```
1. Open MySQL Workbench (or other client)
2. Run: Movie Project/db/mymovieslists.sql
3. This creates and populates the required tables
```

### 3. Start the Frontend

```
cd "Movie Project/frontend"
npm install
npm run dev
```
Frontend will run at: [http://localhost:5173](http://localhost:5173)

### 4. Configure & Run the Backend

```
1. Open: Movie Project/mymovielist/src/main/resources/application.properties
2. Set `spring.datasource.username` and `spring.datasource.password`
3. Then run:
   mvn clean install
   mvn spring-boot:run
```

### 5. Access the App

Go to [http://localhost:5173](http://localhost:5173) once both frontend and backend are running.

## Challenges & Learnings

- Gained hands-on experience designing databases using ER diagrams and relational schemas 
- Developed RESTful APIs with Spring Boot and integrated them with a React frontend
- Implemented JWT authentication using Spring Security for secure requests
- Connected the frontend, backend, and database into a seamless full-stack workflow

## Future Improvements

- Share movie lists and reviews via email or SMS  
- Add social features for friends and public reviews  
- Deploy frontend to Vercel, backend to cloud database  
- Make the app mobile-responsive

## Contact

- Email: [Andrew866799@gmail.com](mailto:Andrew866799@gmail.com)  
- LinkedIn: [linkedin.com/in/andrewkgee](https://www.linkedin.com/in/andrewkgee)
