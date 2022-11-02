pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                 sh 'maven clean compile'
            }
        }
        stage('Test') {
            steps {
               sh 'maven test'
            }
        }
        stage('Deploy') {
            steps {
                 sh 'maven test'
            }
        }
    }
}