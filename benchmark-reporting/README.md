## Benchmark Reporting 


### ElasticSearch Installation

Download ElasticSearch 

`wget https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-6.1.1.tar.gz`


Unarchive it 

`tar xvf ./elasticsearch-6.1.1.tar.gz`

Start ElasticSearch 

`./elasticsearch-6.1.1/bin/elasticsearch > /tmp/elastic.out 2> /tmp/elastic.log &`

### Kibana Installation

Download Kibana 

`wget https://artifacts.elastic.co/downloads/kibana/kibana-6.1.1-darwin-x86_64.tar.gz`


Unarchive it 

`tar xvf ./kibana-6.1.1-darwin-x86_64.tar.gz`

Start Kibana 

`./kibana-6.1.1-darwin-x86_64/bin/kibana > /tmp/kibana.out 2> /tmp/kibana.log &`

Create the index template from the kibana UI (Dev Tools Section)

`producer-template.curl`



### Logstach Installation

Download Logstach 

`wget https://artifacts.elastic.co/downloads/logstash/logstash-6.1.1.tar.gz`

Unarchive it 

`tar xvf ./logstash-6.1.1.tar.gz`

Start the logstash Pipeline 

`./logstash -f ./producer.indexer.conf`

Add the index to Kibana from the Management Section