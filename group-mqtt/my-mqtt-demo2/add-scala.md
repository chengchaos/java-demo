
**一, properties:**

```xml
    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <java.version>1.8</java.version>
        <scala.version>2.11.12</scala.version>
        <netty.version>4.1.36.Final</netty.version>
    </properties>
```

**二, repositories**


```xml

    <repositories>
        <repository>
            <id>fma-group</id>
            <url>http://pts-tsp.futuremove.cn/nexus/repository/fma-group/</url>
        </repository>

        <repository>
            <id>Eclipse Paho Repo</id>
            <url>https://repo.eclipse.org/content/repositories/paho-releases/</url>
        </repository>
    </repositories>
```

**三, dependencies**

```xml

	<dependencies>
        <dependency>
            <groupId>org.scala-lang</groupId>
            <artifactId>scala-library</artifactId>
            <version>${scala.version}</version>
        </dependency>

        <dependency>
            <groupId>org.scala-lang</groupId>
            <artifactId>scala-compiler</artifactId>
            <version>${scala.version}</version>
        </dependency>

        <dependency>
            <groupId>org.clapper</groupId>
            <artifactId>grizzled-slf4j_2.11</artifactId>
            <version>1.3.4</version>
        </dependency>

        <dependency>
            <groupId>org.scala-lang.modules</groupId>
            <artifactId>scala-java8-compat_2.11</artifactId>
            <version>0.9.0</version>
        </dependency>
	</dependencies>
```

**四, plugins**

```xml

	<build>
		<plugins>
            <plugin>
                <groupId>org.scala-tools</groupId>
                <artifactId>maven-scala-plugin</artifactId>
                <version>2.15.2</version>
                <executions>
                    <execution>
                        <goals>
                            <goal>compile</goal>
                            <goal>testCompile</goal>
                        </goals>
                    </execution>
                </executions>
                <configuration>
                    <scalaVersion>${scala.version}</scalaVersion>
                    <args>
                        <arg>-target:jvm-1.8</arg>
                    </args>
                </configuration>
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

            <!-- 资源 -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-resources-plugin</artifactId>
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
                                        <include>**/*.conf</include>
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

