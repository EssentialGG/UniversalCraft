# UniversalCraft

A full Java inter-op library that wraps Minecraft classes which allows you to write code for multiple versions at the same time. Built using ReplayMod's [Preprocessor](https://github.com/ReplayMod/preprocessor).

## Dependency

In your repository block, add:
```groovy
maven {
    url = "https://repo.sk1er.club/repository/maven-public"
}
```
To use the latest builds, use the following dependency:

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
