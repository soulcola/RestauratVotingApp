pipeline {
    agent none
    stages {
        stage('Build') {
            agent { docker 'maven:3.8.5-openjdk-17' }
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