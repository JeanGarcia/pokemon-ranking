# Pokemon Ranking Service

Pokémon Ranking Service provides a RESTful API to access a ranking of Pokémon based on their stats.
The service retrieves Pokémon data from the PokeAPI and ranks them based on their stats.

You can see a **LIVE** version of this service at (insert swagger link here).

# Dependencies

- Java 21 
- Spring Boot 3.5.3

# Setup 

1. Clone the repository-
2. Use the Gradle task `./gradlew bootRun` to build and run the project.

# Testing

1. Use the Gradle task `./gradlew test` to run the test suit and generate the jacoco report at `build/reports/jacoco/test/html/index.html`.

# Justification 



# TODO

- [X] Implement Mappers
- [ ] Abstract configuration properties
- [ ] Implement Unit Tests
- [ ] Implement Integration Tests
- [ ] Update documentation

# Improvements 

Clean up, centralize configuration variables in the application properties file, and externalize those as environment variables.

Update the application service and rest controller to use webflux async instead of blocking calls.

Create integration tests for the 
