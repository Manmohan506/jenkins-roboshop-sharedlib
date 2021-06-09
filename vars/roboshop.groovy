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
        APP_TYPE     =   "${args.APP_TYPE}"
    }

    stages {

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

        stage('Compile Code') {
             when {
                environment name: 'APP_TYPE', value: 'JAVA'

            }
            steps {
                sh '''
                mvn compile
               '''
            }
        }

        stage('Make Package') {
             when {
                environment name: 'APP_TYPE', value: 'JAVA'

            }
            steps {
                sh '''
                mvn package
               '''
            }
        }
      

         stage('Prepare Artifacts') {
            steps {
                script {
                    prepare = new nexus()
                    prepare.make_artifacts ("${APP_TYPE}", "${COMPONENT}")
                }
                sh '''
                ls
                '''         
            }

        }
    

        
        stage('Upload Artifacts') {
          steps {
             sh '''
               curl -f -v -u admin:DevOps321 --upload-file frontend-1.zip http://${NEXUS_IP}:8081/repository/frontend-1/frontend-1.zip
             '''
    }
 }

    }
}


}