def project_url = "https://github.com/D18130495/Personal_blog_server.git"

node {
    def mvnHome
    stage('Pull code from github') {
		checkout([$class: 'GitSCM', branches: [[name: '*/main']],
		extensions: [], userRemoteConfigs: [[url: "${project_url}"]]])
    }
    stage('Maven build code') {
		//sh "mvn -f clean install model"  
		//sh "mvn common clean install"
		//sh "mvn service clean package"
		mvn clean install -pl service -am
    }
    stage('Results') {
         echo 'deploy code'
    }
}