<#
Set-ExecutionPolicy -Scope CurrentUser Unrestricted
# Set-ExecutionPolicy -Scope CurrentUser RemoteSigned -Force
Get-ExecutionPolicy
.\bin\windows\env.ps1
#>

#########################
# env
#########################
$EPITOMECL_KMP_HOME=(get-item (split-path -parent $MyInvocation.MyCommand.Definition)).parent.parent.FullName

# $JVM_OPTS_JDWP="-agentlib:jdwp=transport=dt_socket,server=n,address=localhost:5005,suspend=y"
$JVM_OPTS_MSIZ="-Xmx1g"
# $JVM_OPTS_JAVA9="--add-modules java.xml.bind"
# $JVM_OPTS_LOG="-D""logback.debug""=true -D""logging.config""=file:/$EPITOMECL_KMP_HOME/conf/logback.xml"
# $JVM_OPTS_LOG="-D""logging.config""=file:/$EPITOMECL_KMP_HOME/conf/logback.xml"
# $JVM_OPTS_HOME="-DEPITOMECL_KMP_HOME=${EPITOMECL_KMP_HOME} -D""user.language""=en -D""org.owasp.esapi.resources""=${EPITOMECL_KMP_HOME}/conf"
# $JVM_OPTS_STOP="-DSTOP_PORT=10959 -DSTOP_KEY=stop_engine"
$JVM_OPTS="${JVM_OPTS_JDWP} ${JVM_OPTS_MSIZ} ${JVM_OPTS_JAVA9} ${JVM_OPTS_LOG} ${JVM_OPTS_HOME} ${JVM_OPTS_STOP}"

$UBER_JAR="${EPITOMECL_KMP_HOME}/webapps/worker-41-webapp-0.0.1-SNAPSHOT.war"

$SERVER_LOG="${EPITOMECL_KMP_HOME}/data/logs"
if ( !( Test-Path "${SERVER_LOG}" ) )
{
  New-Item -ItemType directory -Path "${SERVER_LOG}" | Out-Null
}

echo "EPITOMECL_KMP_HOME=$EPITOMECL_KMP_HOME"
echo "JVM_OPTS=$JVM_OPTS"
echo "UBER_JAR=$UBER_JAR"
echo "SERVER_LOG=$SERVER_LOG"
