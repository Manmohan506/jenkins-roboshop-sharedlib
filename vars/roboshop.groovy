def call(String COMPONENT) {
    pipeline {
    agent any

    environment {
        COMPONENT = COMPONENT
    }

    stages {

        stage('Prepare Artifacts') {
            steps {
                sh '''
                echo ${COMPONENT}
                cd static 
                zip -r ../frontend.zip *
                '''         
            }

        }
        stage('Upload Artifacts') {
          steps {
             sh '''
               curl -f -v -u admin:DevOps321 --upload-file frontend.zip http://172.31.13.99:8081/repository/frontend-1/frontend.zip
             '''
    }
}

    }
}


}