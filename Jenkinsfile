
pipeline {
    agent any
    environment {
        DOCKER_PASSWORD = credentials("docker_password")
        GITHUB_TOKEN = credentials("github_token")
    }

    stages {
        stage('Build & Test') {
            steps {
                echo 'Running clean and build...'
                sh './gradlew clean build'
            }
        }
        stage('Tag image') {
              steps {
                script {
                       sh([script: 'git fetch --tag', returnStdout: true]).trim()
                       env.MAJOR_VERSION = sh([script: 'git tag | sort --version-sort | tail -1 | cut -d . -f 1', returnStdout: true]).trim()
                       env.MINOR_VERSION = sh([script: 'git tag | sort --version-sort | tail -1 | cut -d . -f 2', returnStdout: true]).trim()
                       env.PATCH_VERSION = sh([script: 'git tag | sort --version-sort | tail -1 | cut -d . -f 3', returnStdout: true]).trim()
                       env.IMAGE_TAG = "${env.MAJOR_VERSION}.\$((${env.MINOR_VERSION} + 1)).${env.PATCH_VERSION}"
                }

                echo 'Tagging docker image with new version: ${env.IMAGE_TAG}'

                sh "docker login -u alexandrudumeadumea -p $DOCKER_PASSWORD"
                sh "docker build -t alexandrudumeadumea/hello-img:${env.IMAGE_TAG} ."
                sh "docker push alexandrudumeadumea/hello-img:${env.IMAGE_TAG}"

                sh "git tag ${env.IMAGE_TAG}"
                sh "git push https://$GITHUB_TOKEN@github.com/TBD-Prod-Eng/service.git ${env.IMAGE_TAG}"
              }
        }
    }
}