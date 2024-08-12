plugins {
    kotlin("jvm")
    application
    // Optional, to create a single output jar containing the application and all necessary dependencies
    id("com.gradleup.shadow") version "8.3.0"
}

// Setup repositories; most dependencies are served by Maven Central, UniversalCraft is served by Essential's repo.
repositories {
    mavenCentral()
    maven("https://repo.essential.gg/repository/maven-public")
}

dependencies {
    // Note: To use this outside of this repository, replace 0 with the latest version (can be found in the README).
    val universalCraftVersion = 0
    implementation("gg.essential:universalcraft-standalone:$universalCraftVersion")

    // The example will be using Elementa's LayoutDSL to build its GUI
    // We do recommend you use it too, but it's not a hard requirement, you may even just use raw UScreen + UGraphics
    // to draw your GUI.
    // Note: Be sure to check Elementa's README for its latest version: https://github.com/EssentialGG/Elementa
    val elementaVersion = 659
    implementation("gg.essential:elementa:$elementaVersion")
    implementation("gg.essential:elementa-unstable-statev2:$elementaVersion")
    implementation("gg.essential:elementa-unstable-layoutdsl:$elementaVersion")
}

// Use Java 8 to compile our application.
// You may chose to use a more recent version, 8 is the minimum required by UniversalCraft.
kotlin.jvmToolchain(8)

// Configure our main class so the `:run` task works and the `Main` attribute of the output jar is set accordingly
application {
    mainClass.set("gg.essential.example.MainKt")
}

// Optional, combine the application and all its dependencies into a single fat jar using the Shadow Gradle plugin
tasks.shadowJar {
    // Optional, remove unused classes
    // (doesn't really remove much from the example until https://github.com/GradleUp/shadow/issues/522 is fixed)
    minimize {
        exclude(dependency("org.lwjgl:lwjgl-nanovg:.*")) // segfaults in nvgCreate
        exclude(dependency("gg.essential:universalcraft-standalone:.*")) // https://github.com/GradleUp/shadow/issues/522
        exclude(project(":standalone")) // same as the line above, but for the local version in this repository
    }
}

// This section exists only so the above universalcraft-standalone dependency is resolved directly to the local
// `:standalone` project instead of being fetched from maven.
// If you use this example as a base for your own application, remove this and instead fill in a proper version above.
configurations.all {
    resolutionStrategy.dependencySubstitution {
        substitute(module("gg.essential:universalcraft-standalone")).using(project(":standalone"))
    }
}
