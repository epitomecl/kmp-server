<#
 powershell
 Set-ExecutionPolicy -Scope CurrentUser Unrestricted
# Set-ExecutionPolicy -Scope CurrentUser RemoteSigned -Force
 .\bin\mint.ps1
#>

(Get-Host).Version

#########################
# env
#########################
$EPITOMECL_KMP_HOME=(get-item (split-path -parent $MyInvocation.MyCommand.Definition)).parent.parent.FullName
. "${EPITOMECL_KMP_HOME}\bin\windows\env.ps1"

#########################
# init
#########################
$LOGFILE="init.log"
$CMD_OPTS="-init"

$cmd="java ${JVM_OPTS} -jar ${UBER_JAR} ${CMD_OPTS} 2>&1 | Tee-Object ${SERVER_LOG}/${LOGFILE}"
Write-Host ${cmd} -ForegroundColor Green
Invoke-Expression "${cmd}"
