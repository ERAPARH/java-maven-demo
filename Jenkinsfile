pipeline {

    agent {
        label 'linux-python'
    }

    tools {
        jdk 'JDK-21'
        maven 'Maven-3'
    }

    stages {

        stage('Checkout') {
            steps {
                echo 'Code has been checked out from GitHub'
            }
        }

        stage('Build & Test') {
            steps {
                echo 'Running Maven build and tests...'
                sh 'mvn clean test'
            }
        }

        stage('Package') {
            steps {
                echo 'Creating executable JAR...'
                sh 'mvn package'
            }
        }

        stage('Docker Build') {
            steps {
                echo 'Building Docker image...'

                sh '''
                    docker build \
                        -t eraparh/java-maven-demo:1.0 .
                '''
            }
        }

        stage('Docker Push') {
            steps {

                withCredentials([
                    usernamePassword(
                        credentialsId: 'dockerhub-creds',
                        usernameVariable: 'DOCKER_USERNAME',
                        passwordVariable: 'DOCKER_PASSWORD'
                    )
                ]) {

                    sh '''
                        echo "$DOCKER_PASSWORD" | \
                        docker login \
                        -u "$DOCKER_USERNAME" \
                        --password-stdin

                        docker push eraparh/java-maven-demo:1.0

                        docker logout
                    '''
                }
            }
        }

        stage('Archive Artifact') {
            steps {
                echo 'Archiving JAR artifact...'

                archiveArtifacts artifacts: 'target/*.jar',
                                 fingerprint: true
            }
        }
    }

    post {

        always {
            echo 'Publishing JUnit test results...'

            junit 'target/surefire-reports/*.xml'
        }

        success {
            echo 'Pipeline completed successfully!'
        }

        failure {
            echo 'Pipeline failed. Check the console logs.'
        }
    }
}
