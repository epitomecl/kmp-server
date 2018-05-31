<#
powershell Set-ExecutionPolicy -Scope CurrentUser Unrestricted
# powershell Set-ExecutionPolicy -Scope CurrentUser RemoteSigned -Force
powershell .\bin\windows\stop.ps1
#>

#########################
# env
#########################
$EPITOMECL_KMP_HOME=(get-item (split-path -parent $MyInvocation.MyCommand.Definition)).parent.parent.FullName
. "${EPITOMECL_KMP_HOME}\bin\windows\env.ps1"

#########################
# stop
#########################
$LOGFILE="stop.log"
$CMD_OPTS="-stopPort 9999"

$cmd="java ${JVM_OPTS} -jar ${UBER_JAR} ${CMD_OPTS} 2>&1 | Tee-Object ${SERVER_LOG}/${LOGFILE}"
Write-Host ${cmd} -ForegroundColor Green
Invoke-Expression "${cmd}"
