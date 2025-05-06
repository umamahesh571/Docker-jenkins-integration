pipeline {
    agent any
    environment {
        DOCKERHUB_CREDENTIALS = credentials('dockerhub')
        DOCKER_IMAGE = "${DOCKERHUB_CREDENTIALS_USR}/evolve-ui:latest"
        APP_NAME = "evolve-ui"
        JAR_FILE = "target/${APP_NAME}-1.0.0.jar"
    }
    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/umamahesh571/Docker-jenkins-integration.git'
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
                sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
                sh 'docker push $DOCKER_IMAGE'
            }
        }
        stage('Run Container') {
            steps {
                sh 'docker rm -f evolve-ui-container || true'
                sh 'docker run -d -p 8090:8090 --name evolve-ui-container $DOCKER_IMAGE'
            }
        }
    }
}
