#!/bin/sh

#########################
# env
#########################
SERVER_HOME=$( cd "$( dirname "$0" )" && cd .. && cd .. && pwd )
. ${SERVER_HOME}/bin/linux/env.sh

#########################
# init
#########################
LOGFILE="init.log"
CMD_OPTS="-init"

cmd="java ${JVM_OPTS} -jar "'"'"${UBER_JAR}"'"'" ${CMD_OPTS} 2>&1 | tee ${SERVER_LOG}/${LOGFILE}"
echo ${cmd}
eval ${cmd}
