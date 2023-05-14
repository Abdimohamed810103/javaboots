pipeline {
  agent any

  stages {
      stage('Build Artifact') {
            steps {
              sh "mvn clean package -DskipTests=true"
              archive 'target/*.jar' //so that they can sdddddsdssssdbddde downssssloassssded laterssssdddhjhjaaasdsdsadasssffffsssssdsdsds
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
                  stage('SonarQube - SAST') {
                             steps {
                               sh "  mvn clean verify sonar:sonar  -Dsonar.projectKey=jenkins -Dsonar.host.url=http://ec2-3-85-238-97.compute-1.amazonaws.com:9000 -Dsonar.login=sqp_ecb976f7dcdd8a9ebe49b82646c5339416feb464"
                             }
                         }


                stage('Docker Build and Push') {
                      steps {
                        withDockerRegistry([credentialsId: "docker-hub", url: ""]) {
                          sh 'printenv'
                          sh 'docker build -t caloosha/javaboots:""$GIT_COMMIT"" .'
                          sh 'docker push caloosha/javaboots:""$GIT_COMMIT""'
                        }
                      }
                    }
                 stage('Kubernetes Deployment - DEV') {
                      steps {
                        withKubeConfig([credentialsId: 'kubeconfig']) {
                          sh "sed -i 's#replace#caloosha/javaboots:${GIT_COMMIT}#g' k8s-deployment.yaml"
                          sh "kubectl apply -f k8s-deployment.yaml"
                        }
                      }
                    }
    }
}