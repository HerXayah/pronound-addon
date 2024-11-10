plugins {
    id("net.labymod.labygradle")
    id("net.labymod.labygradle.addon")
}


val versions = providers.gradleProperty("net.labymod.minecraft-versions").get().split(";")

group = "com.funkeln.pronouns"
version = providers.environmentVariable("VERSION").getOrElse("1.0.10")

labyMod {
    defaultPackageName = "com.funkeln.pronouns" //change this to your main package name (used by all modules)
    addonInfo {
        namespace = "pronouns"
        displayName = "PronounsDisplay"
        author = "starwakes"
        description = "Display your Pronouns from pronouns.page ingame"
        minecraftVersion = "*"
        version = getVersion().toString()
    }

    minecraft {
        registerVersion(versions.toTypedArray()) {
            runs {
                getByName("client") {
                    // When the property is set to true, you can log in with a Minecraft account
                    // devLogin = true
                }
            }
        }
    }
}

subprojects {
    plugins.apply("net.labymod.labygradle")
    plugins.apply("net.labymod.labygradle.addon")

    group = rootProject.group
    version = rootProject.version
}
