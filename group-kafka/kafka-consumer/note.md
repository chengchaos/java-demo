

```
$ bin/kafka-consumer-groups.sh  --bootstrap-server 192.168.88.174:9092 \
  --all-topics \
  --list
Note: This will not show information about old Zookeeper-based consumers.


$ bin/kafka-consumer-groups.sh  \
  --bootstrap-server 192.168.88.174:9092 \
  --group group01 \
  --describe
```