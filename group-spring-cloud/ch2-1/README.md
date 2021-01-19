


```sh

Borwser 
    - (http) -> dege-server 
    <- (http) -
    - (http) -> gateway-server 
      - (feign) -> user-server
      - (feign) -> product-server
    <- (http) -

```

## Swagger

[在 Spring Boot 项目中使用 Swagger 文档](https://developer.ibm.com/zh/languages/spring/articles/j-using-swagger-in-a-spring-boot-project/)




## Mongo

认证和授权

Authentication

认证识别，解决我是谁

Authorization

操作授权，我能做什么


```javascript

use admin;

db.createUser({
    user: "admin",
    pwd:"Admin||Password||2021",
    roles: [{
        role: "userAdminAnyDatabase", db: "admin"
    }]
})

// change mongod.conf change security:authorization: enabled
// and then, restart the MongoDB, 

use admin;
db.auth("admin", "Admin||Password||2021");
db.createUser({
    user: "chengchao",
    pwd: "Hello-World!",
    roles: [{
        role: "readWrite", db: "chengchao"
    }]
});

// 要加 readWrite 和 dbAdmin 这两个权限。
db.updateUser("chengchao", {
    roles: [
        {role: "readWrite", db: "chengchao"},
        {role: "dbAdmin", db: "chengchao"} ] } )


```

### MongoDB 的内建角色

- Read：允许用户读取指定数据库
- readWrite：允许用户读写指定数据库
- dbAdmin：允许用户在指定数据库中执行管理函数，如索引创建、删除，查看统计或访问system.profile
- userAdmin：允许用户向system.users集合写入，可以找指定数据库里创建、删除和管理用户
- clusterAdmin：只在admin数据库中可用，赋予用户所有分片和复制集相关函数的管理权限。
- readAnyDatabase：只在admin数据库中可用，赋予用户所有数据库的读权限
- readWriteAnyDatabase：只在admin数据库中可用，赋予用户所有数据库的读写权限
- userAdminAnyDatabase：只在admin数据库中可用，赋予用户所有数据库的userAdmin权限
- dbAdminAnyDatabase：只在admin数据库中可用，赋予用户所有数据库的dbAdmin权限。
- root：只在admin数据库中可用。超级账号，超级权限

参考： [https://www.jianshu.com/p/c5f778adfbb3](https://www.jianshu.com/p/c5f778adfbb3)


配置

```yaml

# 单机
spring:
  data:
    mongodb:
      authentication-database: admin   # 鉴权数据库
      database: chengchao              # 数据库名称
      field-naming-strategy:           # 使用字段名策略
      grid-fs-database:                # GridFs 数据库名称
      host: 192.168.56.102             # 服务器，不能设置为 URI
      username: chengchao
      password: Hello-World!
      port: 27017
      repositories:
        type: auto                     # 是否启用关于 JPA 规范编程
---
# 使用 uri 方式； URI 和 host 不能同时指定。
# authSource=admin&authMechanism=SCRAM-SHA-1
spring:
  data:
    mongodb:
      uri: mongodb://chengchao:Hello-World!@192.168.56.102:27017/?authSource=admin&&ssl=false
```