version = "0.1.0"

plugins {
    id("java-library")
}

dependencies {
    api(project(":api"))
    maven(mavenCentral(), "org.apache.httpcomponents:httpclient:4.5.14")
}

labyModProcessor {
    referenceType = net.labymod.gradle.core.processor.ReferenceType.DEFAULT
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}