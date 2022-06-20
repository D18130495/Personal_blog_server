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
		sh "mvn clean install -pl com.yushun:service -am"
    }
    stage('Results') {
        echo 'kill old jar and remove it'
//         sh "kill -9 $(lsof -i:9001 -t)"
        sh "rm -rf /data/app/personal_blog_server"
        echo 'move jar to /data/app/personal_blog_server'
        sh "mv /var/lib/jenkins/workspace/personal_blog/service/target/service-0.0.1-SNAPSHOT.jar /data/app/personal_blog_server"
        echo 'deploy code'
    }
}