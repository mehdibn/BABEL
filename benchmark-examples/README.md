# Kafka Spark example 

## Start babel with the kafka producer
`java -cp ./benchmark-data-generation/target/benchmark-data-generation-1.0-SNAPSHOT-jar-with-dependencies.jar:./benchmark-integration/rget/benchmark-integration-1.0-SNAPSHOT-jar-with-dependencies.jar  tn.lipsic.babel.Client -threads 3  -s -P ./conf/babel.properties -producer tn.lipsic.integration.producers.KafkaProducer -target 100`
