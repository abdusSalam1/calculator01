# Assignment

Spring Boot application that integrates with a third-party currency
exchange API to retrieve real-time exchange rates. The application should calculate the total
payable amount for a bill in a specified currency after applying applicable discounts. The
application should expose an API endpoint that allows users to submit a bill in one currency
and get the payable amount in another currency.

## Prerequisites

Ensure you have the following software installed:

- **Java 17**: Required to run the project.
- **Maven**: For dependency management and building the project.
- **Redis**: For caching.
- **IntelliJ IDEA**: Recommended IDE for Java development.

## Installation Guide

### Step 1: Install Redis

1. **Install Redis**:
    - On macOS: `brew install redis`
    - On Ubuntu: `sudo apt update && sudo apt install redis-server`
    - On Windows: You can download the installer from [Redis for Windows](https://github.com/microsoftarchive/redis/releases).

2. **Start Redis**:
    - macOS/Ubuntu: Run `redis-server` in your terminal.
    - Windows: Open Redis through the installed program or by running `redis-server.exe` from the Redis installation folder.

3. **Verify Redis Installation**: Run `redis-cli ping`. You should get a response `PONG`.

### Step 2: Install Java 17

1. **Download Java 17**: You can download the JDK from [Oracle JDK](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) or [AdoptOpenJDK](https://adoptopenjdk.net/).
2. **Verify Installation**: After installation, check by running `java -version` in the terminal to ensure Java 17 is installed.

### Running the Application
1. Start Redis: Ensure Redis is running by starting redis-server in a separate terminal window.
2. Start IntelliJ IDEA: Open IntelliJ IDEA and load the project.
3. In IntelliJ, locate the CalculatorApplication main class.
Right-click on CalculatorApplication and select "Run".