#!/usr/bin/env bash
# 参考 Kafka 的  kafka-server-start.sh 脚本。
# $# 传递给脚本或函数的参数个数。
if [ $# -lt 1 ];
then
  echo "Usage: $0 start|stop|run"
  exit 1
fi

base_dir=$(dirname "$0")

service_name='thread-demo-001'
jar_file_path="${base_dir}/../target/${service_name}.jar"
server_port=''
server_port='6801'
logging_file=''
logging_file='target/logs/my.log'

EXTRA_ARGS="-name ${service_name} -loggc -jar ${jar_file_path}"

if [ -n "${server_port}" ]; then
  EXTRA_ARGS="${EXTRA_ARGS} --server.port=${server_port}"
fi

if [ -n "${logging_file}" ]; then
  EXTRA_ARGS="${EXTRA_ARGS} --logging.file=${logging_file}"
fi

if [ "x$JAR_RUNNER_HEAP_OPTS" = "x" ]; then
  export JAR_RUNNER_HEAP_OPTS="-Xmx1G -Xms1G"
fi
if [ -z "$PROJ_OPTS" ]; then
  PROJ_OPTS=""
fi

COMMAND=$1
case $COMMAND in
  start)
    EXTRA_ARGS="-daemon "$EXTRA_ARGS
    shift
    ;;
  run)
    shift
    ;;
  stop)
    running_pid=$(jps -ml | grep "$jar_file_path" | awk '{print $1}')
    echo "running_pid => $running_pid"
    kill -15 "$running_pid"
    exit 0
    ;;
  *)
    exit 2
    ;;
esac

# $EXTRA_ARGS 这个变量不能用引号包起来，否则将会作为一个参数传递给后面的脚本。
# echo exec "${base_dir}"/jar-runner.sh $EXTRA_ARGS "${@}"
echo "${base_dir}"/jar-runner.sh $EXTRA_ARGS "${@}"
# exec "${base_dir}"/jar-runner.sh $EXTRA_ARGS "${@}"

