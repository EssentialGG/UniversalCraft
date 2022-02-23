# UniversalCraft

A full Java interop library that wraps Minecraft classes which allows you to write code for multiple versions at the same time. Built using ReplayMod's [Preprocessor](https://github.com/ReplayMod/preprocessor).

## Dependency

It's recommended that you include [Essential](link eventually) instead of adding it yourself.

In your repository block, add:
```groovy
maven {
    url = "https://repo.sk1er.club/repository/maven-public"
}
```
To use the latest builds, use the following dependency format, use the build reference to find the correct replacements:

```groovy
implementation "gg.essential:universalcraft-$mcVersion-$mcPlatform:$buildNumber"
```
<details><summary>Build Reference</summary>
    <table>
      <tbody>
        <tr>
          <th>mcVersion</th>
          <th>mcPlatform</th>
          <th>buildNumber</th>
        </tr>
        <tr>
          <td>1.18.1</td>
          <td>fabric</td>
          <td>
            <img alt="1.18.1-fabric" src="https://badges.modcore.net/badge/dynamic/xml?color=A97BFF&label=%20&query=%2Fmetadata%2Fversioning%2Flatest&url=https://repo.sk1er.club/repository/maven-releases/gg/essential/universalcraft-1.18.1-fabric/maven-metadata.xml">
          </td>
        </tr>
        <tr>
          <td>1.18.1</td>
          <td>forge</td>
          <td>
            <img alt="1.18.1-forge" src="https://badges.modcore.net/badge/dynamic/xml?color=A97BFF&label=%20&query=%2Fmetadata%2Fversioning%2Flatest&url=https://repo.sk1er.club/repository/maven-releases/gg/essential/universalcraft-1.18.1-forge/maven-metadata.xml">
          </td>
        </tr>
        <tr>
          <td>1.17.1</td>
          <td>fabric</td>
          <td>
            <img alt="1.17.1-fabric" src="https://badges.modcore.net/badge/dynamic/xml?color=A97BFF&label=%20&query=%2Fmetadata%2Fversioning%2Flatest&url=https://repo.sk1er.club/repository/maven-releases/gg/essential/universalcraft-1.17.1-fabric/maven-metadata.xml">
          </td>
        </tr>
        <tr>
          <td>1.17.1</td>
          <td>forge</td>
          <td>
            <img alt="1.17.1-forge" src="https://badges.modcore.net/badge/dynamic/xml?color=A97BFF&label=%20&query=%2Fmetadata%2Fversioning%2Flatest&url=https://repo.sk1er.club/repository/maven-releases/gg/essential/universalcraft-1.17.1-forge/maven-metadata.xml">
          </td>
        </tr>
        <tr>
          <td>1.12.2</td>
          <td>forge</td>
          <td>
            <img alt="1.12.2-forge" src="https://badges.modcore.net/badge/dynamic/xml?color=A97BFF&label=%20&query=%2Fmetadata%2Fversioning%2Flatest&url=https://repo.sk1er.club/repository/maven-releases/gg/essential/universalcraft-1.12.2-forge/maven-metadata.xml">
          </td>
        </tr>
        <tr>
          <td>1.8.9</td>
          <td>forge</td>
          <td>
            <img alt="1.8.9-forge" src="https://badges.modcore.net/badge/dynamic/xml?color=A97BFF&label=%20&query=%2Fmetadata%2Fversioning%2Flatest&url=https://repo.sk1er.club/repository/maven-releases/gg/essential/universalcraft-1.8.9-forge/maven-metadata.xml">
          </td>
        </tr>
      </tbody>
    </table>

</details>

<span style="font-size:3em; color:red;">IMPORTANT!</span>

You must also shade UC to avoid potential crashes with other mods. To do this, you will need to use the Shadow Gradle plugin.

<details><summary>Groovy Version</summary>

You can do this by either putting it in your plugins block:
```groovy
plugins {
    id 'com.github.johnrengelman.shadow' version "$version"
}
```
or by including it in your buildscript's classpath and applying it:
```groovy
buildscript {
    repositories {
        gradlePluginPortal()
    }
    dependencies {
        classpath "gradle.plugin.com.github.jengelman.gradle.plugins:shadow:$version"
    }
}

apply plugin: 'com.github.johnrengelman.shadow'
```
You'll then want to relocate UC to your own package to avoid breaking other mods
```groovy
shadowJar {
    relocate("gg.essential.universal", "your.package.universal")
}
```

</details>

<details><summary>Kotlin Script Version</summary>

You can do this by either putting it in your plugins block:
```kotlin
plugins {
    id("com.github.johnrengelman.shadow") version "$version"
}
```
or by including it in your buildscript's classpath and applying it:
```kotlin
buildscript {
    repositories {
        gradlePluginPortal()
    }
    dependencies {
        classpath("gradle.plugin.com.github.jengelman.gradle.plugins:shadow:$version")
    }
}

plugins {
    id("com.github.johnrengelman.shadow")
}
```
You'll then want to relocate UC to your own package to avoid breaking other mods
```kotlin
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

tasks {
    named<ShadowJar>("shadowJar") {
        archiveFileName.set(jar.get().archiveFileName)
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE

        relocate("gg.essential.universal", "your.package.universal")
    }
}
```

</details>
