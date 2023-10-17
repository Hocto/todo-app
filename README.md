To Do App

1. Create a Maven based Spring Boot web application.
2. Use Java, JDK 8 and above is suitable.
3. Use PostgreSQL as the database. Database schema can be recreated every
   time by Hibernate once the application starts.
4. Use Hibernate JPA for ORM/persistence.
5. Use Spring Data JPA for Repository classes.
6. Implement a layered architecture: Controller &gt; Service &gt; Repository
7. Use Spring MVC for the Controller layer. Validate the data that is submitted
   (POST&#39;ed) from the client side to the Controller.
8. Implement the client side using either the classical method Tymeleaf, or by
   creating a single page React application. Use Bootstrap for HTML/CSS.
9. The application must be a simple TODO management system. Functions
   must include:
   o Creating a TODO
   o Deleting a Todo
   o Viewing a TODO
   o Marking a TODO as done
   o Listing existing TODOs

Steps to initialize server and database:

- ./mvnw clean install -DskipTests
- docker image build -t todo-app .
- docker-compose up -d

Steps to initialize UI:

- 'cd to-do-ui/ && npm install && npm start'

Application server will be ready on 8080 port.
UI will be ready on 3000 port.

You can see the API doc here(http://localhost:8080/swagger-ui/)