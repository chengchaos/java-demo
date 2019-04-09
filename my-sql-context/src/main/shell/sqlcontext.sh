#!/usr/bin/env bash
bin/spark-submit \
    --class vip.chengchao.SqlContextApp \
    --name SQLContextDemo \
    --master local[2] \
    /home/chengchao/Uploads/my-sql-context-1.0-SNAPSHOT.jar \
    /home/chengchao/Uploads/people.json
