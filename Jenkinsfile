pipeline {
  agent any
  stages {
    stage('Initialize') {
      steps {
        sh '''echo username=sk1erdeploy'\n'password=d2eb5509b27c5df1bda08f5c7d652f1b22017cc2 > gradle.properties.private'''
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
  }
}
