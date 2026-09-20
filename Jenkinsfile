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

        stage('Docker Build') {
            steps {
                bat 'docker build -t track-activity-backend .'
            }
        }

// 👇 YE NAYA ADD KARO
        stage('Push to ECR') {
            steps {
                withCredentials([
                        string(credentialsId: 'AWS_ACCESS_KEY_ID',
                                variable: 'AWS_ACCESS_KEY_ID'),
                        string(credentialsId: 'AWS_SECRET_ACCESS_KEY',
                                variable: 'AWS_SECRET_ACCESS_KEY')
                ]) {
                    bat '''
                aws ecr get-login-password --region ap-south-1 | docker login --username AWS --password-stdin 331191957836.dkr.ecr.ap-south-1.amazonaws.com

                docker tag track-activity-backend:latest 331191957836.dkr.ecr.ap-south-1.amazonaws.com/track-activity-backend:latest

                docker push 331191957836.dkr.ecr.ap-south-1.amazonaws.com/track-activity-backend:latest
            '''
                }
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