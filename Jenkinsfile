pipeline {
    agent any

    environment {
        DOCKER_USER = 'sureshagrawal'
        DOCKER_PASS = credentials('docker-hub-credentials') // Jenkins secret
        HTTP_PROXY = ''  // set if needed
        HTTPS_PROXY = '' // set if needed
    }

    stages {
        stage('Checkout SCM') {
            steps {
                echo "📦 Checking out source code from GitHub..."
                git url: 'https://github.com/sureshagrawal/sureshagrawal-Git-CRUD-MVC-JDBCpostgresql-SERVLETJSP.git',
                    branch: 'main',
                    credentialsId: 'github-token'
            }
        }

        stage('Login to Docker Hub') {
            steps {
                echo "🔐 Logging in to Docker Hub..."
                withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
                    bat "echo %PASS% | docker login -u %USER% --password-stdin"
                }
            }
        }

        stage('Pre-Pull Base Images') {
            steps {
                echo "⬇️ Pulling base images to avoid network failures..."
                bat "docker pull tomcat:11.0.7-jdk21-temurin"
                bat "docker pull maven:3.9-eclipse-temurin-21"
            }
        }

        stage('Build Docker Image') {
            steps {
                echo "🐳 Building Docker image..."
                withEnv(["HTTP_PROXY=${env.HTTP_PROXY}", "HTTPS_PROXY=${env.HTTPS_PROXY}"]) {
                    bat "docker build -t sureshagrawal/crud-app:latest ."
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                echo "🚀 Pushing Docker image to Docker Hub..."
                bat "docker push sureshagrawal/crud-app:latest"
            }
        }

        stage('Trigger Render Deploy') {
            steps {
                echo "🌐 Triggering Render deployment..."
                // Add Render deploy API call if needed
            }
        }
    }

    post {
        success {
            echo "✅ Build and deployment completed successfully!"
        }
        failure {
            echo "❌ Build or deployment failed. Check console output."
        }
    }
}
