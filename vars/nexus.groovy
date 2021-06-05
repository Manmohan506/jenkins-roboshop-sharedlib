def nexus() {
 command = "curl -f -v -u admin:DevOps321 --upload-file cart.zip http://172.31.13.99:8081/repository/cart/cart.zip"

def execute_state=sh(returnStdout: true, script: command)
}

def demos(n1) {
    print 'Demo'
}