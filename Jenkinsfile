pipeline {
  agent any
  environment {
     deploymentName = "devsecops"
     containerName = "devsecops-container"
     serviceName = "devsecops-svc"
     imageName = "caloosha/javaboots:${GIT_COMMIT}"
     applicationURL = "http://ec2-3-89-74-85.compute-1.amazonaws.com"
     applicationURI = "api/v1"
   }
  stages {
      stage('Build Artifact') {
            steps {
              sh "mvn clean package -DskipTests=true"
              archive 'target/*.jar' //so that they can sdddddsdssssgggdbddde downssssloassssded laterssssdddhjhjaaasdsdsadasssffffsssssdsdsdsssssssssdddd   ddd555eee   sasssddd ddd hhhhddsds hhhdfdfd
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
                         } cc  */

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
                     stage('Integration Tests - DEV') {
                          steps {
                            script {
                              try {
                                withKubeConfig([credentialsId: 'kubeconfig']) {
                                  sh "bash integration-test.sh"
                                }
                              } catch (e) {
                                withKubeConfig([credentialsId: 'kubeconfig']) {
                                  sh "kubectl -n default rollout undo deploy ${deploymentName}"
                                }
                                throw e
                              }
                            }
                          }
                        }
                         stage('Prompte to PROD?') {
                              steps {
                                timeout(time: 2, unit: 'DAYS') {
                                  input 'Do you want to Approve the Deployment to Production Environment/Namespace?'
                                }
                              }
                            }
                          stage('K8S CIS Benchmark') {
                               steps {
                                 script {

                                   parallel(
                                     "Master": {
                                       sh "bash cis-master.sh"
                                     },
                                     "Etcd": {
                                       sh "bash cis-etcd.sh"
                                     },
                                     "Kubelet": {
                                       sh "bash cis-kubelet.sh"
                                     }
                                   )

                                 }
                               }
                             }
    }
}