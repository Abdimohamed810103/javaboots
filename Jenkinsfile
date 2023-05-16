pipeline {
  agent any

  stages {
      stage('Build Artifact') {
            steps {
              sh "mvn clean package -DskipTests=true"
              archive 'target/*.jar' //so that they can sdddddsdssssdbddde downssssloassssded laterssssdddhjhjaaasdsdsadasssffffsssssdsdsdsssssssssdddd   ddd555eee   sasssddd ddd hhhhddsds hhhdfdfd
            }
        }
         stage('unit test') {
                    steps {
                      sh "mvn test"
                     }
                     post {
                             always {
                               junit 'target/surefire-reports/*.xml'
                               jacoco execPattern: 'target/jacoco.exec'
                             }
                           }
                }
                 stage('Mutation Tests - PIT') {
                      steps {
                        sh "mvn org.pitest:pitest-maven:mutationCoverage"
                      }
                      post {
                        always {
                          pitmutation mutationStatsFile: '**/target/pit-reports/**/mutations.xml'
                        }
                      }
                    }
                /*  stage('SonarQube - SAST') {
                             steps {
                               sh "mvn clean verify sonar:sonar -Dsonar.projectKey=jenkins -Dsonar.projectName='jenkins' -Dsonar.host.url=http://ec2-3-83-39-188.compute-1.amazonaws.com:9000 -Dsonar.token=sqp_9dfa5e73d24bdacc92a85dc7313fc98ad2773af4"
                             }
                         } */

                 stage('Vulnerability Scan - Docker') {
                       steps {
                         parallel(
                           "Dependency Scan": {
                             sh "mvn dependency-check:check"
                           },
                           "Trivy Scan": {
                             sh "bash trivy-docker-image-scan.sh"
                           },
                           "OPA Conftest": {
                                         sh 'docker run --rm -v $(pwd):/project openpolicyagent/conftest test --policy opa-docker-security.rego Dockerfile'
                                       }
                         )
                       }
                     }

                stage('Docker Build and Push') {
                      steps {
                        withDockerRegistry([credentialsId: "docker-hub", url: ""]) {
                          sh 'printenv'
                          sh 'sudo docker build -t caloosha/javaboots:""$GIT_COMMIT"" .'
                          sh 'docker push caloosha/javaboots:""$GIT_COMMIT""'
                        }
                      }
                    }
                 stage('Kubernetes Deployment - DEV') {
                      steps {
                        withKubeConfig([credentialsId: 'kubeconfig']) {
                          sh "sed -i 's#replace#caloosha/javaboots:${GIT_COMMIT}#g' k8s-deployment.yaml"
                          sh "kubectl apply -f k8s-deployments.yaml"
                          sh "kubectl apply -f k8s-deployment.yaml"
                        }
                      }
                    }
    }
}