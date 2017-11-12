# BDBench Data Generator

## To build this project :

`mvn compile package`

## To generate Data :

`java -cp ./benchmark-data-generation/target/benchmark-data-generation-1.0-SNAPSHOT-jar-with-dependencies.jar tn.lip2.bdbench.Client 

## Example of a Multi-threaded Injection with Stats :

`java -cp ./benchmark-data-generation/target/benchmark-data-generation-1.0-SNAPSHOT-jar-with-dependencies.jar tn.lip2.bdbench.Client -threads 3 -target 3 -s | grep -v user`

## Benchmark Generator Options :

`java -cp ./benchmark-data-generation/target/benchmark-data-generation-1.0-SNAPSHOT-jar-with-dependencies.jar tn.lip2.bdbench.Client -help`

### Options 

-help            : show Help

-s               : show stats during run (default: no stats)

-threads n       : execute using n threads (default: 1)

-db dbname       : specify the name of the DB to use (default: tn.lip2.bdbench.BasicDB)

-target n        : attempt to do n operations per second (default: unlimited)

-l label         : use label for stats (one label per bath stats)

-P PropertyFile  : load properties from the given file. Multiple files can be specified, and will be processed in the order specified

-p name=value    :  specify a property to be passed to the DB and workloads; multiple properties can be specified, and override any values in the propertyfile


### Properties



