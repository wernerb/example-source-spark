# Example Spark Structured Streaming Source

```scala
val df = spark.
        readStream.
        format("org.apache.spark.sql.sources.SampleNormalDistributionSourceProvider").
        load()
```

## Create a fat jar

```
sbt assembly
```

## Run tests

Run:
```bash
sbt test
```
