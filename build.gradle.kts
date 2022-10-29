import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.jlleitschuh.gradle.ktlint") version "11.0.0"
    kotlin("jvm") version "1.7.20"
    application
}

group = "el.calculator"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.github.h0tk3y.betterParse:better-parse:0.4.4")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.9.0")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.9.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
}

application {
    mainClass.set("el.calculator.ApplicationKt")
}

tasks {
    test {
        useJUnitPlatform()
    }

    named<JavaExec>("run") {
        standardInput = System.`in`
    }

    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "16"
    }
}
