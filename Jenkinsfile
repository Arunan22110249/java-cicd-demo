pipeline {
    agent any

    environment {
        // Azure environment variables (use Jenkins credentials, not plaintext!)
        AZURE_CREDENTIALS = credentials('azure-credentials-id')   // Jenkins → Manage Credentials
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
                    sh """
                        docker build -t ${ACR_NAME + '.azurecr.io/' + IMAGE_NAME + ':' + BUILD_NUMBER} .
                    """
                }
            }
        }

        stage('Login to Azure Container Registry') {
            steps {
                script {
                    echo 'Logging into Azure Container Registry...'
                    sh """
                        az login --service-principal -u ${AZURE_CREDENTIALS_USR} -p ${AZURE_CREDENTIALS_PSW} --tenant 5beb351c-3fb8-418f-b612-fe36ace96ef3
                        az acr login --name ${ACR_NAME}
                    """
                }
            }
        }

        stage('Push Docker Image to ACR') {
            steps {
                script {
                    echo 'Pushing image to ACR...'
                    sh """
                        docker push ${ACR_NAME + '.azurecr.io/' + IMAGE_NAME + ':' + BUILD_NUMBER}
                    """
                }
            }
        }

        stage('Deploy to Azure Web App') {
            steps {
                script {
                    echo 'Deploying to Azure Web App...'
                    sh """
                        az webapp config container set \
                            --name ${WEBAPP_NAME} \
                            --resource-group ${RESOURCE_GROUP} \
                            --docker-custom-image-name ${ACR_NAME + '.azurecr.io/' + IMAGE_NAME + ':' + BUILD_NUMBER} \
                            --docker-registry-server-url ${'https://' + ACR_NAME + '.azurecr.io'}
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
