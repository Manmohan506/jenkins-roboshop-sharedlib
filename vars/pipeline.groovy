def call() {
    pipeline {
    agent any

    stages {

        stage('Prepare Artifacts') {
            steps {
                sh '''
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