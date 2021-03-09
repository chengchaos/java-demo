# java-demo

编辑 pom.xml 文件，添加：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.4.3</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>

    <groupId>luxe.chaos</groupId>
    <artifactId>spring-boot-demo</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>double-token-demo</name>
    <description>double-token-demo for Spring Boot</description>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <java.version>1.8</java.version>
        <scala.version>2.13.5</scala.version>
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

    <dependencies>
        <!-- scala-library -->
        <!-- https://mvnrepository.com/artifact/org.scala-lang/scala-library -->
        <dependency>
            <groupId>org.scala-lang</groupId>
            <artifactId>scala-library</artifactId>
            <!-- <version>2.13.5</version> -->
            <version>${scala.version}</version>
        </dependency>

        <!-- scala-compiler -->
        <!-- https://mvnrepository.com/artifact/org.scala-lang/scala-compiler -->
        <dependency>
            <groupId>org.scala-lang</groupId>
            <artifactId>scala-compiler</artifactId>
            <!-- <version>2.13.5</version> -->
            <version>${scala.version}</version>
        </dependency>

        <!-- https://mvnrepository.com/artifact/org.clapper/grizzled-slf4j -->
        <dependency>
            <groupId>org.clapper</groupId>
            <artifactId>grizzled-slf4j_2.13</artifactId>
            <version>1.3.4</version>
        </dependency>


        <!-- 常用 -->
        <!-- https://mvnrepository.com/artifact/org.apache.commons/commons-lang3 -->
        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-lang3</artifactId>
            <version>3.12.0</version>
        </dependency>


        <!-- https://mvnrepository.com/artifact/org.apache.httpcomponents/httpclient -->
        <dependency>
            <groupId>org.apache.httpcomponents</groupId>
            <artifactId>httpclient</artifactId>
            <version>4.5.13</version>
        </dependency>

        <!-- https://mvnrepository.com/artifact/com.esotericsoftware/reflectasm -->
        <dependency>
            <groupId>com.esotericsoftware</groupId>
            <artifactId>reflectasm</artifactId>
            <version>1.11.9</version>
        </dependency>


        <!-- https://mvnrepository.com/artifact/com.google.guava/guava -->
        <dependency>
            <groupId>com.google.guava</groupId>
            <artifactId>guava</artifactId>
            <version>30.1-jre</version>
        </dependency>


        <!-- org.springframework.boot -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-configuration-processor</artifactId>
        </dependency>

        <!-- spring-boot-test -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
            <exclusions>
                <exclusion>
                    <groupId>org.junit.vintage</groupId>
                    <artifactId>junit-vintage-engine</artifactId>
                </exclusion>
            </exclusions>
        </dependency>

    </dependencies>

    <build>

        <!-- <defaultGoal>compile</defaultGoal> -->
        <!-- <directory>${basedir}/target</directory> -->
        <finalName>${artifactId}</finalName>

        <!-- 定义在filter的文件中的name=value键值对，-->
        <!-- 会在 build 时代替 $ {name} 值应用到 resources中。 -->
        <!-- maven的默认 filter 文件夹为 ${basedir}/src/main/filters -->
        <!-- 不知道怎么用
        <filters>
            <filter>filters/filter1.properties</filter>
        </filters>
        -->

        <pluginManagement>
            <!-- https://github.com/davidB/scala-maven-plugin -->
            <plugins>
                <!-- https://mvnrepository.com/artifact/net.alchim31.maven/scala-maven-plugin -->
                <plugin>
                    <groupId>net.alchim31.maven</groupId>
                    <artifactId>scala-maven-plugin</artifactId>
                    <version>4.4.1</version>
                </plugin>

                <plugin>
                    <groupId>org.apache.maven.plugins</groupId>
                    <artifactId>maven-compiler-plugin</artifactId>
                    <version>3.8.1</version>
                </plugin>
            </plugins>
        </pluginManagement>

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
                <version>3.8.1</version>
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
                <version>2.22.2</version>
                <configuration>
                    <skipTests>true</skipTests>
                </configuration>
            </plugin>

            <!-- scala-compile -->
            <plugin>
                <groupId>net.alchim31.maven</groupId>
                <artifactId>scala-maven-plugin</artifactId>
                <executions>
                    <execution>
                        <id>compile-scala</id>
                        <phase>compile</phase>
                        <goals>
                            <goal>add-source</goal>
                            <goal>compile</goal>
                        </goals>
                    </execution>
                    <execution>
                        <id>test-compile-scala</id>
                        <phase>test-compile</phase>
                        <goals>
                            <goal>add-source</goal>
                            <goal>testCompile</goal>
                        </goals>
                    </execution>
                </executions>
                <configuration>
                    <scalaVersion>${scala.version}</scalaVersion>
                    <jvmArgs>
                        <jvmArg>-Xms64m</jvmArg>
                        <jvmArg>-Xmx1024m</jvmArg>
                    </jvmArgs>
                </configuration>
            </plugin>

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

            <!-- 源码 -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-source-plugin</artifactId>
                <version>3.2.1</version>
                <executions>
                    <execution>
                        <phase>package</phase>
                        <goals>
                            <goal>jar-no-fork</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>

        </plugins>

    </build>


    <profiles>

        <profile>
            <id>pre</id>
            <properties>
                <project.env>pre</project.env>
            </properties>
        </profile>

        <!-- 开发环境 -->
        <profile>
            <id>test</id>
            <properties>
                <project.env>test</project.env>
            </properties>
        </profile>

        <profile>
            <id>dev_local</id>
            <activation>
                <activeByDefault>true</activeByDefault>
            </activation>
            <properties>
                <project.env>dev_local</project.env>
            </properties>
        </profile>
    </profiles>
</project>
```

打印彩色日志，编辑 application.properties 文件，添加：

```sh
console-available=true
spring.output.ansi.enabled=ALWAYS
```

## akka-demo-101

使用这个项目测试了：

- Scala 编写简单的 Actor 系统。

## my-scala003

使用这个项目测试了：

- Scala 和 Java 混合编译
- Java 访问 Kafka （写入）

## my-hbase-001

使用这个项目测试了：

- Scala 和 Java 混合编译
- Java 访问 Kafka （读取）
- Java 访问 HBase
- Java 编写简单的 Actor 系统。

## my-timer

setup

```bash
spring init my-timer
```

EOF
