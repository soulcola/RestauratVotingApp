## [Deployed Instance - link to swagger](http://votingapp.petrtitov.com/swagger-ui/index.html)
##  Technical requirement:
Design and implement a REST API using Hibernate/Spring/SpringMVC (Spring-Boot preferred!) **without frontend**.

The task is:

Build a voting system for deciding where to have lunch.

* 2 types of users: admin and regular users
* Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
* Menu changes each day (admins do the updates)
* Users can vote for a restaurant they want to have lunch at today
* Only one vote counted per user
* If user votes again the same day:
    - If it is before 11:00 we assume that he changed his mind.
    - If it is after 11:00 then it is too late, vote can't be changed

Each restaurant provides a new menu each day.

As a result, provide a link to github repository. It should contain the code, README.md with API documentation and couple curl commands to test it (**better - link to Swagger**).

-----------------------------
P.S.: Make sure everything works with latest version that is on github :)  
P.P.S.: Assume that your API will be used by a frontend developer to build frontend on top of that.

-------------------------------------------------------------
- Stack: [JDK 17](http://jdk.java.net/17/), Spring Boot 3.x, Lombok, H2, Caffeine Cache, SpringDoc OpenApi 2.x 
- Run: `mvn spring-boot:run` in root directory.
-----------------------------------------------------

Swagger UI link
[REST API documentation](http://localhost:8080/)  
Test credentials:
```
User:  user@yandex.com / password
Admin: admin@gmail.com / admin
Guest: guest@gmail.com / guest
```

