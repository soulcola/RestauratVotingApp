pipeline {
    agent none
    stages {
        stage('Build Project') {
            agent any
            tools {
                maven 'maven' // имя, указанное в Global Tool Configuration
            }
            stages {
                stage('Build') {
                    steps {
                        sh 'mvn -B -DskipTests clean package'
                    }
                }
            }
        }
        stage('Build DockerImage') {
            agent any
            steps {
                script {
                    // Собираем Docker-образ с именем soulcola/voting-app:latest
                    dockerImage = docker.build("soulcola/voting-app:1.0.${env.BUILD_NUMBER}", ".")
                    echo "Docker Image ID: ${dockerImage.id}"
                }
            }
        }
        stage('Push on registry') {
            agent any
            steps {
                script {
                    docker.withRegistry('https://index.docker.io/v1/', 'docker-hub') {
                        dockerImage.push()
                        dockerImage.push("latest")
                    }
                }
            }
        }
        stage('Deploy image on dev cluster') {
            agent any
            steps {
                echo "Push to k8s"
                // build job: 'deploy-to-k8s'
            }
        }
    }
}
