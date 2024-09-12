import net.labymod.gradle.core.processor.ReferenceType

version = "0.1.0"

plugins {
    id("java-library")
}

repositories {
    mavenCentral()
}

dependencies {
    api(project(":api"))
    runtimeOnly(
        group = "org.apache.httpcomponents",
        name = "httpclient",
        version = "4.5.14"
    )
}

labyModProcessor {
    referenceType = ReferenceType.DEFAULT
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}
