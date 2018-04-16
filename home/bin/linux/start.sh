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
CMD_OPTS="-httpPort 9999 -httpsPort 9443"

cmd="nohup java ${JVM_OPTS} -jar "'"'"${UBER_JAR}"'"'" ${CMD_OPTS} 2>&1 | tee ${SERVER_LOG}/${LOGFILE} &"
echo ${cmd}
eval ${cmd}
