node {
    def mvnHome
    stage('pull code from github') {
       checkout([$class: 'GitSCM', branches: [[name: '*/main']],
       extensions: [], userRemoteConfigs: [[url: 'https://github.com/D18130495/Personal_blog_server.git']]])
    }
    stage('Build code') {
        sh "mvn -f common clean install"
        sh "mvn -f model clean install"
        sh "mvn -f service clean package"
    }
    stage('Results') {
         echo 'deploy code'
    }
}