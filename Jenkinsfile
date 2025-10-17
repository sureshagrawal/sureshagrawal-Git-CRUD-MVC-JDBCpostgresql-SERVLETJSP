pipeline {
    agent any

    environment {
        DOCKER_USER = 'sureshagrawal'
        DOCKER_PASS = credentials('docker-hub-credentials') // Jenkins credentials
    }

    stages {
        stage('Checkout SCM') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/sureshagrawal/sureshagrawal-Git-CRUD-MVC-JDBCpostgresql-SERVLETJSP.git',
                    credentialsId: 'github-token'
            }
        }

        stage('Docker Login') {
            steps {
                bat """
                echo %DOCKER_PASS% | docker login -u %DOCKER_USER% --password-stdin
                """
            }
        }

        stage('Pre-Pull Base Images') {
            steps {
                bat 'docker pull tomcat:11.0.7-jdk21-temurin'
                bat 'docker pull maven:3.9-eclipse-temurin-21'
            }
        }

        stage('Build Docker Image') {
            steps {
                bat 'docker build -t sureshagrawal/crud-app:latest .'
            }
        }

        stage('Push Docker Image') {
            steps {
                bat 'docker push sureshagrawal/crud-app:latest'
            }
        }

        stage('Trigger Render Deploy') {
            steps {
                bat 'curl -X POST %RENDER_DEPLOY_HOOK%'
            }
        }
    }

    post {
        failure {
            echo '❌ Build or deployment failed. Check console output.'
        }
    }
}
