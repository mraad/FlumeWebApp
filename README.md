# FlumeWebApp

Web application to send log4j messages to HDFS and/or HBase

## Avro to HDFS Agent
### Properties
    agent.sources=s1
    agent.channels=c1
    agent.sinks=k1

    agent.sources.s1.type=avro
    agent.sources.s1.channels=c1
    agent.sources.s1.bind=0.0.0.0
    agent.sources.s1.port=51515
    agent.sources.s1.interceptors=i1
    agent.sources.s1.interceptors.i1.type=timestamp

    agent.channels.c1.type=memory

    agent.sinks.k1.type=hdfs
    agent.sinks.k1.channel=c1
    agent.sinks.k1.hdfs.path=/flume/%Y/%m/%d/%H
    agent.sinks.k1.hdfs.filePrefix=flume
    agent.sinks.k1.hdfs.fileSuffix=.log
    agent.sinks.k1.hdfs.rollInterval=3600
    agent.sinks.k1.hdfs.rollCount=0
    agent.sinks.k1.hdfs.rollSize=0
    agent.sinks.k1.hdfs.fileType=DataStream
    agent.sinks.k1.hdfs.writeFormat=Text
    agent.sinks.k1.hdfs.useLocalTimeStamp=true

### Hive/Impala Integration
    hive> drop table if exists log4j;
    hive> create external table if not exists log4j(
    mm int,
    ss int,
    level string,
    clazz string,
    message string
    ) partitioned by (year int, month int, day int, hour int)
    row format delimited
    fields terminated by '\t'
    lines terminated by '\n'
    stored as textfile
    location '/flume/';

### Altering/Adding Partitions
    $ hadoop fs -ls -R /flume | awk -f altertable.awk > /tmp/tmp.hql
    $ hive -f /tmp/tmp.hql

## Avro to HBase Agent
### HBase setup
    $ hbase shell
    hbase> create 'log4j',{NAME=>'info',VERSIONS=>1}

### Zookeeper configuration (conf/zoo.cfg)
    clientPort=2181
    server.1=localhost.localdomain:2888:3888

### Properties
    agent.sources=avroSource
    agent.channels=memoryChannel
    agent.sinks=hbaseSink

    agent.sources.avroSource.type=avro
    agent.sources.avroSource.channels=memoryChannel
    agent.sources.avroSource.bind=0.0.0.0
    agent.sources.avroSource.port=61616
    agent.sources.avroSource.interceptors=i1
    agent.sources.avroSource.interceptors.i1.type=timestamp

    agent.channels.memoryChannel.type=memory

    agent.sinks.hbaseSink.type=hbase
    agent.sinks.hbaseSink.channel=memoryChannel
    agent.sinks.hbaseSink.table=log4j
    agent.sinks.hbaseSink.columnFamily=info
    agent.sinks.hbaseSink.serializer=org.apache.flume.sink.hbase.RegexHbaseEventSerializer
    agent.sinks.hbaseSink.serializer.regex=^([^\t]+)\t([^\t]+)\t([^\t]+)\t([^\t]+)\t(.+)$
    agent.sinks.hbaseSink.serializer.colNames=mm,ss,level,clazz,message

### Executing
    $ flume-ng agent -n agent -c conf -f conf/agent.properties

## Start Web Application
### Connect to HDFS
    $ mvn -Phdfs clean jetty:run
### Connect to HBase
    $ mvn -Phbase clean jetty:run
### Connect to the server
    http://localhost:8080/FlumeWebApp
