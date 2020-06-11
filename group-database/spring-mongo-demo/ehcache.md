## 

https://blog.csdn.net/sinat_24535471/article/details/87832539

**pom.xml**

```xml

		<!-- https://mvnrepository.com/artifact/org.ehcache/ehcache -->
		<dependency>
			<groupId>org.ehcache</groupId>
			<artifactId>ehcache</artifactId>
			<version>3.8.1</version>
		</dependency>

		<!-- https://mvnrepository.com/artifact/javax.cache/cache-api -->
		<dependency>
			<groupId>javax.cache</groupId>
			<artifactId>cache-api</artifactId>
			<version>1.1.1</version>
		</dependency>

```

在程序配置文件 application.properties 中指定 ehcache.xml 的路径,一般放置在当前 classpath 下；这样就让 Spring 缓存启用 Ehcache。

application.properties

```
spring.cache.jcache.config=classpath:ehcache.xml
```

在需要使用缓存的方法上使用注解 `@CacheResult` 进行声明，这样一旦调用这个方法，返回的结果就会被缓存，除非缓存被清除掉，下次就不会执行方法的逻辑了。


`@CacheResult` 必须指定 cacheName，否则 cacheName 默认视为该方法名称。