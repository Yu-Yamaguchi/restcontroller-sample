#!/bin/bash
LOGDIR="/usr/local/spring/shlog"
LOG="${LOGDIR}/stdout.log"
ERROR_LOG="${LOGDIR}/stderr.log"
RUN_DIR="/usr/local/spring/run"
PID_FILE="${RUN_DIR}/spring.pid"

[ -d $RUN_DIR ] || mkdir -p ${RUN_DIR}
/usr/bin/java -Xms512M -Xmx1G -jar /usr/local/spring/restcontroller-sample-1.0.0.jar \
    --spring.profiles.active=staging 
    > $LOG 2>$ERROR_LOG &
echo $! > ${PID_FILE}
