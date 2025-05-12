# Read Me First
This application implements a store management tool that exposes endpoints for managing products.

# Getting Started
### Prerequisites
This project is a Java 21, Maven based project that uses SpringBoot for the web part.  
It uses Docker to create a Postgres instance.  
It includes a Postman collection for testing the exposed endpoints.

Good to have: pgAdmin or other management tool to see the data from Postgres.

### Setup
Ensure you're in the directory with the docker-compose.yaml file, then run `docker-compose up` and launch the Postgres container.  
Import in Postman the collection and the environment found in `resources/postman`.  
Run the application.  

At startup, Liquibase will populate the database with some mocked products as specified in `resources/db` files.  
Liquibase will also create 3 users with different wrights:
- ADMIN - full rights (create, update and get products)
- MANAGER - rights to update and get products
- USER (at the current status of the application) - same as non authenticated requests, can just get products

### Functionalities
This application exposes the following endpoints:
- Get All Products paginated (all users)
  - Returns all products according to page size and number. Default page size = 4, default page number = 0
- Get Product by SKU id (all users)
  - Returns the product matching the SKU number. If no product is found, an exception is thrown.
- Update price (manager and admin)
  - Updates the price of a product matching the id. If no product is found, an exception is thrown.
- Update quantity (manager and admin)
  - Updates the quantity of a product matching the id. If no product is found, an exception is thrown.
- Create new product (admin)
  - Creates a new product. If the product that we try to create has a SKU that is already in use, an exception is thrown.

### Technologies used
- Java 21, Maven, SpringBoot (with web, security, logging, data - hibernate, aop, caching, validation, actuator, test), Postgres, Liquibase, Lombok, Mapstruct, Docker, Git.

### Further improvements
1. Logging - now we implement traceId with an AOP around the controller methods, which populates the traceId when the request is made with the correct auth. If an unauthenticated or unauthorized user makes a request, the traceId will not be populated.
2. Take into consideration adding an index in our products table.
3. Nice to have - implement swagger.
4. Expose metrics (maybe with popular Grafana and Prometheus - but I don't have experience yet with these tools).
5. Do some sort of performance testing on frequently used endpoints, most likely get product by sku and get all product paginated (I used JMeter and Blazemeter to test high volumes or requests)
6. Replace product id value from a number that increments to a UUID.