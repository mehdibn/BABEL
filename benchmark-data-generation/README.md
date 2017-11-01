# BDBench Data Generator

## to build this project :

`mvn compile package`

## to generate Data :

`java -cp ./benchmark-data-generation/target/benchmark-data-generation-1.0-SNAPSHOT-jar-with-dependencies.jar tn.lip2.bdbench.Client -p workload=tn.lip2.bdbench.workloads.CoreWorkload -load`