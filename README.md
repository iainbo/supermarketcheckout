# Springboot and Thymeleaf Supermarket checkout


# About
This application was made using Springboot and Thymeleaf.
It utilizes an H2 in memory database as well as Spring data JPA and Spring Rest.


# How to run the application
The application uses maven to build, test and run.

On the command line, go to the root of the folder containing the application and enter:
```
mvn spring-boot:run
```

You can also enter:
```
mvn clean package
```
to run the unit tests and create an executable jar file.

Once the application has started, it can be accessed by navigating to:
`http://localhost:8080/supermarket`

The application displays to you a list of items in stock and allows you to specify a quantity to add to the basket.

When you click Submit, the application calculates the total of the basket and then applies any applicable offers.

You are then presented with receipt which tells you how may items are in the basket, what the total cost is, what the cost is once offers have been applied is and the savings you have made.
