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

        stage('Deploy') {
            steps {
                withCredentials([
                        string(credentialsId: 'AWS_ACCESS_KEY_ID',
                            variable: 'AWS_ACCESS_KEY_ID'),
                        string(credentialsId: 'AWS_SECRET_ACCESS_KEY',
                            variable: 'AWS_SECRET_ACCESS_KEY')
                    ]) {
                    bat '''
                docker rm -f track-activity-backend 2>nul || echo Container does not exist

                docker run -d ^
                  --name track-activity-backend ^
                  -p 8081:8081 ^
                  -e AWS_REGION=ap-south-1 ^
                  -e AWS_ACCESS_KEY_ID=%AWS_ACCESS_KEY_ID% ^
                  -e AWS_SECRET_ACCESS_KEY=%AWS_SECRET_ACCESS_KEY% ^
                  track-activity-backend
            '''
                }
            }
        }
    }
}