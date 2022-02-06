def call(){
  pipeline {
      agent any
      environment {
          NEXUS_USER         = credentials('NEXUS-USER')
          NEXUS_PASSWORD     = credentials('NEXUS-PASS')
          def gradle = load('gradle.groovy')
      }
      parameters {
            choice choices: ['maven', 'gradle'], description: 'Seleccione una herramienta para preceder a compilar', name: 'compileTool'
            text description: 'Enviar los stages separados por ";"... Vacío si necesita todos los stages', name: 'stages'
      }
      stages {
          stage("Pipeline"){
              steps {
                  script{
                      sh "env"
                      env.TAREA = ""
                      if(params.compileTool == 'maven'){
                        maven.call(params.stages);
                      }else{
                        gradle.call(params.stages)
                      }
                  }
              }
              post{
          success{
            slackSend color: 'good', message: "[FGO] [${JOB_NAME}] [${BUILD_TAG}] Ejecucion Exitosa", teamDomain: 'dipdevopsusac-tr94431'
          }
          failure{
            slackSend color: 'danger', message: "[FGO] [${env.JOB_NAME}] [${BUILD_TAG}] Ejecucion fallida en stage [${env.TAREA}]", teamDomain: 'dipdevopsusac-tr94431'
          }
        }
          }
      }
  }
}
return this;
