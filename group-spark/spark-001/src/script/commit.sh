#!/usr/bin/env bash

MASTER_TYPE='local[2]'
APP_NAME='MyApp'
MAIN_CLASS='cn.chengchaos.SqlContextApp'
JAR_PATH='/home/chengchao/lib/my-scala-01.jar'

spark-submit --name ${APP_NAME} \
  --master ${MASTER_TYPE} \
  --class ${MAIN_CLASS}  \
  ${JAR_PATH} \
  file:///home/chengchao/files/people.json