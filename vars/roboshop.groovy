def call(Map params = [:]) {
    def args = [
        NEXUS_IP              : '172.31.13.99',
    ]
    args << params

    pipeline {
    agent {
        label "${args.SLAVE_LABEL}"
    }

    environment {
        COMPONENT    =   "${args.COMPONENT}"
        NEXUS_IP     =   "${args.NEXUS_IP}"
        PROJECT_NAME =   "${args.PROJECT_NAME}"
        SLAVE_LABEL  =   "${args.SLAVE_LABEL}"
    }

    stages {

        stage('Prepare Artifacts') {
            steps {
                sh '''
                echo ${COMPONENT}
                cd static 
                zip -r ../${COMPONENT}.zip *
                '''         
            }

        }
        stage('Upload Artifacts') {
          steps {
             sh '''
               curl -f -v -u admin:DevOps321 --upload-file frontend.zip http://${NEXUS_IP}:8081/repository/frontend-1/frontend.zip
             '''
    }
}

    }
}


}