def project_url = "https://github.com/D18130495/Personal_blog_server.git"

node {
    def mvnHome
    stage('Pull code from github') {
		//checkout([$class: 'GitSCM', branches: [[name: '*/main']],
		//extensions: [], userRemoteConfigs: [[url: "${project_url}"]]])
    }
    stage('Maven build code') {
		//sh "mvn -f clean install model"  
		//sh "mvn common clean install"
		//sh "mvn service clean package"
		//sh "mvn clean install -pl com.yushun:service -am"
    }
    stage('Results') {
        echo 'Remove jar'
        //sh "sudo rm /data/app/personal_blog_server/service-0.0.1-SNAPSHOT.jar"
        //echo 'Move jar to /data/app/personal_blog_server'
        //sh "sudo mv /var/lib/jenkins/workspace/personal_blog/service/target/service-0.0.1-SNAPSHOT.jar /data/app/personal_blog_server"
        //echo 'Run deploy.sh to deploy jar'
        //sh "sudo JENKINS_NODE_COOKIE=dontKillMe /data/app/personal_blog_server/sh/deploy.sh restart service-0.0.1-SNAPSHOT.jar"
    }
}