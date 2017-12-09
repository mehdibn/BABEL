# BDBench Data Generator

## To build this project :

`mvn compile package`

## To generate Data :

`java -cp ./benchmark-data-generation/target/benchmark-data-generation-1.0-SNAPSHOT-jar-with-dependencies.jar tn.lip2.bdbench.Client `

## Example of a Multi-threaded Injection with Stats :

`java -cp ./benchmark-data-generation/target/benchmark-data-generation-1.0-SNAPSHOT-jar-with-dependencies.jar tn.lip2.bdbench.Client -threads 3 -target 3 -P ./conf/bdbench.properties -s | grep -v user`

## Benchmark Generator Options :

`java -cp ./benchmark-data-generation/target/benchmark-data-generation-1.0-SNAPSHOT-jar-with-dependencies.jar tn.lip2.bdbench.Client -help`

### Options 

<b>-help</b>            : show Help

<b>-s</b>               : show stats during run (default: no stats)

<b>-threads n</b>       : execute using n threads (default: 1)

<b>-db dbname</b>       : specify the name of the DB to use (default: tn.lip2.bdbench.BasicDB)

<b>-target n</b>        : attempt to do n operations per second (default: unlimited)

<b>-l label</b>         : use label for stats (one label per bath stats)

<b>-P PropertyFile</b>  : load properties from the given file. Multiple files can be specified, and will be processed in the order specified

<b>-p name=value</b>    :  specify a property to be passed to the DB and workloads; multiple properties can be specified, and override any values in the propertyfile

<b>-id injectorID</b>   :  use an Id by Injector (default value : default)


### Properties

<b>exportfile</b>       : path to a file where output should be written instead of to stdout (default: undefined/write to stdout)

<b>fieldcount</b>       : the number of fields in a record (default: 10)

<b>fieldlength</b>      : the size of each field (default: 100)

<b>recordcount</b>      : number of records to load into the database initially (default: 0)

<b>maxexecutiontime</b> : maximum execution time in seconds. The benchmark runs until either the operation count has exhausted or the maximum specified time has elapsed, whichever is earlier

<b>kafkabrokers</b>      : list of kafka brokers for metrics integration

<b>kafkatopic</b>       : kafka topic for metrics integration


### Used Kafka Commands

`
/usr/local/bin/zookeeper-server-start /usr/local/etc/zookeeper/zoo.cfg > /tmp/zookeeper.out 2> /tmp/zookeeper.log &
/usr/local/bin/kafka-server-start /usr/local/etc/kafka/server.properties > /tmp/kafka.out 2> /tmp/kafka.log &
/usr/local/bin/kafka-topics --zookeeper localhost:2181 --describe
__/usr/local/bin/kafka-topics --zookeeper localhost:2181 --create --topic bdbench --replication-factor 1 --partitions 1__
/usr/local/bin/kafka-console-consumer --bootstrap-server localhost:9092 --topic bdbench --from-beginning  --property print.key=true --property print.timestamp=true
/usr/local/bin/kafka-console-producer --broker-list localhost:9092 --topic bdbench
`

### BDBench Test Command

`java -cp ./benchmark-data-generation/target/benchmark-data-generation-1.0-SNAPSHOT-jar-with-dependencies.jar tn.lip2.bdbench.Client -threads 3 -target 3 -s -P ./conf/bdbench.properties| grep -v user`

### BDBench Test Command

`log.message.timestamp.type -> CreateTime`