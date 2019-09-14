# Kafka Spark example 

## Start BDBench with the kafka producer
`java -cp ./benchmark-data-generation/target/benchmark-data-generation-1.0-SNAPSHOT-jar-with-dependencies.jar:./benchmark-integration/rget/benchmark-integration-1.0-SNAPSHOT-jar-with-dependencies.jar  tn.lip2.bdbench.Client -threads 3  -s -P ./conf/bdbench.properties -producer tn.lip2.integration.producers.KafkaProducer -target 100`

## Submit the Spark Job from IntelliJ

