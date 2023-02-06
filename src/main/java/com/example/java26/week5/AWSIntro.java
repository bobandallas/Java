package com.example.java26.week5;

/**
 *
 *  EC2 普通计时收费 -> spot on（超过费用自动关闭） -> lease -> 一片region都是你的。指定
 *
 *  每个EC2都有tomcat，如何做loadbalancer ：
 *      Loadbalancer server ：
 *          1。classic
 *          2。network layer in 4
 *          3。Application LoadBalanceer(ALB 也有 ip) 根据url path进行redirect
 *
 *  如何解决single point failure在不同的EC2之间：
 *      ASG 帮你部署ec2。 Auto Scaling Group；自动帮你启动新的EC2，也像mutiple thread pool一样会有max， min
 *
 *  IP不能直接attach到EC2上，而是通过ENI （Elastic Network Interface）配置Ip
 *  一般是不让用户访问public ip 访问 EC2，必须通过API gateway 来搞。 --》 VPC： subnet 设置 private ip
 *
 *  region（data center cluster level） ,  zone (data center level)
 *
 *                route 53 （可以做跨region的load balance，做DNS server 访问domain）
 *                  |
 *                region1
 *               API Gateway  - lambda （read limiter，customize filter, lambda 类似于severless，代码交给他们运行）
 *                  |
 *               ALB(ip)
 *          /               \                                                   加密：1。加密好传s3 2。给s3自己的key 3 KMS 读取key 4。直接交给S3的服务加密
 *  target[EC2(tomcat)]     target[ASG[EC2(docker(tomcat)), EC2(tomcat)]]  -> S3(simple storage sys =-- Object Storage 存大图片) -> lifecycle hook(把不常用的file到) -> S3 Glacier(便宜，速度慢)
 *                                  |
 *                             Elastic Cache
 *                                 |
 *             EC2(MySQL) 贵/ RDS(MySQL, Oracle, Postgre) RDBMS service / Aurora 功能更多，拓展disk/ DynamoDB（DAS Cache速度极快）
 *                            |         \       \
 *                          StandBy   read1     read2
 *                             ｜
 *                          用来代替leader， 提供availability
 *
 *  Security Group: instance   +   stateful（只要进来了就可以出去，只要出去了再进来不再验证）
 *
 *
 *  VPC :
 *                   internet gateway
 *                   /
 *        public subnet(route table)        private subnet(route table, NACL) NACL network access controll list
 *            CIDR                               CIDR block 来计算具体位置，因为subnet保存了一大片private ip的block
 *                                            private如果想访问外部需要NAT，有点像public ip pool
 *
 *   IAM :
 *   SQS : FIFO / Standard
 *   SNS
 *
 *   ECR : docker image repository
 *   ECS : task definition 1 - 1 task 1 - m / 1 - 1 docker container
 *       : EC2 / Fargate（severlless）
 *   CloudWatch : monitor / metric -> alarm
 *   CloudTrail
 *   CloudFront : CDN + TTL(time to live)
 *
 *       client(China) -> request -> edge location server -> request -> server(US)
 *
 *   Cloudformation : 所有的config
 *
 *   ************************
 *  AWS
 *      youtube / udemy / ..
 *      developer / solution architect associate certificate
 *  Frontend
 *      React   (react hook, component(return render() {}), router, redux, reducer)
 *      Angular (component, ng-directive, two way binding,
 *              string interpolation, input / output , pipe,
 *              dependency injection, router, decorator, interceptor,
 *              http client -> observable....)
 *  coding:
 *      easy : 3 ~ 5 min bug free
 *      medium : < 20 min bug free
 *          bfs / dfs / sliding window / string, array / hashmap / treemap / priority queue
 *          [1, 2, 3, 4] => combination
 *          stack
 *
 *          1   2   3   4
 *          5   6   7   8
 *          9   10  11  12
 *          13  14  15  16
 *
 *          1   2     3   4     8   12    16    15   14   13   9    5   6   7   11  10
 *
 *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *
 *   next week
 *   Agile scrum + Daily Work
 *   Production support + log
 *   CI/CD
 *   interview questions
 *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *
 * what’s your java experience
 *      1. java -> collection, exception, stream api, multi-threading
 *      2. spring -> spring boot / jpa / hibernate / spring mvc
 *      ..
 * what is mvc
 *      1. model view controller
 *      2. model: db + repository layer / dao layer
 *      3. view: user interface
 *      4. controller: service logic layer
 * why spring
 *      1. IOC: concept / why IOC ： loose couping, dependency injection, mange bean lifecycle
 *              @Autowired, @Component..
 *      2. AOP: concept / why AOP : centralize logic
 *              @Before, @After, @PointCut..
 * why spring boot
 *      1. based on Spring IOC, AOP
 *      2. embedded tomcat
 *      3. application properties
 *      4. main starter
 *      5. actuator
 *      6. for microservice
 * what is point cut
 *      1. location 对哪些做before after
 * java 8 new features
 *      1. functional interface
 *      2. lambda expression
 *      3. stream api
 *      4. optional
 *      5. completable future
 *      6. default method in interface
 *      7. move perm gen to meta space
 * example of stream api
 *      list.stream().filter(x -> x > 3).map(x -> x).collect(Collectors.toList());
 * predicate vs supplier vs consumer vs function
 *      1. Function : one input, one output
 *  *           Consumer : one input, no output
 *  *           Supplier : no input, one output
 *  *           Predicate: one input, boolean output
 * stored procedure vs function vs package
 *  ·   stored procedure 减少service layer的logic。减少数据交换
 *      package 管理procedure
 *
 *      cursor 分为explicit 和 implicit
 *      create procedure name as
 *      --x initialize cursor
 *      --x
 *      begin
 *          begin cursor
 *          transaction
 *          for cursor
 *              fetch cursor into val
 *          end cursor
 *
 *          exception
 *      end
 *
 * when to use function
 *      select xx
 *      from xx
 *      where function(xx) = 1
 *
 * when did you use trigger ： 比如做一个backup
 *      create trigger xx
 *      before / after insert , update , delete
 *      :old value / :new value
 *      begin
 *          ...
 *      end
 *
 * what other database objects have you used before
 *      1. trigger
 *      2. package
 *      3. view
 *      4. index
 *      ..
 * diff mysql and oracle
 *      1. syntax
 *      2. isolation levels
 * isolation levels
 *      1. mysql: read uncommitted, read committed, repeatable read(default), serializable
 *      2. oracle: read committed(default), serializable, read only
 * inner join vs left join          cross join
 * sql query:  return count of employee in each department >= 3
 *      emp    emp_dept     dept
 *      1. group by + count + having
 *      2. join
 *      select xx, count(*)
 *      from ... join .. on ..
 *      group by xx
 *      having count(*) >= 3
 *
 * manager behavior questions:
 *      will you memorize everything
 *      will you prefer frontend or backend
 *      future plans..
 *
 *
 * what is a good restful api?
 * 1. restful standard (naming style / http / versioning / ...stateless)
 * 2. SOLID principle + OOP
 * 3. exception handling + customized error message
 * 4. log
 * 5. document : api
 * 6. circuit breaker
 * 7. centralize config ?
 * 8. security : authentication / authorization / encrypion / https / properties / data
 * 9. test / TDD
 *
 * how to design microservices?
 * 1. config service
 * 2. gateway service
 * 3. discovery service
 * 4. load balancer : client side LB / server side LB
 * 5. security: ...
 * 6. db cluster / db parition / sharding
 * 7. scalability
 * 8. cache cluster / local cache
 * 9. monitor : Splunk(log, alarm, metric), Cloudwatch(log...), actuator, JProfiler, Jconsole
 * 10. message queue
 * ..
 *
 * how to improve restapi / application performance
 * 1. locate issues
 * 	log -> Splunk -> P99 / metrics -> 1% slowest api log
 * 	jvm issues?
 * 	database issues?
 * 	network issues?
 * 	load balancer issues?
 * 2. veritical scaling / horizontal scaling
 * 3. jvm issues
 * 	heap dump / gc algorithm / config
 * 	algorithms
 * 4. database issues
 * 	query performance -> execution plan -> index / hint / matrial view
 * 	pl/sql
 * 	cache
 * 	sharding
 * 5. message queue -> decouple servies
 *
 *
 *
 *
 *
 *
 *
 * bonus : connect services to Splunk server
 *
 *
 *
 *
 *
 */