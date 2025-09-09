# Java for .NET Developers: A Comparative Guide

## Table of Contents
1. [Introduction](#1-introduction)
2. [Java Ecosystem Overview](#2-java-ecosystem-overview)
3. [Setting Up the Environment](#3-setting-up-the-environment)
4. [Gradle](#4-gradle)
5. [Spring Boot vs ASP.NET Core](#5-spring-boot-vs-aspnet-core)
6. [Key Differences Summary](#6-key-differences-summary)
7. [java_examples Demo Project](#7-java_examples-demo-project)

## 1. Introduction

This document is designed to help developers familiar with C# and .NET transition smoothly into the Java ecosystem 
by drawing parallels between the two technologies.

## 2. Java Ecosystem Overview

- **Java Development Kit (JDK)**: The core development kit for Java applications. Similar to .NET SDK.
- **Build Tools**: Gradle (modern, flexible) and Maven (older, XML-based). Comparable to MSBuild and NuGet.
- **Frameworks**: Spring Boot (web applications), Hibernate (ORM). Similar to ASP.NET Core and Entity Framework.
- **Dependency Management**: Handled by Gradle/Maven, Similar to NuGet in .NET.
- **IDEs**: IntelliJ IDEA, Comparable to Visual Studio and Rider.
- **Testing Frameworks**: JUnit, TestNG. Similar to xUnit, NUnit, MSTest.
- **Package Repositories**: Maven Central, JCenter. Similar to NuGet.org.
- **Documentation**: Javadoc, Swagger. Similar to XML comments, Swashbuckle.

## 3. Setting Up the Environment

### 3.1 Installing JDK
1. Download and install JDK 21 from [Microsoft](https://learn.microsoft.com/en-us/java/openjdk/download) or [Adoptium](https://adoptium.net/).
2. Set `JAVA_HOME` environment variable to point to the JDK installation directory.
3. Add `JAVA_HOME/bin` to your system `PATH`.
4. Verify installation:
   ```bash
   java -version
   javac -version
   ```
   
### 3.2 Installing Gradle
1. Download and unzip Gradle from [Gradle Releases](https://gradle.org/releases/).
2. Add `GRADLE_HOME/bin` to your system `PATH`.
3. Verify installation:
   ```bash
   gradle -v
   ```
   
### 3.3 IDE Setup
- **IntelliJ IDEA**: Download and install from [JetBrains](https://www.jetbrains.com/idea/download/).
- **VS Code**: Install Java extensions from the marketplace.

## 4. Gradle

### 4.1 What Is Gradle? (vs .NET Build System)

Gradle is Java's equivalent to **MSBuild** + **NuGet** + **dotnet CLI** combined into one tool. 
Gradle is a modern alternative to Maven (XML-based, like old .csproj).

### 4.2 How It Is Used (Developer Workflow vs .NET)

**Gradle Commands vs .NET CLI:**

| Task           | Gradle Command                     | .NET CLI Equivalent |
|----------------|------------------------------------|---------------------|
| **List Tasks** | `./gradlew tasks`                  | `dotnet --help`     |
| **Clean**      | `./gradlew clean`                  | `dotnet clean`      |
| **Build**      | `./gradlew build`                  | `dotnet build`      |
| **Run**        | `./gradlew bootRun`                | `dotnet run`        |
| **Test**       | `./gradlew test`                   | `dotnet test`       |
| **Package**    | `./gradlew bootJar`                | `dotnet publish`    |
| **Restore**    | `./gradlew --refresh-dependencies` | `dotnet restore`    |

### 4.3 Project Files Overview (vs .NET Solution Structure)

**File Mapping and Purpose:**

| Section Link                      | Gradle File             | .NET Equivalent                                           | Purpose                                                                              |
|-----------------------------------|-------------------------|-----------------------------------------------------------|--------------------------------------------------------------------------------------|
| [4.3.1](#431-settingsgradlekts)   | **settings.gradle.kts** | **.sln**                                                  | Defines projects in solution                                                         |
| [4.3.2](#432-buildgradlekts)      | **build.gradle.kts**    | **.csproj**                                               | Dependencies, build configuration, target framework                                  |
| [4.3.3](#433-gradleproperties)    | **gradle.properties**   | **Directory.Build.props** / **global.json** / **secrets** | Global build properties, consistent tooling versions, environment variables, secrets |
| [4.3.4](#434-gradlew--gradlewbat) | gradlew / gradlew.bat   | (dotnet CLI is global)                                    | Wrapper scripts to run Gradle without local install                                  |

#### 4.3.1 settings.gradle.kts

> Equivalent to Solution File (.sln)  

This file is normally auto-generated when you create a Gradle project.
It defines project structure and configures build settings.
It will be modified to include multiple modules (subprojects) similar to how a .sln file includes multiple .csproj files.

#### 4.3.2 build.gradle.kts

> Equivalent to Project File (.csproj)  

**Component Mapping:**

| Gradle Component                    | .NET Equivalent               | Purpose                 |
|-------------------------------------|-------------------------------|-------------------------|
| `plugins`                           | `Sdk="Microsoft.NET.Sdk.Web"` | Framework and tooling   |
| `repositories { mavenCentral() }`   | **NuGet.org** (implicit)      | Package source          |
| `dependencies { implementation() }` | `<PackageReference>`          | NuGet packages          |
| `java.toolchain`                    | `<TargetFramework>`           | Runtime version         |
| `application.mainClass`             | **Program.cs** entry point    | Application entry point |
| `tasks {}`                          | `<Target>`                    | Custom build tasks      |

**plugins:**

plugins are similar to the SDK attribute in .csproj files.
They define the type of project (e.g., Java application, Spring Boot app).
common plugins include:
- `id("org.springframework.boot") version "X.Y.Z"`: Spring Boot
- `id("io.spring.dependency-management") version "X.Y.Z"`: Dependency management
- `application`: For building runnable applications
- `java`: Basic Java support

**repositories:**

This section defines where to fetch dependencies from, similar to NuGet package sources.
`mavenCentral()` is the most common repository, similar to NuGet.org.

**dependencies:**

This section lists all project dependencies, similar to `<PackageReference>` in .csproj files.
Here are common configurations and their .NET equivalents:

| Gradle Configuration   | .NET Equivalent                              | Purpose                          | Usage                                     |
|------------------------|----------------------------------------------|----------------------------------|-------------------------------------------|
| `implementation()`     | `<PackageReference>`                         | Runtime and compile dependencies | Production code dependencies              |
| `compileOnly()`        | `<PackageReference PrivateAssets="All">`     | Compile-time only                | APIs, annotations not needed at runtime   |
| `runtimeOnly()`        | `<PackageReference ExcludeAssets="Compile">` | Runtime only dependencies        | Database drivers, logging implementations |
| `testImplementation()` | `<PackageReference>` in test project         | Test dependencies                | JUnit, testing frameworks                 |
| `developmentOnly()`    | `<PackageReference IncludeAssets="Build">`   | Development tools                | Hot reload, dev tools                     |

**java.toolchain:**

This section specifies the Java version to use, similar to `<TargetFramework>` in .csproj files.
It ensures consistent Java versions across different environments.

**application.mainClass:**

This specifies the main entry point of the application, similar to `Program.cs` in .NET.

**tasks:**

This section allows defining custom build tasks, similar to custom MSBuild targets in .csproj files.

#### 4.3.3 gradle.properties

This file sets global properties for the build, such as Java version and encoding which is equivalent to `global.json`.
It can hold sensitive data like API keys (similar to user secrets in .NET).
It can set environment variables such as `org.gradle.jvmargs=-Xmx2g` to configure Gradle's JVM memory settings
It can define project-wide properties like `version=1.0.0` or `group=com.example` which is similar to `Directory.Build.props`.
It can configure repositories and plugin versions which is similar to `NuGet.config`.

#### 4.3.4 gradlew / gradlew.bat

> Equivalent to dotnet CLI  

These are wrapper scripts that allow you to run Gradle commands without needing a local Gradle installation.
They ensure that everyone uses the same Gradle version, similar to how .NET SDK versions are managed globally or via `global.json`.

### 4.4 Additional Resources

- [Gradle Getting Started Guide](https://docs.gradle.org/current/userguide/getting_started_eng.html)

## 5. Spring Boot vs ASP.NET Core

Spring Boot is Java's counterpart to ASP.NET Core for building web applications and APIs.
It provides embedded servers, dependency injection, configuration management, and more.

### 5.1 Spring Boot Dependencies ↔ ASP.NET Core Packages

```kotlin
// Java                                          // C# .NET Equivalent
implementation("spring-boot-starter")            // Microsoft.AspNetCore.App
implementation("spring-boot-starter-web")        // Microsoft.AspNetCore.Mvc
implementation("spring-boot-starter-data-jpa")   // Microsoft.EntityFrameworkCore
implementation("spring-boot-starter-validation") // System.ComponentModel.DataAnnotations
implementation("spring-boot-starter-security")   // Microsoft.AspNetCore.Authentication
implementation("spring-boot-devtools")           // Microsoft.AspNetCore.Mvc.Razor.RuntimeCompilation
runtimeOnly("h2database:h2")                     // Microsoft.EntityFrameworkCore.InMemory
testImplementation("spring-boot-starter-test")   // Microsoft.AspNetCore.Mvc.Testing
```

### 5.2 Application Entry Points

```java
// filepath: src/main/java/com/shop/Application.java    // Equivalent to Program.cs
@SpringBootApplication    // Equivalent to WebApplication.CreateBuilder()
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args); // Equivalent to app.Run()
    }
}
```

registering controllers, services, repositories, contexts and middleware is done via annotations and component scanning.

### 5.3 Configuration Files

```properties
# filepath: src/main/resources/application.properties   // Equivalent to appsettings.json
server.port=8080                              # Similar to Kestrel URLs
spring.datasource.url=jdbc:h2:mem:testdb      # ConnectionStrings
spring.jpa.hibernate.ddl-auto=create-drop     # EF migrations equivalent
spring.jpa.show-sql=true                      # EF logging
```

### 5.4 Additional Resources

- [Spring Boot Tutorials](https://docs.spring.io/spring-boot/tutorial/first-application/index.html)

## 6. Key Differences Summary

| Aspect              | Java/Gradle            | C#/.NET                 |
|---------------------|------------------------|-------------------------|
| **Build Tool**      | Gradle                 | MSBuild + dotnet CLI    |
| **Package Manager** | Maven Central          | NuGet                   |
| **Project File**    | build.gradle.kts       | .csproj                 |
| **Solution File**   | settings.gradle.kts    | .sln                    |
| **Entry Point**     | main() method          | Program.cs              |
| **Config**          | application.properties | appsettings.json        |
| **Web Framework**   | Spring Boot            | ASP.NET Core            |
| **ORM**             | JPA/Hibernate          | Entity Framework        |
| **DI Container**    | Spring                 | Microsoft.Extensions.DI |
| **Testing**         | JUnit                  | xUnit/NUnit/MSTest      |


## 7. java_examples Demo Project

The `java_examples` directory contains a sample Spring Boot project that demonstrates the concepts discussed in this guide.
It includes:
- A simple REST API with CRUD operations
- Integration with an in-memory H2 database using JPA
- Unit and integration tests using JUnit
- Gradle build scripts for managing dependencies and tasks
- Configuration files for application settings
- Instructions for running and testing the application
- Comments and documentation to explain key parts of the code
- A README file with setup and usage instructions
- A comparison of Java and .NET concepts used in the project
- Examples of common patterns and best practices in Java development
- Links to additional resources for learning Java and Spring Boot
- A focus on making the project easy to understand for .NET developers transitioning to Java


### 7.1 Developing the Demo Project step by step

The demo project was developed step by step in the following commits:
- [Initial commit: Basic Gradle project setup](

#### 7.1.1 Initial commit: Basic Gradle project setup

This commit sets up a basic Gradle project structure with necessary files.
- Created `settings.gradle.kts` to define the project name.
- Created `build.gradle.kts` with essential plugins and dependencies.
- Added `gradle.properties` for global properties.
- Included `gradlew` and `gradlew.bat` for Gradle wrapper.
- Set up `.gitignore` to exclude build artifacts and IDE files.
- Added `README.md` with project overview and setup instructions.
- Created `src/main/java/com/shop/Application.java` as the main entry point.
- Created `src/main/resources/application.properties` for configuration.

#### 7.1.2 Added Product entity, repository and controller

This commit introduces the Product entity and its corresponding repository.
- Created `Product.java` in `src/main/java/com/shop/product/` to define the Product entity.
- Created `ProductRepository.java` in `src/main/java/com/shop/product/` to handle database operations.
- Created `ProductController.java` in `src/main/java/com/shop/product/` to expose REST endpoints for Product.
- Created `DataLoader.java` in `src/main/java/com/shop/config/` to initialize sample data on application startup.

#### 7.1.3 Configured Security with Spring Security

This commit adds basic auth security features to the application using Spring Security.
- Created `SecurityConfig.java` in `src/main/java/com/shop/security/` to configure HTTP security and authentication.

#### 7.1.4 Added Cart entity, repository, controller and service

This commit introduces the Cart entity along with its repository, service, and controller.
- Created `Cart.java` in `src/main/java/com/shop/cart/` to define the Cart entity.
- Created `CartRepository.java` in `src/main/java/com/shop/cart/` to handle database operations for Cart.
- Created `CartService.java` in `src/main/java/com/shop/cart/` to define the Cart service interface.
- Created `CartServiceImpl.java` in `src/main/java/com/shop/cart/` to implement CartService interface.
- Created `CartController.java` in `src/main/java/com/shop/cart/` to expose REST endpoints for Cart.

