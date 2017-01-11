node('androidSDK') {
  stage('Build') {
    checkout scm
    sh './gradlew clean assemble'
  }
  stage('Check') {
  sh './gradlew check'
    stage('jUnit') {
    junit '**/build/test-results/**/*.xml'
    }
    stage('androidLint') {
    androidLint canComputeNew: false, defaultEncoding: '', healthy: '0', pattern: '**/lint-results*.xml', unHealthy: '10'
    }
  }
  stage('Connected Check') {
  sh 'echo "To be configured"'
  }
}
