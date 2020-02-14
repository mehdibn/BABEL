# BABEL Data Generator

## To build this project :

`mvn compile package`

## To generate Data :

`java -cp ./benchmark-data-generation/target/benchmark-data-generation-1.0-SNAPSHOT-jar-with-dependencies.jar tn.lipsic.babel.Client `

## Example of a Multi-threaded Injection with Stats :

`java -cp ./benchmark-data-generation/target/benchmark-data-generation-1.0-SNAPSHOT-jar-with-dependencies.jar tn.lipsic.babel.Client -threads 3 -target 3 -P ./conf/babel.properties -s | grep -v user`

## Benchmark Generator Options :

`java -cp ./benchmark-data-generation/target/benchmark-data-generation-1.0-SNAPSHOT-jar-with-dependencies.jar tn.lipsic.babel.Client -help`

### Options 

<b>-help</b>            : show Help

<b>-s</b>               : show stats during run (default: no stats)

<b>-threads n</b>       : execute using n threads (default: 1)

<b>-producer dbname</b>       : specify the name of the DB to use (default: tn.lipsic.integration.producers.BasicDB)

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



### babel Test Command

`java -cp ./benchmark-data-generation/target/benchmark-data-generation-1.0-SNAPSHOT-jar-with-dependencies.jar:./benchmark-integration/target/benchmark-integration-1.0-SNAPSHOT-jar-with-dependencies.jar tn.lipsic.babel.Client -threads 3 -target 3 -s -P ./conf/babel.properties| grep -v user`


