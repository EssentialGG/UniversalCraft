pipeline {
  agent any
  stages {
    stage('Initialize') {
      steps {
        sh '''echo username=sk1erdeploy'\n'password=${github-deploy} > gradle.properties.private'''
        sh '''./gradlew clean
        ./gradlew setupCiWorkspace --no-daemon'''
      }
    }
    stage('Build') {
      steps {
        sh "./gradlew build -PBUILD_ID=${env.BUILD_ID} --no-daemon"
      }
    }


    stage('Report') {
      steps {
        archiveArtifacts 'versions/1.8.9/build/libs/*.jar'
        archiveArtifacts 'versions/1.12.2/build/libs/*.jar'
        archiveArtifacts 'versions/1.15.2/build/libs/*.jar'
      }
    }
    stage('Publish') {
        steps {
            nexusPublisher nexusInstanceId: 'sk1errepo', nexusRepositoryId: 'maven-snapshots', packages: [[$class: 'MavenPackage', mavenAssetList: [[classifier: '', extension: '', filePath: 'versions/1.8.9/build/libs/UniversalCraft1.8.9-${BUILD_ID}.jar'], [classifier: '', extension: '', filePath: 'versions/1.12.2/build/libs/UniversalCraft1.12.2-${BUILD_ID}.jar']], mavenCoordinate: [artifactId: 'UniversalVersion', groupId: 'club.sk1er', packaging: 'idk', version: '1.0']]], tagName: '${BUILD_ID}'
        }
    }
  }
}
