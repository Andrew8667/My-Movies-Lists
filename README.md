My Movies Lists — Full-Stack Movie Review List Manager Web Application

Introduction / Summary
-My Movies Lists is a full-stack web application built with React, Vite, and JavaScript on the frontend, and Spring Boot with Java on the backend, using MySQL as the database. Motivated by my passion for movies and reviewing, this app enables users to explore a vast catalog of movies, view detailed information, rate films, and organize their ratings into personalized custom lists.

Demo
-Click here to view a short demonstration of the app’s core features and how it works: https://youtu.be/9AYR0rqFDvc

Tech Stack
-Frontend: React, Vite, Material UI (with MUI’s styling system)
-Backend: Java, Spring Boot (REST APIs), Spring Security (JWT-based authentication)
-Database: MySQL (managed via MySQL Workbench)
-Development Tools: IntelliJ IDEA, VSCode
-Build Tools: Maven (backend), npm (frontend)

Core Features
-User registration and login
-Movie search and detailed views powered by the OMDb API
-Stateless authentication securing all API requests with JWT via Spring Security
-Responsive and visually appealing UI optimized for desktop
-REST APIs for managing user reviews and custom movie lists

Installation Instructions
-Prerequisites: Java 17+, Maven, Node.js 16+, npm, MySQL Server running
-Download and Open the Project
  1. Download the project as a ZIP file (My-Movies-Lists-main.zip).
  2. Unzip the downloaded file.
  3. Open the unzipped folder My-Movies-Lists-main in your preferred IDE (e.g., IntelliJ IDEA, VSCode).
-Set Up the Database
  1. Open MySQL Workbench (or your MySQL client).
  2. Run the SQL script located at: Movie Project/db/mymovieslists.sql
  3. This will create and populate the necessary database and tables for the application.
-Start the Frontend
  1. Open a terminal and navigate to the frontend directory Movie Project/frontend
  2. Install the required Node.js packages: Npm install
  3. Start the development server: Npm run dev
  4. The frontend will be available at: http://localhost:5173/
-Configure and run the backend
  1. Open Movie Project/mymovielist/src/main/resources/application.properties and update spring.datasource.username and spring.datasource.pasword with your MySQL credentials
  2. Run Spring Boot application in Movie Project/mymovielist: mvn clean install, mvn spring-boot:run
-Access the Application
  1. Once both frontend and backend are running, open your browser and go to: http://localhost:5173/

Challenges & Learnings
- Gained hands-on experience designing databases using ER diagrams and relational schemas
- Developed RESTful APIs with Spring Boot and integrated them with a React frontend
- Implemented JWT authentication using Spring Security for secure, stateless user sessions
- Connected the frontend, backend, and database into a seamless full-stack workflow

Future Improvements
- Enable sharing of movie lists and reviews via email or SMS
- Add social features to connect with friends and view their movie reviews
- Deploy the frontend to Vercel and migrate the backend database to a cloud-hosted solution
- Develop a mobile-friendly version of the application for better accessibility

Contact Info
- Email: Andrew866799@gmail.com
- LinkedIn: www.linkedin.com/in/andrewkgee
