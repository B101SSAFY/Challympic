pipeline {
    agent any

    tools {
        nodejs "nodejs:14.17.6"
    }

    stages {
        stage('npm install') {
            steps {
                dir('./FRONTEND') {
                    sh 'npm install'
                }
            }
        }
        stage('npm build') {
            steps {
                dir('./FRONTEND') {
                    sh 'npm run build'
                }
            }
        }
        stage('build nginx image') {
            steps {
                sh 'docker build -t challympic/nginx ./FRONTEND'
            }
        }
        stage('nginx deploy') {
            steps {
                sh 'docker rm -f challympic-nginx'
                sh 'docker run --network challympic -d --name challympic-nginx -p 7770:80 -v /etc/letsencrypt:/etc/letsencrypt -u root challympic/nginx'
            }
        }
        stage('build springboot') {
            steps {
                dir('./BACKEND') {
                    sh 'chmod +x gradlew'
                    sh './gradlew build'
                }
            }
        }
        stage('build springboot image') {
            steps {
                sh 'docker build -t challympic/springboot ./BACKEND'
            }
        }
        stage('springboot deploy') {
            steps {
                sh 'docker rm -f challympic-springboot'
                sh 'docker run --network challympic -d --name challympic-springboot -p 8880:8880 -u root challympic/springboot'
            }
        }
        stage('finish') {
            steps {
                sh 'docker restart challympic-nginx'
                sh 'docker images -qf dangling=true | xargs -I{} docker rmi {}'
            }
        }
    }
}