package com.example.java26.week5;

/**
 *
 *  实现Asynchronous communication 用户不需要等待所有的操作完成之后再去干别的，像non-blocking system
 *
 *  user
 *   |
 * producer   ->  message queue (server)  ->  consumer
 *   |
 *  DB
 *
 *  queue model             竞争关系
 *  pub - sub  model        交给所有的subscriber，
 *
 *  => active mq / rabbit mq   设计类似于queue model 都是在consumer层面设计 horizontal scaling
 *
 *  => kafka                    对Queue 层面做sharding， partition（通过hash）
 *              partition在同一个topic内是有序的， topic之间无序
 *
 *              只有一个group类似于queue model。 多个group类似于pub - sub
 *
 *              在同一个consumer group中，不会处理重复的partition！
 *              partition many to one consumer(in same group)
 *
 *              broker Leader也会分发task到正确的另外的broker中， 如果某个消息处理时间过长，如果只有一个consumer，后面的消息全会被阻塞
 *              如果任务大时间长，建议用传统的 active mq / rabbit mq
 *               broker1
 *               Topic1                 Consumer Group1 （leader决定分发策略）
 * producer  ->  partition1(L)     ->     Consumer1(p1)
 *               partition2(F)            Consumer2(p2, p3)
 *               partition3(L)
 *                                      Consumer Group2
 *                                        Consumer3(p1, p2, p3)
 *               broker2
 *               Topic1
 *               partition1(F)
 *               partition2(L)
 *               partition3(F)
 *  => SQS
 *          producer   ->  message queue (server)  ->  consumer
 *                          visibility timeout
 *  => SNS （结合了pub - sub）
 *                 -> SQS1
 *          => SNS -> SQS2
 *                 -> email / text
 *    *    *    *    *    *    *    *    *    *    *    *    *    *    *    *
 *    1. message failed
 *      dead letter queue       会存有fail的message
 *    2. duplicate messages (global unique message id)
 *      idempotent
 *      redis cache / database
 *      SNS -> de-duplicate
 *      ...
 *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *
 *      同时向DB和MQ发送，如何保持transaction
 *      比较常见的是 1。CDC pattern + outbox pattern  2。 global transaction
 *    3. message queue and db not in same tx
 *
 *      outbox table 存着之后发送给mq的message， CDC监测DB改变，一旦outbox改变，递交给MQ。
 *      能保证一起递交到DB和MQ
 *
 *      缺点可能是duplicate message
 *
 *      service ->  message queue
 *         |
 *       DB
 *
 *       service
 *         |
 *       DB (outbox table)  -> service(cdc / monitor service) -> message queue
 *
 *    4. global transaction
 *       a. SAGA
 *          多个数据库，多个service如何保持一致？
 *          如果任何操作出现问题就rollback
 *          service -> mq -> service -> mq -> service
 *             |                |               |
 *            DB1              DB2             DB3
 *            +1                +1
 *
 *             -1 <-  mq   <-   -1  <-  mq   <- service
 *      b. two phase commit
 *
 *              coordinator(server)
 *              /       \
 *            DB1       DB2
 *
 *          1. prepare stage   问两个数据库ok吗 然后提交log
 *          2. commit stage         把log运行
 *
 *          弊端 coordinator 1。single point failure 2。performance
 *   *    *    *    *    *    *    *    *    *    *    *
 *
 *
 *
 */