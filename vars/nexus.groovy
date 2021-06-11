 def nexus() {
<<<<<<< HEAD
 command = "curl -f -v -u admin:DevOps321 --upload-file ${FILENAME} http://172.31.13.99:8081/repository/${COMPONENT}/${FILENAME} "
=======
 command = "curl -f -v -u admin:DevOps321 --upload-file ${FILENAME} http://172.31.13.99:8081/repository/${COMPONENT}/${FILENAME}"
>>>>>>> 2a756ad9ced766981cbdaec2b1874a7e50a75db3

 def execute_state=sh(returnStdout: true, script: command) 
}

 def make_artifacts(APP_TYPE, COMPONENT) {
    if(APP_TYPE == "NGINX") {
        command = "cd static && zip -r ../${COMPONENT}.zip *"
        def execute_com=sh(returnStdout: true, script: command)
        print execute_com

    } else if(APP_TYPE == "NODEJS") {
        command = "zip -r ${COMPONENT}.zip  server.js * "
        def execute_com=sh(returnStdout: true, script: command)
        print execute_com

    }else if(APP_TYPE == "JAVA") {
        command = "cp target/*.jar ${COMPONENT}.jar && zip -r ${COMPONENT}.zip ${COMPONENT}.jar"
        def execute_com=sh(returnStdout: true, script: command)
        print execute_com

    } else if(APP_TYPE == "PYTHON") {
     command = "zip -r ${COMPONENT}.zip payment.ini payment.py rabbitmq.py requirements.txt"
        def execute_com=sh(returnStdout: true, script: command)
        print execute_com
    }
}


def code_build(APP_TYPE, COMPONENT) {
    if(APP_TYPE == "NODEJS") {
        //command = "npm install"
        command = "env"
        def execute_com=sh(returnStdout: true, script: command)
        print execute_com

    } else if(APP_TYPE == "JAVA") {
        command = "mvn clean package"
        def execute_com=sh(returnStdout: true, script: command)
        print execute_com
    }
}