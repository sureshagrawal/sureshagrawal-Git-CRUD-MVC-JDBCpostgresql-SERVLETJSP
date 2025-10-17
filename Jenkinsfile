pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "sureshagrawal/crud-app:latest"
    }

    stages {

        stage('Checkout Code') {
            steps {
                echo '📦 Checking out source code from GitHub...'
                git branch: 'main', url: 'https://github.com/sureshagrawal/sureshagrawal-Git-CRUD-MVC-JDBCpostgresql-SERVLETJSP.git', credentialsId: 'github-token'
            }
        }

        stage('Login to Docker Hub') {
            steps {
                echo '🔐 Logging in to Docker Hub...'
                withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    bat 'echo %DOCKER_PASS% | docker login -u %DOCKER_USER% --password-stdin'
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                echo '🐳 Building Docker image...'
                bat 'docker build -t %DOCKER_IMAGE% .'
            }
        }

        stage('Push Docker Image') {
            steps {
                echo '🚀 Pushing Docker image to Docker Hub...'
                bat 'docker push %DOCKER_IMAGE%'
            }
        }

        stage('Trigger Render Deploy') {
            steps {
                echo '🌍 Triggering Render deploy hook...'
                withCredentials([string(credentialsId: 'RENDER_DEPLOY_HOOK', variable: 'HOOK')]) {
                    bat 'curl -X POST %HOOK%'
                }
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
