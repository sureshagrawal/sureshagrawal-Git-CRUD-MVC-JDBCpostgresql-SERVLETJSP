pipeline {
    agent any

    environment {
        DOCKERHUB_CRED = 'docker-hub-credentials'      // Docker Hub username + password
        IMAGE_NAME = 'sureshagrawal/crud-app:latest'   // Docker Hub image name
        RENDER_API_KEY = credentials('render-api-key') // Render deploy hook
    }

    stages {
        stage('Checkout') {
            steps {
                echo "Cloning repository..."
                git branch: 'main', credentialsId: 'github-token', url: 'https://github.com/sureshagrawal/sureshagrawal-Git-CRUD-MVC-JDBCpostgresql-SERVLETJSP.git'
            }
        }

        stage('Build Docker Image') {
            steps {
                echo "Building Docker image..."
                sh "docker build -t ${IMAGE_NAME} ."
            }
        }

        stage('Push Docker Image') {
            steps {
                echo "Logging in and pushing to Docker Hub..."
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
                echo "Triggering Render deployment..."
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
