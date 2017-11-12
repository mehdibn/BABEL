# BDBench Data Generator

## To build this project :

`mvn compile package`

## To generate Data :

`java -cp ./benchmark-data-generation/target/benchmark-data-generation-1.0-SNAPSHOT-jar-with-dependencies.jar tn.lip2.bdbench.Client 

## More details in this command :

`java -cp ./benchmark-data-generation/target/benchmark-data-generation-1.0-SNAPSHOT-jar-with-dependencies.jar tn.lip2.bdbench.Client -threads 3 -target 3 -s | grep -v user`

## Benchmark Options :

`java -cp ./benchmark-data-generation/target/benchmark-data-generation-1.0-SNAPSHOT-jar-with-dependencies.jar tn.lip2.bdbench.Client -help`

### Options 
-s : 

