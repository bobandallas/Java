package com.example.java26.week5;

/**
 *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *
 *  single leader
 *
 *      leader node     -       follower node1  / follower node2 / follower node3
 *      write node      -       read node1 / ..
 *      master node     -       slave node1 / ..
 *
 *      stand by node 用来替代master node（如果不work） 做为leader
 *
 *      server write      不同的策略：1。write之后直接返回 2。write之后和其中某些read node同步之后在return ok
 *         |
 *      write db           如果request太多，可以做一个vertical scaling加内存等等，解决不了考虑partition db & sharding
 *         | synchronized data
 *     read node1      /  read node2       / read node3
 *
 *      server read       不同策略：1。可以直接read from write db。2。 从 read node 读取 缺点是可能读到old数据
 *        |
 *      write db /  read node
 *
 *   *   *   *   *   *   *   *
 *  multi leader
 *
 *
 *      Leader1             Leader2             Leader3
 *      /   \              /       \           /       \
 *   F1     F2
 *   *   *   *   *   *   *   *
 *  leaderless
 *
 *                   node1
 *
 *
 *         node3               node2
 *
 *
 *                  node4
 *   *   *   *   *   *   *   *
 *
 *   partition DB策略
 *
 *   DB1(id % 3 == 0)        DB2(id % 3 == 1)            DB3(id % 3 == 2)       DB4 缺点在于加入新的db会重新分配所有数据
 *      *   *   *   *   *   *   *    *    *    *    *    *    *
 *      这种设计模式gateway来做数据库操作，先发送到config server
 *                                  |
 *                              gateway         <->      config server(metadata)
 *
 *                      /           |           \
 *
 *  DB1(id 1 ~ 10k)        DB2(10k + 1 ~ 20k)      DB3(20k + 1 ~ 30k)
 *
 *      *   *   *   *   *   *   *    *    *    *    *    *    *
 *CAP       CA不存在
 * Consistency 每次都是最新的数据
 * Availability 每分钟任何时间都能得到response
 * Partition tolerance  node断开不影响整体
 *
 *                                 gateway mongos         <->      (mongos)config server(metadata)
 *
 *                      /           |                   \
 *          sharding1           sharding2               sharding3
 *  DB1(id 1 ~ 10k)        DB2(10k + 1 ~ 20k)           DB3(20k + 1 ~ 30k)
 *    primary node(写)           primary node(local index)
 *    secondary node (读)        secondary
 *
 *  hash(id) % length / .. 缺点是慢要搜索 优点是更均匀
 *
 *
 * global secondary index(server)  用来实现field index查询 缺点是很难和数据库保持consistency，要保持global transaction
 *      Tom: DB1/ DB2
 *      Jerry: DB1
 *    *    *    *    *    *    *    *    *    *    *    *
 *    数据库实现一般都是 B+ 或者 LSM (log structure index tree)
 *
 *    random IO
 *    B+
 *                 /
 *     memory: read data
 *              /
 *     disk: block(row ->rollback pointer -> new row)
 *    *    *    *    *    *    *    *    *    *    *    *
 *    sequential IO  性能上Write非常快，读取上不太行
 *
 *    LSM => Log structure merged index tree?
 *
 *                  Log file4
 *          /               |               \
 *    Log file1         Log file2           Log file3
 *
 *
 *      sorted string table
 *
 *                  SST4
 *
 *        /             |                    \
 *    SST1              SST2                   SST3
 *   update id=1        update id=1             delete id=1
 *
 *
 *  *    *    *    *    *    *    *    *    *    *    *    *
 *    0 ~ Integer.MAX_VALUE
 *
 *                          N1(0)
 *
 *          N4(40k)                     N2(10k)
 *
 *                                     NX(15k)
 *                        N3(20k)
 *
 *
 *
 *   Redis cluster 25k(?) hash slot
 *      Leader1(0 ~ 10k)             Leader2(10k ~ 15k)             Leader3(15k ~ 25k)
 *
 *
 *    Cassandra node
 *                                      flush to disk
 *   write  -->     mem table (cache)  ------------->  SST(sorted string table)
 *           |
 *          commit log 插入之前，防止node shut down
 *
 *                SST6
 *              /       \
 *          SST1        SST2        SST3        SST4            SST5
 *
 *   read  -->    blooming filter  -> index -> SST -> merge -> get data
 *
 * BASE Theory:
 * Basic available
 * Soft stage 拿到数据之前会存在不同的stage
 * Eventual consistency 最终会consistency
 *
 *    Cassandra Cluster
 *                          N1(0)
 *
 *          N4(40k)                    N2(10k) (data1)
 *
 *                                     NX(15k)(data1)
 *                        N3(20k)(data1)
 *
 *
 *      replica factor = 3          取决于备份几份
 *      read consistency level = 1 ~ replica factor
 *                  >= 2  : read repair  读取多份比对，返还给用户read repair更新数据
 *                  read data N3
 *                  read hash(data) from NX
 *      write consistency level = 1 ~ replica factor  成功几个然后返还给用户
 *
 *      rc + wc > replica factor    交叉node，会出现不是consistency的
 *
 *     client -> read -> N4 ->  N2 / NX / N3
 *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *
 *  Election -> Raft
 *      互相确认存在，如果follower确认leader不存在，变成candidate然后投票变成leader
 *     Leader   -   Candidate   -   Follower
 *
 *    *    *    *    *    *    *    *    *    *    *    *    *    *    *    *
 *    tomorrow :
 *      nosql vs rdbms 
 *      message queue
 *      global transaction
 *
 *
 *   homework
 *      1. provide spring cloud config service 所有的application property都放进去
 *      2. refresh properties -> actuator
 *
 */