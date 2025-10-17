pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "sureshagrawal/crud-app:latest"
        RENDER_DEPLOY_HOOK = credentials('RENDER_DEPLOY_HOOK')
    }

    stages {
        stage('Checkout Code') {
            steps {
                git branch: 'main', url: 'https://github.com/sureshagrawal/sureshagrawal-Git-CRUD-MVC-JDBCpostgresql-SERVLETJSP.git'
            }
        }

        stage('Login to Docker Hub') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    sh 'echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin'
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t $DOCKER_IMAGE .'
            }
        }

        stage('Push Docker Image') {
            steps {
                sh 'docker push $DOCKER_IMAGE'
            }
        }

        stage('Trigger Render Deploy') {
            steps {
                sh 'curl -X POST $RENDER_DEPLOY_HOOK'
            }
        }
    }

    post {
        success {
            echo '✅ Deployment pipeline completed successfully!'
        }
        failure {
            echo '❌ Build or deployment failed. Check console output.'
        }
    }
}
