pipeline {
  agent any
  stages {
    stage('Initialize') {
      steps {
        sh '''echo username=sk1erdeploy'\n'password=d2eb5509b27c5df1bda08f5c7d652f1b22017cc2 > gradle.properties.private'''
        sh '''./gradlew clean
./gradlew setupCiWorkspace'''
      }
    }
    stage('Build') {
      steps {
        sh '''./gradlew build
'''
      }
    }
    stage('Report') {
      steps {
        archiveArtifacts 'build/libs/*.jar'
      }
    }
  }
}
