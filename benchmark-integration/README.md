# Monitoring Structure 

## Producer Structure

### Example :

018-03-17 12:47:30:130 1 sec: 3 operations; 3 current ops/sec; INSERT count: 3, average latency(us): 1221,67 

2018-03-17 12:47:31:133 2 sec: 6 operations; 2,99 current ops/sec; INSERT count: 3, average latency(us): 370,67 

2018-03-17 12:47:32:135 3 sec: 9 operations; 3 current ops/sec; INSERT count: 3, average latency(us): 228,00 

2018-03-17 12:47:33:132 4 sec: 12 operations; 3,01 current ops/sec; INSERT count: 3, average latency(us): 389,00 

2018-03-17 12:47:34:133 5 sec: 15 operations; 3 current ops/sec; INSERT count: 3, average latency(us): 201,00

###  Grok Template

"%{TIMESTAMP_ISO8601:timestamp} %{NUMBER:duration} sec: %{NUMBER:operations} operations; %{NUMBER:throughput} current ops/sec; INSERT count: %{NUMBER:count}. average latency\(us\): %{NUMBER:latency} "

###  Definition

timestamp : Time of Metric

duration  : Benchmark Duration

operations: nb of operations per second

count     : total operations between (duration) and (duration-1) 

latency   : average Latency 