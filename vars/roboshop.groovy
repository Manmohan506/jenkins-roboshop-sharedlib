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

        stage('Prepare Artifacts - NGINX') {
            when {
                environment name: 'APP_TYPE', value: 'NGINX'
            }
            steps {
                script {
                    thing = new nexus()
                    thing.demos 'new'
                }
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
        stage('prepare Artifacts - JAVA') {
             when {
                environment name: 'APP_TYPE', value: 'JAVA'

            }
            steps {
                sh '''
                  cp target/*.jar ${COMPONENT}.jar
                  zip -r ${COMPONENT}.zip ${COMPONENT}.jar
               '''
            }


        }

          stage('prepare Artifacts - PYTHON') {
               when {
                environment name: 'APP_TYPE', value: 'PYTHON'

            }
            steps {
                sh '''
                  zip -r ${COMPONENT}.zip payment.ini payment.py rabbitmq.py requirements.txt
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