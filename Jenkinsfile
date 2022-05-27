node {
    def mvnHome
    stage('pull code from github') {
       checkout([$class: 'GitSCM', branches: [[name: '*/main']],
       extensions: [], userRemoteConfigs: [[url: 'https://github.com/D18130495/Personal_blog_server.git']]])
    }
    stage('Build') {
        echo 'build code'
    }
    stage('Results') {
         echo 'deploy code'
    }
}