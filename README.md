JENKINS-DOCKER-INTEGRATION:
--------------------------
UBUNTU T2 MEDIUM 4GIB SERVER AND ALL TCP ENABLED

AS a  ROOT USER

Install Docker

sudo apt install docker.io -y
sudo usermod -aG docker jenkins
sudo systemctl restart jenkins

usermod -aG docker jenkins

Plugins required:
maven integration
pipeline
pipeline stage view
deploy to container
docker pipeline 

Add credentials: dockerhub credentials
manage jenkins--> credentials--> global--> add credentials-->username password-->dockerhub id name

Create job :

create new pipeline job with jenkins file 


pipeline {
    agent any

    environment {
        DOCKERHUB_CREDENTIALS = credentials('dockerhub-creds')
        DOCKERHUB_USERNAME = "${DOCKERHUB_CREDENTIALS_USR}"
        DOCKERHUB_PASSWORD = "${DOCKERHUB_CREDENTIALS_PSW}"
        APP_NAME = "evolve-ui"
        VERSION = "1.0.0"
        JAR_FILE = "target/${APP_NAME}-${VERSION}.jar"
        DOCKER_IMAGE = "${DOCKERHUB_USERNAME}/${APP_NAME}:latest"
    }

    stages {
        stage('Checkout') {
            steps {
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

        stage('Run Docker Container') {
            steps {
                script {
                    sh """
                        docker stop $APP_NAME || true
                        docker rm $APP_NAME || true
                        docker run -d --name $APP_NAME -p 8090:8090 $DOCKER_IMAGE
                    """
                }
            }
        }
    }

    post {
        success {
            echo "✅ Build, push, and Docker deployment completed!"
        }
        failure {
            echo "❌ Pipeline failed. Investigate above steps."
        }
    }
}


Build now 

NOW CHECK IN DOCKERHUB ,YOU WILL FIND IMAGE .


<PUBLIC IP>:8090 

YOU WILL SEE UI OF APP
