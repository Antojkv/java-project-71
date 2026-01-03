plugins {
    id("application")
    id("checkstyle")
    id("java")
    id("com.github.ben-manes.versions") version "0.53.0"
    id("org.sonarqube") version "7.2.1.6560"
    id("jacoco")
}

sonar {
    properties {
        property("sonar.projectKey", "Antojkv_java-project-71")
        property("sonar.organization", "antojkv")
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.coverage.jacoco.xmlReportPaths",
            layout.buildDirectory.file("reports/jacoco/test/jacocoTestReport.xml").get().asFile.absolutePath)
        property("sonar.jacoco.reportPaths",
            layout.buildDirectory.file("reports/jacoco/test/jacocoTestReport.xml").get().asFile.absolutePath)
        property("sonar.java.binaries", "${layout.buildDirectory.get()}/classes/java/main")
        property("sonar.java.test.binaries", "${layout.buildDirectory.get()}/classes/java/test")
        property("sonar.sources", "${project.projectDir}/src/main/java")
        property("sonar.tests", "${project.projectDir}/src/test/java")
        property("sonar.inclusions", "src/main/java/**/*")
        property("sonar.test.inclusions", "src/test/java/**/*")
        property("sonar.sourceEncoding", "UTF-8")
        property("sonar.language", "java")
        property("sonar.verbose", "true")
        property("sonar.log.level", "DEBUG")
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
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.17.2")
    testImplementation("org.assertj:assertj-core:3.22.0")
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("hexlet.code.App")
}

jacoco {
    toolVersion = "0.8.9"
}

tasks.test {
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required = true
        csv.required = false
        html.required = true
        html.outputLocation = layout.buildDirectory.dir("jacocoHtml")
    }

    // Проверка отчета Jacoco
    doLast {
        val xmlReport = layout.buildDirectory.file("reports/jacoco/test/jacocoTestReport.xml").get().asFile
        println("=== Jacoco XML отчет ===")
        println("Путь: ${xmlReport.absolutePath}")
        println("Существует: ${xmlReport.exists()}")
        println("Размер: ${xmlReport.length()} байт")

        if (xmlReport.exists()) {
            val content = xmlReport.readText()
            val hasPlainFormatter = content.contains("PlainFormatter")
            val hasStylishFormatter = content.contains("StylishFormatter")
            println("Содержит PlainFormatter: $hasPlainFormatter")
            println("Содержит StylishFormatter: $hasStylishFormatter")
        }
    }
}

// Конфигурация задачи sonar
tasks.named("sonar") {
    dependsOn(tasks.jacocoTestReport)
    mustRunAfter(tasks.jacocoTestReport)

    // Проверка перед запуском Sonar
    doFirst {
        println("=== Проверка перед SonarQube ===")
        val xmlReport = layout.buildDirectory.file("reports/jacoco/test/jacocoTestReport.xml").get().asFile
        println("Отчет для Sonar: ${xmlReport.absolutePath}")

        if (!xmlReport.exists()) {
            throw GradleException("Jacoco отчет не найден! Запустите сначала ./gradlew test jacocoTestReport")
        }
    }
}

// Задача для быстрой проверки путей
tasks.register("checkSonarPaths") {
    group = "verification"
    description = "Проверка путей для SonarQube"

    doLast {
        println("=== Проверка путей для Sonar ===")
        println("Project dir: ${project.projectDir}")
        println("Build dir: ${layout.buildDirectory.get()}")

        val xmlPath = layout.buildDirectory.file("reports/jacoco/test/jacocoTestReport.xml").get().asFile
        println("XML отчет: ${xmlPath.absolutePath}")
        println("XML существует: ${xmlPath.exists()}")

        println("\nMain classes: ${layout.buildDirectory.get()}/classes/java/main")
        println("Test classes: ${layout.buildDirectory.get()}/classes/java/test")

        // Проверим структуру
        val mainClassesDir = file("${layout.buildDirectory.get()}/classes/java/main/hexlet/code")
        println("\nСодержимое build/classes/java/main/hexlet/code:")
        if (mainClassesDir.exists() && mainClassesDir.isDirectory) {
            mainClassesDir.listFiles()?.forEach { println("  - ${it.name}") }
        } else {
            println("  Директория не существует: ${mainClassesDir.absolutePath}")
        }

        val testClassesDir = file("${layout.buildDirectory.get()}/classes/java/test/hexlet/code")
        println("\nСодержимое build/classes/java/test/hexlet/code:")
        if (testClassesDir.exists() && testClassesDir.isDirectory) {
            testClassesDir.listFiles()?.forEach { println("  - ${it.name}") }
        } else {
            println("  Директория не существует: ${testClassesDir.absolutePath}")
        }

        println("\n=== Проверка исходного кода ===")
        val mainSourceDir = file("${project.projectDir}/src/main/java/hexlet/code")
        println("Исходники main: ${mainSourceDir.absolutePath}")
        if (mainSourceDir.exists()) {
            mainSourceDir.walkTopDown().maxDepth(2).forEach {
                if (it.isFile) println("  - ${it.relativeTo(mainSourceDir)}")
            }
        }

        val testSourceDir = file("${project.projectDir}/src/test/java/hexlet/code")
        println("\nИсходники test: ${testSourceDir.absolutePath}")
        if (testSourceDir.exists()) {
            testSourceDir.walkTopDown().maxDepth(2).forEach {
                if (it.isFile) println("  - ${it.relativeTo(testSourceDir)}")
            }
        }
    }
}

// Простая проверка Jacoco
tasks.register("checkJacoco") {
    group = "verification"
    description = "Простая проверка Jacoco отчета"

    dependsOn(tasks.jacocoTestReport)

    doLast {
        val reportFile = file("${layout.buildDirectory.get()}/reports/jacoco/test/jacocoTestReport.xml")
        println("Проверка Jacoco отчета:")
        println("Файл: ${reportFile.absolutePath}")
        println("Существует: ${reportFile.exists()}")

        if (reportFile.exists()) {
            val content = reportFile.readText()
            println("Размер: ${content.length} символов")
            println("Содержит PlainFormatter: ${content.contains("PlainFormatter")}")
            println("Содержит StylishFormatter: ${content.contains("StylishFormatter")}")
            println("Содержит formatters пакет: ${content.contains("hexlet/code/formatters")}")
        }
    }
}