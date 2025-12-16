plugins {
    id("application")
    id("checkstyle")
    id("java")
    id("com.github.ben-manes.versions") version "0.53.0"
    id("org.sonarqube") version "7.2.1.6560"
    jacoco
}

sonar {
    properties {
        property("sonar.projectKey", "Antojkv_java-project-71")
        property("sonar.organization", "antojkv")
    }
}

checkstyle {
    toolVersion = "10.12.4"
}

group = "hexlet.code"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:6.1.0-M1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation("info.picocli:picocli:4.7.7")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.2")
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("hexlet.code.App")
}