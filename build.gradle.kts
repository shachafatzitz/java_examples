// Apply necessary plugins
plugins {
    application
    id("org.springframework.boot") version "3.3.3"
    id("io.spring.dependency-management") version "1.1.6"
}

// Project metadata
group = "com"
version = "0.0.1-SNAPSHOT"

// Repositories for dependency resolution
repositories { 
    mavenCentral() 
}

// Project dependencies
dependencies {
    // Spring Boot starters - pre-configured dependency bundles
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-security")

    // Development and runtime dependencies
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("com.h2database:h2")

    // Testing dependencies
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

// Application configuration
application {
    mainClass = "com.shop.Application"
}

// Java toolchain configuration
java { 
    toolchain { 
        languageVersion.set(JavaLanguageVersion.of(21)) 
    } 
}

// Spring Boot specific task configuration
tasks.named<org.springframework.boot.gradle.tasks.run.BootRun>("bootRun") {
    mainClass.set("com.shop.Application")
}

// Test task configuration to use JUnit Platform
tasks.named<Test>("test") { 
    useJUnitPlatform()
}

// Documentation generation with exclusions
tasks.named<Javadoc>("javadoc").configure {
    exclude("app/Internal*.java")
    exclude("app/internal/*")
}

// Custom task for creating reports archive
tasks.register<Zip>("zip-reports") {
    description = "Zips the reports directory"
    group = "Reporting"
    from("Reports/")
    include("*")
    archiveFileName.set("Reports.zip")
    destinationDirectory.set(file("/dir"))
}