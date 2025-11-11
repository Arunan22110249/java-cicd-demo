pipeline {
    agent any

    environment {
        AZURE_CREDENTIALS = credentials('azure-credentials-id')
        ACR_NAME = "jenkinsacr687827139"
        WEBAPP_NAME = "jenkins-webapp-demo"
        RESOURCE_GROUP = "jenkins-rg"
        REGION = "centralindia"
        IMAGE_NAME = "java-cicd-demo"
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out code...'
                checkout scm
            }
        }

        stage('Build Maven Project') {
            steps {
                echo 'Building Java project using Maven...'
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    echo 'Building Docker image...'
                    def imageTag = "${ACR_NAME}.azurecr.io/${IMAGE_NAME}:${BUILD_NUMBER}"
                    sh "docker build -t ${imageTag} ."
                }
            }
        }

        stage('Login to Azure Container Registry') {
            steps {
                script {
                    echo 'Logging into Azure Container Registry...'
                    sh """
                        az login --service-principal \
                            -u ${AZURE_CREDENTIALS_USR} \
                            -p ${AZURE_CREDENTIALS_PSW} \
                            --tenant 5beb351c-3fb8-418f-b612-fe36ace96ef3

                        az acr login --name ${ACR_NAME}
                    """
                }
            }
        }

        stage('Push Docker Image to ACR') {
            steps {
                script {
                    echo 'Pushing image to ACR...'
                    def imageTag = "${ACR_NAME}.azurecr.io/${IMAGE_NAME}:${BUILD_NUMBER}"
                    sh "docker push ${imageTag}"
                }
            }
        }

        stage('Deploy to Azure Web App') {
            steps {
                script {
                    echo 'Deploying to Azure Web App...'
                    def imageTag = "${ACR_NAME}.azurecr.io/${IMAGE_NAME}:${BUILD_NUMBER}"
                    def registryUrl = "https://${ACR_NAME}.azurecr.io"
                    sh """
                        az webapp config container set \
                            --name ${WEBAPP_NAME} \
                            --resource-group ${RESOURCE_GROUP} \
                            --docker-custom-image-name ${imageTag} \
                            --docker-registry-server-url ${registryUrl}
                    """
                }
            }
        }
    }

    post {
        success {
            echo "✅ Deployment successful!"
            echo "Application running at: https://${WEBAPP_NAME}.azurewebsites.net"
        }
        failure {
            echo "❌ Pipeline failed. Check Jenkins console logs for details."
        }
    }
}
