rootProject.name = "pronound-addon"

pluginManagement {
    val labyGradlePluginVersion = "0.4.6"
    plugins {
        id("net.labymod.gradle") version (labyGradlePluginVersion)
    }

    buildscript {
        repositories {
            maven("https://dist.labymod.net/api/v1/maven/release/")
            maven("https://repo.spongepowered.org/repository/maven-public")
            mavenCentral()
        }

        dependencies {
            classpath("net.labymod.gradle", "addon", labyGradlePluginVersion)
        }
    }
}

include(
    ":api",
    ":core",
)

plugins.apply("net.labymod.gradle")
