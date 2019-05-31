## 创建数据库
				
				
### use 命令

MongoDB 使用 `use DATABASE_NAME` 命令来创建数据库。

如果指定的数据库 DATABASE_NAME 不存在，则该命令将创建一个新的数据库，否则返回现有的数据库。

语法
`use DATABASE` 

语句的基本语法如下 

```
use DATABASE_NAME
```

### 示例

如果要创建一个名称为 `<newdb>` 的数据库，那么使用 use DATABASE 语句将如下所示：

```
> use newdb
switched to db newdb
```



要检查当前选择的数据库，请使用 db 命令

```
>db
newdb
```

如果要检查数据库列表，请使用命令：show dbs。

```
>show dbs
local     0.000025GB
test      0.00002GB
```

创建的数据库(newdb)不在列表中。要显示数据库，需要至少插入一个文档，空的数据库是不显示出来的。

```
>db.items.insert({"name":"yiibai tutorials"})
>show dbs
local     0.00005GB
test      0.00002GB
newdb      0.00002GB
```

在 MongoDB 中默认数据库是：test。 如果您还没有创建过任何数据库，则集合/文档将存储在test数据库中。


> 原文出自【易百教程】，商业转载请联系作者获得授权，非商业请保留原文链接：https://www.yiibai.com/mongodb/mongodb_create_database.html


