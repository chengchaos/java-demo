#!/usr/bin/env bash
# 参考 Kafka 的  kafka-server-start.sh 脚本。
# $# 传递给脚本或函数的参数个数。
if [ $# -lt 1 ];
then
  echo "Usage: $0 start|stop|run"
  exit 1
fi

base_dir=$(dirname "$0")

service_name='thread-demo-001'     # 可以设置为 Jar 文件名相同。
console_out=''
gc_log=''
server_port=''
logging_file=''
EXTRA_ARGS=''

# 使用相当脚本的路径或者绝对路径。
jar_file_path="${base_dir}/../target/${service_name}.jar"

if [ ! -f "${jar_file_path}" ]; then
  echo "404 File Not Found! ... [ ${jar_file_path} ]"
  exit 1
fi

console_out="${service_name}"     # 不为空则输出控制台信息到 logs/${service_name}.out
gc_log="${service_name}"          # 不为空则将 GC log 输出到 logs/${gc_log}-gc.log
server_port='6801'                # 指定 spring boot 的运行端口
logging_file='target/logs/my.log' # 指定 spring boot 日志输出文件

if [ -n "${console_out}" ]; then
  EXTRA_ARGS="${EXTRA_ARGS} -logout ${console_out}"
fi

if [ -n "${gc_log}" ]; then
  EXTRA_ARGS="${EXTRA_ARGS} -loggc ${gc_log}"
fi

EXTRA_ARGS="${EXTRA_ARGS} -jar ${jar_file_path}"

if [ -n "${server_port}" ]; then
  EXTRA_ARGS="${EXTRA_ARGS} --server.port=${server_port}"
fi

if [ -n "${logging_file}" ]; then
  EXTRA_ARGS="${EXTRA_ARGS} --logging.file=${logging_file}"
fi

if [ "x$JAR_RUN_HEAP_OPTS" = "x" ]; then
  export JAR_RUN_HEAP_OPTS="-Xmx1G -Xms1G"
fi

if [ -z "$PROJ_OPTS" ]; then
  PROJ_OPTS="-Djava.security.egd=file:/dev/./urandom"
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
echo "exec ${base_dir}/jar-runner.sh" $EXTRA_ARGS "${@}"
exec "${base_dir}/jar-runner.sh" $EXTRA_ARGS "${@}"

