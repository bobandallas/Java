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
 *
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
 *          c. co-relation id / global unique id
 *                 DB primary + sequence
 *                 UUID
 *                 snowflake   64 bit = long
 *                      [time stamp][machine id][process id][8 digit serial id / number]
 *                      0 ~ 1111 1111
 *          d. redirect request to security service
 *          e. rate limiter
 *                 cache
 *                  a. queue [t1, t2, t3, t4, t5]
 *                  b. key range : value
 *                     range1 -> count
 *                     range2 -> count
 *
 *                         [     3][1     ]
 *                             [      ]
 *                        -----------------------> timestamp
 *                  c. token bucket
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
 *      3. configuration service
 *            centralize application properties
 *            services -> actuator + refresh application
 *      4. service discovery / registration service
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