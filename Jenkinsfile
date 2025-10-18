pipeline {
    agent any

    environment {
        DOCKERHUB_CRED = 'docker-hub-credentials'
        IMAGE_NAME = 'sureshagrawal/crud-app:latest'
        RENDER_API_KEY = credentials('render-api-key')
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', credentialsId: 'github-token', url: 'https://github.com/sureshagrawal/sureshagrawal-Git-CRUD-MVC-JDBCpostgresql-SERVLETJSP.git'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh "docker build -t ${IMAGE_NAME} ."
            }
        }

        stage('Push Docker Image') {
            steps {
                withCredentials([usernamePassword(credentialsId: "${DOCKERHUB_CRED}", usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    sh '''
                        echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin
                        docker push ${IMAGE_NAME}
                        docker logout
                    '''
                }
            }
        }

        stage('Trigger Render Deploy') {
            steps {
                sh '''
                    curl -X POST -H "Accept: application/json" -H "Authorization: Bearer $RENDER_API_KEY" $RENDER_API_KEY
                '''
            }
        }
    }

    post {
        success {
            echo "✅ Pipeline completed successfully!"
        }
        failure {
            echo "❌ Pipeline failed!"
        }
    }
}
