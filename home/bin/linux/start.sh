#!/bin/sh

#########################
# env
#########################
SERVER_HOME=$( cd "$( dirname "$0" )" && cd .. && cd .. && pwd )
. ${SERVER_HOME}/bin/linux/env.sh

#########################
# start
#########################
LOGFILE="start.log"
CMD_OPTS="--server.port=9080"

cmd="nohup java ${JVM_OPTS} -jar "'"'"${UBER_JAR}"'"'" ${CMD_OPTS} 2>&1 | tee ${SERVER_LOG}/${LOGFILE} &"
echo ${cmd}
eval ${cmd}
