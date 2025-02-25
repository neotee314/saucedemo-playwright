"# saucedemo-playwright"

# Saucedemo Playwright Tests

This project is a test automation framework for the [SauceDemo](https://www.saucedemo.com/) application, using [Playwright](https://playwright.dev/java/) and [JUnit 5](https://junit.org/junit5/).

## Features

- Automated UI tests using Playwright
- JUnit 5 for test management
- Allure reporting for test results
- CI/CD integration with GitHub Actions

## Project Structure

```
 saucedemo-playwright/
   ├── src/
   │   ├── main/java/org/example/pages/  # Page Object Model (POM)
   │   ├── test/java/org/example/tests/  # Test cases
   │   ├── test/resources/               # Test resources
<<<<<<< HEAD
   ├── target/                           # Build output
=======
>>>>>>> d34343051e4e2d950a5b325b9405ae0fa66730d4
   ├── pom.xml                           # Maven configuration
   ├── .github/workflows/ci.yml          # GitHub Actions workflow
   ├── README.md                         # Project documentation
```

## Prerequisites

Before running the tests, ensure you have the following installed:

- Java 22 (or later)
- Maven
- Node.js (for Playwright dependencies)

## Setup Instructions

1. Clone the repository:
   ```sh
   git clone https://github.com/YOUR_USERNAME/saucedemo-playwright.git
   cd saucedemo-playwright
   ```
2. Install Playwright browsers:
   ```sh
   mvn playwright:install
   ```
3. Run tests locally:
   ```sh
   mvn test
   ```
4. Generate Allure reports:
   ```sh
   mvn allure:report
   ```
## Author

[Abolfazl Heidari](https://github.com/neotee314)
