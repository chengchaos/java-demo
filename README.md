# java-demo

## my-timer


setup


```bash

$ spring init my-timer
```

编辑 pom.xml 文件，添加：

```xml
    <repositories>
        <repository>
            <id>fma-group</id>
            <url>https://www.chengchaos.cn/nexus/repository/fma-group/</url>
        </repository>
    </repositories>


	<build>
		<plugins>
            <!-- Spring-Boot-Maven -->
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>


            <!-- Java 编译 -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.7.0</version>
                <configuration>
                    <source>1.8</source>
                    <target>1.8</target>
                    <encoding>UTF-8</encoding>
                </configuration>
            </plugin>

            <!-- Java 测试 -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>2.20.1</version>
                <configuration>
                    <skipTests>true</skipTests>
                </configuration>
            </plugin>

		</plugins>
	</build>
```

编辑 application.properties 文件，添加：


```sh
console-available=true
spring.output.ansi.enabled=ALWAYS
```

