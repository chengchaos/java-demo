

```bash

spark-submit --class cn.chengchaos.spark.sql.sqlctx.SQLContextApp \
--master spark://hadoop:7077 \
./my-spark-demo.jar \
./people.json 


spark-submit --class cn.chengchaos.spark.sql.sqlctx.HiveContextApp \
--master spark://hadoop:7077 \
--jars /works/apps/hive/lib/mysql-connector-java-8.0.17.jar \
./my-spark-demo.jar


```