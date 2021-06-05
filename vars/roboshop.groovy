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

        stage('Prepare Artifacts - NGINX') {
            when {
                environment name: 'APP_TYPE', value: 'NGINX'
            }
            steps {
                sh '''
                cd static 
                zip -r ../${COMPONENT}.zip *
                '''         
            }

        }

        stage('Download Dependencies') {
            when {
                environment name: 'APP_TYPE', value: 'NODEJS'

            }
            steps {
                sh '''
                 npm install
                ''' 
            }
        }


        stage('prepare Artifacts - NODEJS') {
             when {
                environment name: 'APP_TYPE', value: 'NODEJS'

            }
            steps {
                sh '''
                zip -r ${COMPONENT}.zip node_modules server.js
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