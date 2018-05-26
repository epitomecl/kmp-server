#########################
# env
#########################
SERVER_HOME=$( cd "$( dirname "$0" )" && cd .. && cd .. && pwd )

# JVM_OPTS_JDWP="-agentlib:jdwp=transport=dt_socket,server=n,address=localhost:5005,suspend=y"
JVM_OPTS_MSIZ="-Xmx1g"
# JVM_OPTS_JAVA9="--add-modules java.xml.bind"
# JVM_OPTS_LOG="-Dlogback.debug=true -Dlogging.config=file:/$SERVER_HOME/conf/logback.xml"
# JVM_OPTS_LOG="-Dlogging.config=file:/${SERVER_HOME}/conf/logback.xml"
# JVM_OPTS_HOME="-DSERVER_HOME=${SERVER_HOME} -Djetty.home=${SERVER_HOME} -Dorg.owasp.esapi.resources=${SERVER_HOME}/conf"
# JVM_OPTS_STOP="-DSTOP_PORT=10959 -DSTOP_KEY=stop_engine"
JVM_OPTS="${JVM_OPTS_JDWP} ${JVM_OPTS_MSIZ} ${JVM_OPTS_JAVA9} ${JVM_OPTS_LOG} ${JVM_OPTS_HOME} ${JVM_OPTS_STOP}"

UBER_JAR="${SERVER_HOME}/webapps/kmp-41-multictx-2.0.0.war"

SERVER_LOG=${SERVER_HOME}/data/logs
if [ ! -d "${SERVER_LOG}" ]; then
  mkdir -p "${SERVER_LOG}"
fi


echo "SERVER_HOME=${SERVER_HOME}"
echo "JVM_OPTS=${JVM_OPTS}"
echo "UBER_JAR=${UBER_JAR}"
echo "SERVER_LOG=${SERVER_LOG}"
