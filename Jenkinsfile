pipeline {
    agent none
    environment {
        // Определяем переменную CONTAINER_TAG, содержащую тег образа
        CONTAINER_TAG = "1.0.${env.BUILD_NUMBER}"
    }
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
                    // Сборка Docker-образа с тегом, содержащим номер сборки
                    dockerImage = docker.build("soulcola/voting-app:${CONTAINER_TAG}", ".")
                    echo "Docker Image ID: ${dockerImage.id}"
                }
            }
        }
        stage('Push on registry') {
            agent any
            steps {
                script {
                    // Публикация образа в Docker Hub
                    docker.withRegistry('https://index.docker.io/v1/', 'docker-hub') {
                        dockerImage.push()
                        dockerImage.push("latest")
                    }
                }
            }
        }
        stage('Trigger ManifestUpdate') {
            agent any
            steps {
                echo "Triggering updatemanifest job"
                // Передача параметра DOCKERTAG, равного номеру сборки (BUILD_NUMBER)
                build job: 'voting-app-k8s', parameters: [string(name: 'DOCKERTAG', value: env.CONTAINER_TAG)]
            }
        }
    }
}
