Smart Movie Ticket Booking System with MAYA Chatbot
The Smart Movie Ticket Booking System is a full-stack movie ticket booking application that leverages a Python-based chatbot (called MAYA) to identify user intents and provide personalized suggestions. The Java-based backend manages movie-related data including movie details, show timings, theaters, ratings, users, and admin functionalities.

Table of Contents
Getting Started
Frontend (React)
Backend (Java)
Backend (Python - MAYA Chatbot)
Features
Technologies Used
Testing
Contributing
License
Getting Started
Follow the instructions below to get the project running locally:

Prerequisites
Ensure you have the following installed on your machine:

Node.js: For running the React frontend
Java 11+: For running the Java backend
Python 3.8+: For running the Python-based MAYA chatbot
MySQL/MongoDB: For database management (depending on your setup)
Docker (optional): For containerized setup
Kafka/RabbitMQ (optional): If integrated for message passing
Installation
Clone the repository:

bash

git clone https://github.com/pavankalyan6m/SmartMovieTicketBookingApplication.git
Navigate into the project folder:

bash

cd SmartMovieTicketBookingSystem
Install dependencies for each part of the project.

For Frontend (React):
Navigate to the React frontend folder:

bash

cd React_Frontend
Install Node.js dependencies:

bash

npm install
Start the React frontend:

bash

npm start
For Backend (Java - Spring Boot):
Navigate to the Java backend folder:

bash

cd Java_Backend
Set up the database (ensure MySQL is running):

Create a database for the application (e.g., movie_ticket_booking_system).
Update the application.properties or application.yml with your database credentials.
Build and run the Spring Boot application:

bash

./mvnw spring-boot:run
For Backend (Python - MAYA Chatbot):
Navigate to the Python backend folder:

bash

cd Python_Backend
Set up a virtual environment (optional):

bash

python -m venv venv
source venv/bin/activate  # On Windows: venv\Scripts\activate
Install Python dependencies:

bash

pip install -r requirements.txt
Run the Python chatbot:

bash

python app.py  # or whichever script is the entry point
Frontend (React)
Overview
The React frontend provides an interactive interface for users to search movies, book tickets, and interact with the MAYA chatbot. It communicates with the Java backend for movie data and uses the Python-based chatbot to handle user queries.

Features
User-friendly interface for movie browsing and booking
Integration with MAYA chatbot to provide suggestions based on user input
User authentication and session management
Setup Instructions
Follow the instructions in the Getting Started section to install and run the frontend locally.

Backend (Java - Spring Boot)
Overview
The Java backend serves as the central part of the system, handling movie data, managing show timings, theater locations, ratings, and user/admin details. It interacts with the frontend via REST APIs and also communicates with the Python-based chatbot to understand user preferences.

Features
User Management: Admin and user roles, user authentication using JWT tokens.
Movie Management: CRUD operations for storing and retrieving movie details, show timings, and ratings.
Show Timings: Schedule for movie screenings, along with theater location details.
Theater Locations: Storing and retrieving theater data including addresses, timings, and available seats.
Setup Instructions
Follow the instructions in the Getting Started section to install and run the Java backend locally.

Key Technologies:
Spring Boot: Framework for building RESTful APIs.
Spring Data JPA: For database interactions.
Spring Security: For managing authentication and authorization.
JWT: For token-based authentication.
Backend (Python - MAYA Chatbot)
Overview
MAYA is the Python-based chatbot that interacts with users, identifies their intents, and suggests movies based on their preferences. MAYA processes user input and returns relevant movie suggestions or prompts to guide the user through the booking process.

Features
Intent Recognition: MAYA uses natural language processing (NLP) to identify user intents such as "book tickets", "suggest movies", and more.
Movie Suggestions: Based on user preferences, MAYA suggests movies available for booking.
Movie Data Processing: Fetches movie details such as titles, genres, ratings, and show timings.
Setup Instructions
Follow the instructions in the Getting Started section to install and run the Python backend locally.

Key Technologies:
Flask/Django: Web framework for building APIs.
NLTK or spaCy: For natural language processing (NLP) to understand and classify user intents.
TensorFlow (optional): If using machine learning for more advanced intent recognition.
Features
MAYA Chatbot
Intent Recognition: Identifies user intents like "show me movies", "book a ticket", and "suggest a movie based on genre".
Personalized Suggestions: Provides suggestions based on user input, e.g., "I want to watch a horror movie" or "Find me movies playing at 7 PM".
Real-time Interactions: Chatbot updates dynamically as the user provides input and interacts with the movie listings.
Movie Data Management
CRUD Operations: Admins can add, update, or delete movie details, show timings, and theater locations.
Movie Booking: Users can view available movie showtimes and book tickets accordingly.
User and Admin Roles: Admins have full control over the movie database, while users can only browse movies and book tickets.
Technologies Used
Frontend:

React, Axios, React Router
HTML5, CSS3, JavaScript
Backend:

Java: Spring Boot, Spring Security, JWT, Spring Data JPA
Python: Flask/Django, NLTK/spaCy, TensorFlow (if applicable)
Database:

MySQL/MongoDB (depending on your implementation)
Messaging Queue: Kafka/RabbitMQ (optional)

Version Control: Git, GitHub

Containerization: Docker (optional)

CI/CD: Jenkins, GitHub Actions

Testing
Frontend Testing:
React components are tested using Jest and React Testing Library.

Backend Testing:
Java: Use JUnit for unit testing Spring Boot controllers and services.
Python: Use unittest or pytest for backend testing.
Run the tests:

For Java:
bash

./mvnw test
For Python:
bash

python -m unittest test_filename.py  # or use pytest
Contributing
Feel free to fork this repository, make changes, and submit a pull request! To contribute:

Fork the repository
Create a new branch (git checkout -b feature-branch)
Make your changes and commit them (git commit -am 'Add new feature')
Push to your fork (git push origin feature-branch)
Submit a pull request
License
This project is licensed under the MIT License - see the LICENSE file for details.
