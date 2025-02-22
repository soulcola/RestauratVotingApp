pipeline {
    agent none
    stages {
        stage('Build') {
            agent { docker 'maven:3.9-eclipse-temurin-23-alpine }
            steps {
                echo 'Hello, Maven'
                sh 'mvn -B -DskipTests clean package'
            }
        }
        stage('Run') {
            agent any
            steps {
                sh 'docker-compose up --detach'
            }
        }
    }
}