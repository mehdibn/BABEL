# BDBench Data Generator

## to build this project :

`mvn compile package`

## to generate Data :

`java -cp ./benchmark-data-generation/target/benchmark-data-generation-1.0-SNAPSHOT-jar-with-dependencies.jar tn.lip2.bdbench.Client -p workload=tn.lip2.bdbench.workloads.CoreWorkload -load`

## more details in this command :
`java -cp ./benchmark-data-generation/target/benchmark-data-generation-1.0-SNAPSHOT-jar-with-dependencies.jar tn.lip2.bdbench.Client -p workload=tn.lip2.bdbench.workloads.CoreWorkload -load -threads 3 -target 3 -s | grep -v user`