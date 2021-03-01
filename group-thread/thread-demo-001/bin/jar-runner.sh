#!/usr/bin/env bash
# 参考 Kafka 的 kafka-run-class.sh 脚本。

if [ $# -lt 1 ];
then
  echo "Usage: $0 [-daemon] [-name service_name] [-loggc] jar_name [opts]"
  exit 1
fi

base_dir=$(dirname "$0")/..

# JMX port to use
if [ -n "$JMX_PORT" ]; then
  PROJ_JMX_OPTS="$PROJ_JMX_OPTS -Dcom.sun.management.jmxremote.port=$JMX_PORT "
fi

# Log directory to use
if [ "x$LOG_DIR" = "x" ]; then
  LOG_DIR="$base_dir/logs"
  mkdir -p "$LOG_DIR"
fi

# Generic jvm settings you want to add
if [ -z "$PROJ_OPTS" ]; then
  PROJ_OPTS=""
fi

# Which java to use
if [ -z "$JAVA_HOME" ]; then
  JAVA="java"
else
  JAVA="$JAVA_HOME/bin/java"
fi

# Memory options
if [ -z "${JAR_RUN_HEAP_OPTS}" ]; then
  JAR_RUN_HEAP_OPTS="-Xmx256M"
fi

# JVM performance options
if [ -z "$PROJ_JVM_PERFORMANCE_OPTS" ]; then
  PROJ_JVM_PERFORMANCE_OPTS="-server -XX:+UseG1GC -XX:MaxGCPauseMillis=20 -XX:InitiatingHeapOccupancyPercent=35 -XX:+ExplicitGCInvokesConcurrent -Djava.awt.headless=true"
fi

DAEMON_MODE="false"
GC_LOG_ENABLED="false"
CONSOLE_OUTPUT_FILE=/dev/null

while [ $# -gt 0 ]; do
  COMMAND=$1
  case $COMMAND in
    -logout)
      console_log=$2
      CONSOLE_OUTPUT_FILE=$LOG_DIR/${console_log}.out
      shift 2
      ;;
    -loggc)
      if [ -z "$PROJ_GC_LOG_OPTS" ]; then
        GC_LOG_ENABLED="true"
        gc_log=$2
      fi
      shift 2
      ;;
    -daemon)
      DAEMON_MODE="true"
      shift
      ;;
    *)
      break
      ;;
  esac
done


# GC options
GC_FILE_SUFFIX='-gc.log'
GC_LOG_FILE_NAME=''
if [ "x$GC_LOG_ENABLED" = "xtrue" ]; then
  GC_LOG_FILE_NAME=${gc_log}$GC_FILE_SUFFIX

  # The first segment of the version number, which is '1' for releases before Java 9
  # it then becomes '9', '10', ...
  # Some examples of the first line of `java --version`:
  # 8 -> java version "1.8.0_152"
  # 9.0.4 -> java version "9.0.4"
  # 10 -> java version "10" 2018-03-20
  # 10.0.1 -> java version "10.0.1" 2018-04-17
  # We need to match to the end of the line to prevent sed from printing the characters that do not match
  mkdir -p "$LOG_DIR"
  JAVA_MAJOR_VERSION=$($JAVA -version 2>&1 | sed -E -n 's/.* version "([0-9]*).*$/\1/p')
  if [[ "$JAVA_MAJOR_VERSION" -ge "9" ]] ; then
    PROJ_GC_LOG_OPTS="-Xlog:gc*:file=$LOG_DIR/$GC_LOG_FILE_NAME:time,tags:filecount=10,filesize=102400"
  else
    PROJ_GC_LOG_OPTS="-Xloggc:$LOG_DIR/$GC_LOG_FILE_NAME -verbose:gc -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintGCTimeStamps -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=10 -XX:GCLogFileSize=100M"
  fi
fi

# Launch mode
if [ "is_$DAEMON_MODE" = "is_true" ]; then
  echo "nohup $JAVA $JAR_RUN_HEAP_OPTS $PROJ_JVM_PERFORMANCE_OPTS $PROJ_GC_LOG_OPTS $PROJ_JMX_OPTS -cp $CLASSPATH $PROJ_OPTS " "$@" " > $CONSOLE_OUTPUT_FILE 2>&1 < /dev/null & "
  nohup $JAVA $JAR_RUN_HEAP_OPTS $PROJ_JVM_PERFORMANCE_OPTS $PROJ_GC_LOG_OPTS $PROJ_JMX_OPTS -cp $CLASSPATH $PROJ_OPTS "$@" > $CONSOLE_OUTPUT_FILE 2>&1 < /dev/null &
else
  echo exec "$JAVA $JAR_RUN_HEAP_OPTS $PROJ_JVM_PERFORMANCE_OPTS $PROJ_GC_LOG_OPTS $PROJ_JMX_OPTS" -cp "$CLASSPATH $PROJ_OPTS" "$@"
  exec $JAVA $JAR_RUN_HEAP_OPTS $PROJ_JVM_PERFORMANCE_OPTS $PROJ_GC_LOG_OPTS $PROJ_JMX_OPTS -cp $CLASSPATH $PROJ_OPTS "$@"
fi