def call(Map params = [:] ) {
     def args = [
        NEXUS_IP : '172.31.13.99', 
     ]
     args << params
    pipeline {
        agent {
            label "${args.SLAVE_LABEL}"
        }
    
        environment {
            COMPONENT ="${args.COMPONENT}"
            NEXUS_IP = "${args.NEXUS_IP}"
            PROJECT_NAME = "${args.PROJECT_NAME}"
            SLAVE_LABEL = "${args.SLAVE_LABEL}"
            APP_TYPE    = "${args.APP_TYPE}"
        }
        stages {
            stage('Build code & install dependencies') {
                steps {
                    script {
                        build = new nexus()
                        build.code_build("${APP_TYPE}","${COMPONENT}")
                    }
                }

            }
            stage('Prepare Artifacts') {

                steps {
                    script {
                        prepare = new nexus()
                        prepare.make_artifacts("${APP_TYPE}","${COMPONENT}")
                    }
                }
            }

<<<<<<< HEAD
        }
    

        
        stage('Upload Artifacts') {
          steps {
             sh '''
<<<<<<< HEAD
               curl -f -v -u admin:DevOps321 --upload-file ${FILENAME} http://172.31.13.99:8081/repository/${COMPONENT}/${FILENAME}
            '''
            }
        }
=======
               curl -f -v -u admin:DevOps321 --upload-file ${FILENAME} http://172.31.13.99:8081/repository/${COMPONENT}/${FILENAME}"
             '''
    }
 }
>>>>>>> 2a756ad9ced766981cbdaec2b1874a7e50a75db3
=======
            stage('Upload Artifacts') {
                steps {
                    script {
                        prepare = new nexus()
                        prepare.nexus(COMPONENT)
                    }
>>>>>>> 52740540c1031ddd98e0d37ac12316574485db7b

                }
            }
        }
    }

 }