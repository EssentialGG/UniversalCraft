import gg.essential.gradle.util.*

plugins {
    kotlin("jvm")
    id("gg.essential.multi-version")
    id("gg.essential.defaults")
    id("gg.essential.defaults.maven-publish")
}

group = "gg.essential"

java.withSourcesJar()
tasks.compileKotlin.setJvmDefault(if (platform.mcVersion >= 11400) "all" else "all-compatibility")
loom.noServerRunConfigs()

repositories {
    maven("https://repo.spongepowered.org/repository/maven-public/")
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.5.21")
    if(platform.mcVersion >= 11502) {
        compileOnly("org.spongepowered:mixin:0.7.11-SNAPSHOT")
    }
}

tasks.jar {
    manifest {
        attributes(mapOf("FMLModType" to "LIBRARY",
            "MixinConfigs" to "mixins.universal.json"))
    }
}
