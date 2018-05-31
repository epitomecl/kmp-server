<#
powershell Set-ExecutionPolicy -Scope CurrentUser Unrestricted
# powershell Set-ExecutionPolicy -Scope CurrentUser RemoteSigned -Force
powershell .\bin\windows\start.ps1
#>

#########################
# env
#########################
$EPITOMECL_KMP_HOME=(get-item (split-path -parent $MyInvocation.MyCommand.Definition)).parent.parent.FullName
. "${EPITOMECL_KMP_HOME}\bin\windows\env.ps1"

#########################
# start
#########################
$LOGFILE="start.log"
$CMD_OPTS="--spring.profiles.active=prod"

$cmd="java ${JVM_OPTS} -jar ${UBER_JAR} ${CMD_OPTS} 2>&1 | Tee-Object ${SERVER_LOG}/${LOGFILE}"
Write-Host ${cmd} -ForegroundColor Green
Invoke-Expression "${cmd}"
