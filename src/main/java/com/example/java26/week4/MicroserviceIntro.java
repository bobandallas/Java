package com.example.java26.week4;

/**
 *  homework:
 *      25 minutes
 *      1. post endpoint => insert user info into database
 *          {
 *              "provider" : {
 *                  "first_name": xx,
 *                  "last_name": xx,
 *                  "middle_name":xx,
 *                  "dob":xx,
 *              }
 *          }
 *          first_name / last_name / dob : not null
 *
 *          get => display data
 *
 *      2. endpoint: /userinfo + post
 *      3. controller + service + repository
 *      4. 2 unit test cases
 *      5. database : H2
 *   *    *    *    *    *    *    *    *    *    *    *    *    *    *    *    *    *    *
 *   horizontal scaling
 *                load balancer
 *             /        \         \
 *          node1       node2     node3(v1)
 *   vertical scaling
 *
 *   monolithic cons
 *      1. maintain / development
 *      2. scalability
 *      3. deployment
 *      4. message queue
 *      5. developing language
 *      ..
 *
 *                                          |
 *                                       gateway        ->   security service  - DB
 *                                          |
 *               employee service  <=   department service
 *                      |                           |
 *                    DB                           DB
 *
 *              每一次route都是新的http request
 *
 *   microservice pros
 *      1. scalability
 *      2. loose coupling
 *      3. deployment
 *      4. diff developing languages
 *      ..
 *   how to design microservices from scratch
 *      1. api gateway
 *          a. device
 *          b. centralize log
 *          c. co-relation id / global unique id    flow的id
 *                 DB primary + sequence
 *                 UUID
 *                 snowflake   64 bit = long
 *                      [time stamp][machine id][process id][8 digit serial id / number]
 *                      0 ~ 1111 1111
 *          d. redirect request to security service
 *          e. rate limiter  （通常来说 1。 服务器最大处理多少request 2。每个id最大request）
 *                 cache
 *                  a. queue [t1, t2, t3, t4, t5]  每个id生成一个queue，统计时间点之间的差别
 *                  b. key range : value           time range 来 count计算。 时间interval 内的访问数量
 *                     range1 -> count
 *                     range2 -> count
 *
 *                         [     3][1     ]
 *                             [      ]        这样看没法访问
 *                        -----------------------> timestamp
 *                  c. token bucket  类似于水桶，速率控制会麻烦
 *
 *                        drop token 1token/sec
 *                        |
 *                      \   /
 *                       \_/
 *                        |
 *                        --> token -> user
 *                  d. key : value pair
 *                    time -> count
 *                             2 [    1 ]
 *                        -----|-----|-------------> timestamp
 *
 *      2. centralized security service
 *      3. configuration service   集中管理配置文件，优点在于when message queue + notification 修改 直接refresh diff configuration
 *              安全， 修改起来方便
 *            centralize application properties
 *            services -> actuator + refresh application
 *      4. service discovery / registration service
 *
 *          如果多个port拥有相同的service如何去选呢？
 *          
 *          department -> restTemplate.getForObject(employee-service/endpoint, xx)
 *                                  |
 *                     send request to  discovery service(key: value / service-name: location)
 *                                  |
 *                   get response employee-service: ip:8000/8001/8002
 *                                  |
 *                        load balancing library choose one of them based on rule
 *                                  |
 *                      restTemplate.getForObject(ip1:8001, xx)
 *
 *      5. DB cluster + global transaction
 *      6. message queue
 *      7. health monitor
 *      8. log monitor
 *      9. documentation
 *      10. deployment ci/cd + docker
 *
 *      ...clock / time stamp service
 *
 *
 *  Next Monday: 1:30pm cdt
 *     1. finish introduction
 *     2. explain spring cloud project
 */