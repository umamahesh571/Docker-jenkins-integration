pipeline {
    agent any

    environment {
        // Credentials must be added in Jenkins as a username/password type with ID: 'dockerhub'
        DOCKERHUB_CREDENTIALS = credentials('dockerhub')
        DOCKERHUB_USERNAME = "${DOCKERHUB_CREDENTIALS_USR}"
        DOCKERHUB_PASSWORD = "${DOCKERHUB_CREDENTIALS_PSW}"
        APP_NAME = "evolve-ui"
        VERSION = "1.0.0"
        JAR_FILE = "target/${APP_NAME}-${VERSION}.jar"
        DOCKER_IMAGE = "${DOCKERHUB_USERNAME}/${APP_NAME}:latest"
        CONTAINER_NAME = "${APP_NAME}-container"
    }

    stages {
        stage('Checkout') {
            steps {
                // Checkout the 'main' branch from GitHub
                git branch: 'main', url: 'https://github.com/umamahesh571/Docker-jenkins-integration.git'
            }
        }

        stage('Build with Maven') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t $DOCKER_IMAGE .'
            }
        }

        stage('Push to Docker Hub') {
            steps {
                script {
                    sh """
                        echo $DOCKERHUB_PASSWORD | docker login -u $DOCKERHUB_USERNAME --password-stdin
                        docker push $DOCKER_IMAGE
                    """
                }
            }
        }

        stage('Deploy') {
            steps {
                sh """
                    docker rm -f $CONTAINER_NAME || true
                    docker run -d -p 8090:8090 --name $CONTAINER_NAME $DOCKER_IMAGE
                """
            }
        }
    }

    post {
        success {
            echo "✅ Build, Docker push, and deployment completed successfully!"
        }
        failure {
            echo "❌ Pipeline failed. Check above logs for error."
        }
    }
}
