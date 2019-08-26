# Poor Cluster

Really a jvm cluster for anyone. 

With this you can easily build a cluster of JVM applications. JVM's can communicate and share state without the user needing to reason about any of the concerns of distributed computing.  

Based off of [JGroups](http://www.jgroups.org/)

This implements the following:
* Intra-node communication
* Shared distributed state
* Locking/Concurrency control

Main Class **com.poor.cluster.Node** 

### Usage
This can be run on multuple nodes or a single node (using multicast)
`java -Djgroups.bind_addr=127.0.0.1 -jar PoorCluster.jar`

