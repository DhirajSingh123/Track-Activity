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

        stage('Deploy to EC2') {
            steps {
                withCredentials([
                        string(credentialsId: 'AWS_ACCESS_KEY_ID',
                                variable: 'AWS_ACCESS_KEY_ID'),
                        string(credentialsId: 'AWS_SECRET_ACCESS_KEY',
                                variable: 'AWS_SECRET_ACCESS_KEY')
                ]) {
                    bat '''
                aws ssm send-command ^
                  --instance-ids "i-01fff8561f87d5dad" ^
                  --document-name "AWS-RunShellScript" ^
                  --comment "Deploy Track Activity Backend" ^
                  --parameters "commands=['aws ecr get-login-password --region ap-south-1 | docker login --username AWS --password-stdin 331191957836.dkr.ecr.ap-south-1.amazonaws.com','docker pull 331191957836.dkr.ecr.ap-south-1.amazonaws.com/track-activity-backend:latest','docker rm -f track-activity-backend || true','docker run -d --name track-activity-backend -p 8081:8081 -e AWS_REGION=ap-south-1 331191957836.dkr.ecr.ap-south-1.amazonaws.com/track-activity-backend:latest']" ^
                  --region ap-south-1
            '''
                }
            }
        }
    }
}