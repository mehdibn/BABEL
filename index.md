# BABEL
A Generic Benchmarking Platform for BigData Architectures


# BABEL Installation 

## Requirements 

 -  Java 1.8
 -  Ansible 

## Steps 
### Ansible Deployment  


1. Install **Ansible** :

http://docs.ansible.com/ansible/latest/installation_guide/intro_installation.html

2. Active the Remove Session in your Mac Configuration

`Préférences Système > Partage > Coche "Session à distance"`

3. create your ssh public key :

`ssh-keygen`

4. add your public Key to your Host 

`ssh-copy-id localhost`

5. test Ansible Installation 

`ansible localhost -m ping`

6. BABEL Installation, from BABEL/benchmark-installation-orchestration :

`ansible-playbook ./install.yml`

7. Start BABEL, from BABEL/benchmark-installation-orchestration :

`ansible-playbook ./start.yml`

### BABEL Deployment

1. Clone the BABEL project from Github

`git clone https://github.com/mehdibn/BABEL.git`

2. Configure your produce by extending the java abstract class **tn.lipsic.babel.adapters.GenericProducer**

3. Create your own BABEL deployment in the **benchmark-installation-orchestration** module 

4. Configure the **inventory** with the different groups:

```
[elasticsearch-nodes]
elastic-node-1
elastic-node-2
elastic-node-3

[logstash-nodes]
logstash-node-1
logstash-node-2
logstash-node-3

[kibana-nodes]
kibana-node-1


[kafka-nodes]
kafka-node-1
kafka-node-2
kafka-node-3

[babel-nodes]
babel-node-1
babel-node-2
babel-node-3

[sut]
sut-node-1
sut-node-2
sut-node-3
sut-node-4
sut-node-5
```

5. Define the different BABEL and SUT parameters in the **group_vars/all** file, e.g :

```
# babel properties
# OS System  : linux | darwin
os_type : linux
parent_dir : /var/app
babel_install_parent_dir: '{{parent_dir}}/babel/'
babel_install_dir: '{{babel_install_parent_dir}}/{{babel_name}}'
babel_install_link_dir: '{{babel_install_parent_dir}}/babel'
babel_name : 'babel-{{babel_ver}}'
babel_ver : '1.0-SNAPSHOT'



#kafka
embedded_kafka: true
kafka_install_parent_dir: '{{parent_dir}}/kafka/'
kafkabrokers : '10.0.0.8:9092'
zookeeperquorum : '10.0.0.8:2181'
kafkarep: "1"

#elastic
embedded_elasticsearch: true
elasticsearch_install_parent_dir: '{{parent_dir}}/elasticsearch/'
elastic_coordinator : '10.0.0.8:9200'

#logstash
embedded_logstash: true
logstash_install_parent_dir: '{{parent_dir}}/logstash/'

#kibana
embedded_kibana: true
kibana_install_parent_dir: '{{parent_dir}}/kibana/'


#SUT properties
#Metricbeat
metricbeat_install_parent_dir: '{{parent_dir}}/metricbeat/'

#lambda
SUTKAFKABIN: "/usr/hdp/current/kafka-broker/bin/"
SUTSPARKBIN: "/usr/hdp/current/spark2-client/bin"
SUTKAFKAREP: "3"
SUTHiveTablePath: "/tmp/spark-data"
SUTCheckpointDir: "/tmp/spark-check"
SUTSparkMode: "cluster"

#Producer Properties
producer: 'tn.lipsic.integration.producers.KafkaProducer'
threads: "5"
maxMessages: "1000000"
SUTkafkabrokers : 'benchmark2-w1.l0hv1yccohxuzhmf13izu2rvog.fx.internal.cloudapp.net:6667,benchmark2-w0.l0hv1yccohxuzhmf13izu2rvog.fx.internal.cloudapp.net:6667,benchmark2-w2.l0hv1yccohxuzhmf13izu2rvog.fx.internal.cloudapp.net:6667'
SUTZookeeper: 'benchmark2-m3.l0hv1yccohxuzhmf13izu2rvog.fx.internal.cloudapp.net:2181'
SUTtopic : 'babel-m'

#Consumers properties
consumers_jolokia_agent:
  - id: "1"
    layer: "layer1"
    port: "7770"
    type: "Kafka"
    user: "kafka"
    metrics:
      - mbean: "kafka.server:name=MessagesInPerSec,topic={{ SUTtopic }},type=BrokerTopicMetrics"
        attr: "Count"
        field: "MessagesInPerSec"

  - id: "4"
    layer: "layer3"
    port: "7771"
    type: "DataNode"
    user: "hdfs"
    metrics:
      - mbean: "Hadoop:name=FSDatasetState,service=DataNode"
        attr: "DfsUsed"
        field: "DfsUsed"
      - mbean: "Hadoop:name=RpcActivityForPort8010,service=DataNode"
        attr: "RpcProcessingTimeAvgTime"
        field: "RpcProcessingTimeAvgTime"

consumers_jolokia_embedded:
  - id: "2"
    layer: "layer2"
    port_executor: "7775"
    port_driver: "7772"
    type: "spark-streaming"
    metrics:
      - mbean: "java.lang:name=PS MarkSweep,type=GarbageCollector"
        attr: "CollectionTime"
        field: "gc.psms_collection_time"

  - id: "3"
    layer: "layer2"
    port_executor: "7776"
    port_driver: "7773"
    type: "spark-streaming"
    metrics:
      - mbean: "java.lang:name=PS MarkSweep,type=GarbageCollector"
        attr: "CollectionTime"
        field: "gc.psms_collection_time"

  - id: "6"
    layer: "layer4"
    port_executor: "7777"
    port_driver: "7774"
    type: "spark"
    metrics:
      - mbean: "java.lang:name=PS MarkSweep,type=GarbageCollector"
        attr: "CollectionTime"
        field: "gc.psms_collection_time"

```

6. install BABEL 

`ansible-playbook install.yml`

7. init BABEL 

`ansible-playbook init-balel-nodes.yml`

8. init SUT 

`ansible-playbook init-sut-nodes.yml`
