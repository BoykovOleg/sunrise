Application Architecture
========================

Application has three layers:  
domain - contains domain models(entities, value objects) and repositories.  
application - contains application services, DTOs, result objects(dto).  
presentation(ui) - contains controllers and controller advice.  

Domain layer works only with spring infrastructure(annotation, repository implementations).
Domain layer cannot use application or presentation components(classes).  
Application layer can use domain components(classes), but cannot use presentation components.  
Presentation works only with the application layer and the main entry point is dev.sunrise.application.Application class.  

Presentation interacts with dev.sunrise.application.Application only with DTOs and spring classes(Pageable).  
The validation is on the application layer. It is part of the application, not presentation(but presentation layer can have validation too).

Application layer has the event_time package for providing events time info and the sunrise_api package for requesting external api - https://sunrise-sunset.org/api.

The article about layered architecture in spring - https://www.petrikainulainen.net/software-development/design/understanding-spring-web-application-architecture-the-classic-way/  
The names of packages can be renamed to more traditional - services, repositories, entities, etc.

Environment and Deploying
=========================
The application requires mysql(version 8) and java 8. Mysql database name and credentials are in the .env file and can be exported as env variables with:
```
source .env
```

Mysql can be started with docker and docker-compose:
```
docker-compose up -d
```
**Run tests:**
```
./mvnw test
```
**Run application:**
```
./mvnw spring-boot:run
```
Add some Ukraine cities with coordinates:
```
bash seed.txt
```
Endpoints:
---------
```
GET http://localhost:8080/cities

GET http://localhost:8080/cities?page=1&perPage=5

POST http://localhost:8080/cities 
with json payload:
{
    "name": "Alchevs’k",
    "latitude":48.46893,
    "longitude":38.81669
}

GET http://localhost:8080/event_time?cities=Cherkasy,Chernihiv&type=sunrise_sunset
where type can be sunrise, sunset or sunrise_sunset.
