# thread-count

我计划用来测试 FreeBSD 系统中可以跑多少个线程。

在 FreeBSD 中，线程的限制在 `kern.threads.max_threads_per_proc` 内核参数中。 默认值是 1500.



```bash

sysctl -a | grep max_thread
kern.threads.max_threads_hits: 32
kern.threads.max_threads_per_proc: 1500
debug.acpi.max_threads: 3

sysctl -w kern.threads.max_threads_per_proc=65535
kern.threads.max_threads_per_proc: 1500 -> 65535

sysctl -vmstat 1
```

查看 Java 的线程大小：
[https://www.sohu.com/a/323369737_355142](https://www.sohu.com/a/323369737_355142)

```bash
java -XX:+PrintFlagsFinal -version | grep ThreadStackSize 

## java 8
java -XX:+UnlockDiagnosticVMOptions -XX:NativeMemoryTracking=summary -XX:+PrintNMTStatistics -version -
```

