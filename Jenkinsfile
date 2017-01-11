stage('Build') {
  node('androidSDK') {
    checkout scm
    sh './gradlew clean assemble'
    stash includes: '**/build/**', name: 'build'
  }
}
stage('Check') {
  parallel(
    'jUnit': {
      node('androidSDK') {
        unstash 'build'
        sh './gradlew test'
        junit '**/build/test-results/**/*.xml'
      }
    },
    'androidLint': {
      node('androidSDK') {
        unstash 'build'
        sh './gradlew lint'
        androidLint canComputeNew: false, defaultEncoding: '', failedTotalAll: '50', healthy: '0', pattern: '**/lint-results*.xml', unHealthy: '100', unstableTotalAll: '0'
      }
     },
    'detekt': {
      node('androidSDK') {
        checkout scm
        sh './gradlew detekt'
      }
    }
  )
}
stage('Connected Check') {
  sh 'echo "To be configured"'
  parallel(
    'api24': {
      node('androidSDK') {
        sh 'echo "To be configured"'
      }
    },
    'api25': {
      node('androidSDK') {
        sh 'echo "To be configured"'
      }
    }
  )
}
stage('Archive') {
  node('master') {
    unstash 'build'
    archiveArtifacts artifacts: '**/*.apk', fingerprint: true, onlyIfSuccessful: true
  }
}
