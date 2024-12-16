import org.hidetake.gradle.swagger.generator.GenerateSwaggerUI
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.4.0"
    id("io.spring.dependency-management") version "1.1.6"
    kotlin("plugin.jpa") version "1.9.25"
    id("org.jlleitschuh.gradle.ktlint") version "12.1.0"

    id("com.epages.restdocs-api-spec") version "0.18.4"
    id("org.hidetake.swagger.generator") version "2.18.2"
}

group = "camp.nextstep.edu"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    runtimeOnly("com.h2database:h2")

    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.2")

    // Kotest (Unin Test)
    testImplementation("io.kotest:kotest-runner-junit5:5.7.2")
    testImplementation("io.kotest:kotest-assertions-core:5.7.2")

    // Spring Boot Test (Integration Test)
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.2")

    // Rest Assured (Integration Test)
    testImplementation("io.rest-assured:rest-assured:5.5.0")
    testImplementation("io.rest-assured:kotlin-extensions:5.5.0")

    // Mock MVC (Documentation Test)
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    testImplementation("com.epages:restdocs-api-spec-mockmvc:0.18.4")
    testImplementation("com.ninja-squad:springmockk:4.0.2")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<GenerateSwaggerUI> {
    dependsOn("openapi3")

    delete("src/main/resources/static/docs/")
    copy {
        from("build/resources/main/static/docs/")
        into("src/main/resources/static/docs/")
    }
}

tasks.withType<BootJar> {
    dependsOn("openapi3")
    dependsOn("postman")
}

openapi3 {
    setServer("http://localhost:8080")
    title = "로또 당첨 확인 서비스"
    description = "구매한 로또를 입력하고 당첨을 확인하는 서비스"
    version = "0.0.1"
    format = "yaml"
    outputDirectory = "build/resources/main/static/docs"
}
