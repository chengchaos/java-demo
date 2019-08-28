

Spark 1.x 使用 Spark context 和 Hive Context

Spark 2.x 引入 SparkSession 


```
val sc: SparkContext
val sqlContext = new org.apache.spark.sql.SQLContext(sc)

// this is used to implicitly convert an RDD to a DataFrame.
import sqlContext.implicits._



```


    http://www.luyixian.cn/news_show_23263.aspx   