# jackdaw-example

```
lein run -m jackdaw-example.core
```

```
kafka-console-producer --broker-list localhost:9092 --topic input
>"This is test value"
```

```
kafka-console-consumer --bootstrap-server localhost:9092 --topic output
"This is test value"
```
