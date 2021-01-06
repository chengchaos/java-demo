# Spring Boot

[https://start.spring.io/](https://start.spring.io/)

在这个页面可以开始 Spring boot。

1, 在 pom.xml 文件中添加 

```xml

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <java.version>1.8</java.version>
        <scala.version>2.13.3</scala.version>
    </properties>

    <!-- 仓库 -->
    <repositories>
        <repository>
            <id>aliyun</id>
            <url>https://maven.aliyun.com/repository/public</url>
            <releases>
                <enabled>true</enabled>
            </releases>
            <snapshots>
                <enabled>false</enabled>
            </snapshots>
        </repository>
    </repositories>

    <pluginRepositories>
        <pluginRepository>
            <id>aliyun</id>
            <url>https://maven.aliyun.com/repository/public</url>
            <releases>
                <enabled>true</enabled>
            </releases>
            <snapshots>
                <enabled>false</enabled>
            </snapshots>
        </pluginRepository>
    </pluginRepositories>

    <build>
        <plugins>
            <!-- 资源 -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-resources-plugin</artifactId>
                <version>3.2.0</version>
                <executions>
                    <execution>
                        <id>default-resources</id>
                        <phase>validate</phase>
                        <goals>
                            <goal>copy-resources</goal>
                        </goals>
                        <configuration>
                            <outputDirectory>target/classes</outputDirectory>
                            <useDefaultDelimiters>false</useDefaultDelimiters>
                            <delimiters>
                                <delimiter>@*@</delimiter>
                            </delimiters>
                            <resources>
                                <resource>
                                    <directory>src/main/resources/</directory>
                                    <filtering>true</filtering>
                                    <includes>
                                        <include>**/*.yml</include>
                                        <include>**/*.properties</include>
                                        <include>**/*.xml</include>
                                    </includes>
                                </resource>
                            </resources>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
```
