#!groovy
node {
    properties([
            parameters([
                string(
                        name: 'Branch',
                        defaultValue: 'master',
                        description: '发布分支或版本'
                )
            ])
    ])

    stage('Checking Parameter') {
        /**
         * 参数校验
         */

        if (!(params.Branch.trim())) {
            error "请输入需要发布的分支或标签"
        }

    }
    stage('checkout') {
        git credentialsId: '8e7a3e79-bb97-40f4-9e2a-60d124a07296', url: 'git@gitlab.vchangyi.com:java/spring-cloud-base-server.git', branch: params.Branch
    }
    stage('test') {
        sh './gradlew test'
    }
    stage('build') {
        echo 'Skipped. publish image depend build.'
    }
    stage('publish image') {
        sh "docker login -u ${DOCKER_USER} -p ${DOCKER_PWD} ${DOCKER_REPO}"
        sh "./gradlew clean pushDocker"
    }
    stage('clean image') {
        sh "./gradlew cleanDocker"
    }
}