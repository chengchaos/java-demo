
## 目的

从 MongoDB 中读取某一个 tbox 上传上来的原始报文, 经过稍加改造(修改 vin 和 iccid) 作为新的报完转发给 netty 的 server 端.

目的是用于测试(没有真 tbox 的时候)

```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v2.1.4.RELEASE)
```


## 实现

1) MongoDB 使用 Spring-boot 自带的库:

参考: [https://www.jb51.net/article/113253.htm](https://www.jb51.net/article/113253.htm)


1.1) pom.xml 文件中添加依赖:

```
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-mongodb</artifactId>
            <version>2.0.6.RELEASE</version>
        </dependency>
```

1.2) yml 文件中添加配置:

```
## 单机模式:
## spring.data.mongodb.uri=mongodb://name:pass@localhost:27017/test
## 集群模式:
## spring.data.mongodb.uri=mongodb://user:pwd@ip1:port1,ip2:port2/database

spring:
  data:
    mongodb:
      port: 27018
      host: 40.73.24.131
      database: sinogold
```

1.3) 创建实体类

其中, 原始报文数据保存在: `sinogold`.`SAL_PacketMessage` 中. 数据格式类似:

```
{
    "_id" : ObjectId("5afa8ce5053af50cf077382a"),
    "_class" : "com.sinogold.activelink.entity.mongo.PacketMessage",
    "deviceId" : "10016",
    "code" : "FMT-CB00000000016",
    "uploadTime" : NumberLong(20180515153148),
    "serverTime" : NumberLong(20180515153148),
    "hexDump" : "23230201464d542d4342303030303030303030313601007412050f0f1f30010201030238000004b501c529062d01244e2002020101f0d6d8fffbdc00ccffff0101f0d6d8fffbdc00ccffff0300cd27104e410005344a3f606b08a00c4e20c8037812ff040261a89c72050006f1afd00260cadc060101177002021a900203dcd2c8dc0703000356dfffffffff86"
}
```