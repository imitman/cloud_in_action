pipeline {
    agent any
	environment {
	    GIT_COMMIT_HASH = sh (script: "git log -n 1 --pretty=format:'%H'", returnStdout: true)
    }
    stages {
        stage('Build') {
            steps {
                echo 'Running ${env.BUILD_ID} build on ${env.JENKINS_URL}'
                sh 'chmod +x gradlew'
                sh './gradlew bootJar'
            }
        }
        stage('Test') {
            steps {
                sh './gradlew test'
            }
            post {
                always {
                    publishHTML target : [
                        allowMissing: false,
                        alwaysLinkToLastBuild: false,
                        keepAll: true,
                        reportDir: 'build/reports/tests/test',
                        reportFiles: 'index.html',
                        reportName: 'Cloud in action tests',
                        reportTitles: 'The Report'
                    ]
                }
            }
        }
		stage('Update Docker image') {
            environment {
                DOCKER_HUB_LOGIN = credentials('docker-hub')
            }
            steps {
                sh 'docker build -t ${IMAGE_NAME}:$GIT_COMMIT_HASH .'
                sh 'docker login --username=$DOCKER_HUB_LOGIN_USR --password=$DOCKER_HUB_LOGIN_PSW'
                sh 'docker push ${IMAGE_NAME}:$GIT_COMMIT_HASH'
                sh 'docker rmi ${IMAGE_NAME}:$GIT_COMMIT_HASH'
            }
        }
        stage('Deploy') {
            steps {
                sshagent(credentials : ['cloud-ssh-key']) {
                    sh 'ssh -oStrictHostKeyChecking=accept-new ${RUNNING_HOST}'
                    sh 'docker rm -f ${CONTAINER_NAME} || true'
                    sh 'docker rmi $(docker images ${IMAGE_NAME} -q)'
                    sh 'docker run -d --name ${CONTAINER_NAME} -p 80:8080 -e DB_URL -e DB_USERNAME -e DB_PASSWORD ${IMAGE_NAME}:$GIT_COMMIT_HASH'
                    sh 'exit'
                }
            }
        }
        stage('Health check') {
            steps {
                timestamps {
                    script {
                        echo 'Checking and waiting 30 seconds for ${HEALTH_CHECK_URL} env to be available'
                        timeout(time: 30, unit: 'SECONDS') {
                            HTTP_RESPONSE = ''
                            while(!HTTP_RESPONSE == '200' ) {
                                HTTP_RESPONSE = sh (returnStdout: true, script: 'curl -s -o /dev/null -w %{http_code} ${HEALTH_CHECK_URL}')
                                sleep(time: 3, unit: 'SECONDS')
                            }
                        }
                    }
                }
            }
        }
    }
}