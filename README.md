# GitHub API Repository Fetcher

A Spring Boot 3.5 application that fetches all **non-fork** repositories for a given GitHub user along with their branches and the latest commit SHA.

## Features
- Fetches repositories for a given GitHub username.
- Filters out forked repositories.
- Fetches all branches for each repository with the latest commit SHA.
- Returns data in JSON format.
- Includes an **integration test** (happy path).

## Technologies
- **Java 21**
- **Spring Boot 3.5**
- **Spring Web**
- **RestTemplate** for consuming external APIs
- **JUnit 5** + **AssertJ** for testing
- **Maven** for dependency management

## Running the Application
 - Clone the repository
 - Build and run with mvn (mvn spring-boot:run)
   
## Running Tests
 -The project contains one integration test (GitApiIntegrationTests) it verifies that the API correctly fetches and returns repositories with branches for a valid GitHub user.
