pipeline {
    agent any

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Maven Build') {
            steps {
                bat 'mvn clean package -DskipTests'
            }
        }
        stage('Docker Build') {
            steps {
                bat 'docker build -t track-activity-backend .'
            }
        }
    }
}