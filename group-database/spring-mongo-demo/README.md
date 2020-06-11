# Spring Boot 中使用 MongoDB

https://www.cnblogs.com/ityouknow/p/6828919.html

## 起步

**pom.xml**

```xml
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-mongodb</artifactId>
		</dependency>
```


## 查询

**精确查询 is**

```java
/*
Object some() {
    Criteria criteria = new Criteria("Name");
    criteria.is(name);
    Query query = new Query(criteria);
    return mongoTemplate.find(query, SomeBean.class, StorageConstant.STORAGE_COLLECTION_NAME);
}
*/
```

**模糊查询 regex**


```java
/*
<pre>
Pattern pattern = Pattern.compile("^.*hzb.*$", Pattern.CASE_INSENSITIVE);

query.with(new Sort(Sort.Direction.DESC, "createTime"));

// 多个时间条件。
Query query = new Query(
	 Criteria.where("ip").is(ip)
	 .andOperator(
		Criteria.where("createdDate").lt(endDate),
		Criteria.where("createdDate").gte(startDate)));




public List<PageUrl> getPageUrlsByUrl(int begin, int end,String linkUrlid) {          
    Query query = new Query();  
    query.addCriteria(Criteria.where("linkUrl.id").is(linkUrlid));  
    return find(query.limit(end - begin).skip(begin), PageUrl.class);          
}  

// 查询字段不存在的数据

public List<GoodsDetail> getGoodsDetails2(int begin, int end) {  
    Query query = new Query();  
    query.addCriteria(Criteria.where("goodsSummary").not());  
    return find(query.limit(end - begin).skip(begin),GoodsDetail.class);  
}

// 查询字段不为空的数据

Criteria.where("key1").ne("").ne(null)  

</pre>
*/
```

https://www.cnblogs.com/powerwu/articles/9192465.html

https://www.cnblogs.com/sa-dan/p/6836055.html