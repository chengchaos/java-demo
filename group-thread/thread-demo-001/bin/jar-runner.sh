#!/usr/bin/env bash

JAR_FOLDER="../target"
JAR_FILE='thread-demo-001.jar'
JAVA_OPTS="-server -Xms1024m -Xmx1024m \
  -Djava.security.egd=file:///dev/./urandom"

# https://www.codenong.com/6168781/
case "$1" in
  -d|--daemon)
    $0 < /dev/null &> /dev/null & disown
    exit 0
    ;;
  *)
    ;;
esac

# https://blog.csdn.net/10km/article/details/51906821
SHELL_FOLDER=$(cd "$(dirname "$0")" || exit 1;pwd)
#echo "SHELL_FOLDER => $SHELL_FOLDER"
SHELL_FOLDER=$(dirname "$(readlink -f "$0")")
#echo "SHELL_FOLDER => $SHELL_FOLDER"

cd "$SHELL_FOLDER" || exit 1
#pwd


#nohup java -jar jlr-wmp-poi2car-1.0-SNAPSHOT.jar --server.port=6801 --server.context-path=/jlr-wmp-poi2car > 6801.log 2>&1 &
#nohup
RUN_COMMAND="java ${JAVA_OPTS}"
RUN_COMMAND="$RUN_COMMAND -jar ${JAR_FOLDER}/${JAR_FILE}"
RUN_COMMAND="$RUN_COMMAND --server.port=6801 "
#RUN_COMMAND="$RUN_COMMAND --logging.path=logs/ "
RUN_COMMAND="$RUN_COMMAND --logging.file=logs/td01.log "

echo "RUN_COMMAND => $RUN_COMMAND"
$RUN_COMMAND
echo "*** end ***"





