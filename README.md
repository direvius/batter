batter
======

a dummy target web server for load testing

Pass a file with URIs and responses as first parameter, port as second.
You should also consider passing some JVM parameters as well, for example:

```
java -Xms16048m -Xmx16048m -XX:+UseConcMarkSweepGC -XX:+PrintGC -server -Dlogback.configurationFile=./logback.xml -jar ./batter-1.5-SNAPSHOT.jar ppo.log 80
```

ppo.log here looks like:

```
/uri/example?a=b&b=c
response
blal
--
/uri2/example?c=d
response2
--
```
