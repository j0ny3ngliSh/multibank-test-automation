# Multibank Trading Automation Framework

A production-grade UI automation framework built with Java, Playwright, and JUnit 5. This framework is designed for scalability, failure diagnostics, and environment flexibility.

## Key Features
* **Thread-Safe Architecture:** Uses `ThreadLocal` for parallel test execution.
* **Failure Diagnostics:** Integrated Playwright Tracing—auto-generates "flight recorder" files (`.zip`) on test failure for easy debugging.
* **Session Persistence:** Supports `storageState` injection to bypass OTP/CAPTCHA workflows.
* **Config-Driven:** Easily switch environments, browsers, and headless modes via `config.properties` without recompiling.
* **Page Object Model (POM):** Decouples element locators from business logic for maintainable, readable tests.

## Project Structure
- `src/main/java/com/multibank/pages/` - Page Object Model classes.
- `src/test/java/com/multibank/tests/` - Test suites and `BaseTest` infrastructure.
- `src/test/java/com/multibank/utils/` - Config readers, session generators, and helpers.
- `src/test/resources/config.properties` - Environment configuration.

## Prerequisites
- Java 17+
- Maven 3.8+

## Setup & Configuration
Create your `src/test/resources/config.properties`:
```properties
browser=chromium
headless=true
baseUrl=[https://trade.multibank.io/](https://trade.multibank.io/)

```

*Note: Ensure `auth.json` is generated using the `SessionGenerator` utility if targeting secured environments.*

## How to run

Run all tests:

```bash
mvn test

```

## Failure Diagnostics

Test traces are saved to `/target/traces/`. To view them:

1. Open [Playwright Trace Viewer](https://trace.playwright.dev/).
2. Drag and drop the generated `.zip` file from your `target/traces/` folder.

## Allure Reports

Generate and serve reports:

```bash
mvn allure:serve

```