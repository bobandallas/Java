package com.example.java26.week3;

/**
 * 如何1010101转换成char等等
 * ASCII: standard 0 ~ 127
 * 你 -> 1245 -> 010101.. -> router(0 ~ 127) -> cable -> router -> server -> 288 -> 我  (如果不encode router(0 ~ 127) 会skip 一些字段)
 * 发送 HTTP 需要包装[ip header][tcp header][Http Header][data] 数据有限制，如果太大分段传输 tcp包含port
 *
 *
 *      DNS server (key: value) (domain name: ip)
 *                  /
 *
 *  user source ip   ->    1010101010101    ->  domain name(destination ip) application
 *  user source ip   <-    0101010110101    <-  domain name(destination ip) application
 *
 *    DHCP(mac address <-> private ip)                                                   socket build tcp connection   ->  assign connection thread pool -> get one thread -> read http info -> GetEmployeeClass
 *     |                                                                                    |
 *    user(private ip + port)  ->  NAT(public ip pool)   -> cable..   ->   router ->   domain name(destination ip) server
 *                                  |
 *    [private ip + port][app public ip + app port] -> [public ip + port][app public ip + app port]把局域网内内部的private ip 被NAT(Network address transfer)替换为public
 *
 *  http 流程
 *  1.  check ip from cache
 *      find ip from domain name (DNS server) -> cache ip with TTL(time to live)
 *  2. socket build tcp connection : [source ip + source port][destination ip + destination port]
 *  3. request : send ip packet : [ip header][tcp header][Http Header][data]
 *  4. get response
 *  5. disconnect
 *
 *
 *  OSI
 *  7 Application layer: http
 *  6 Presentation layer: SSL/TLS
 *  5 Session layer: socket
 *  4 Transport layer: TCP / UDP       UDP类似于广播型，丢包了不管 ， TCP会报告丢包
 *      TCP (SEQ, ACK)
 *      3 ways handshake
 *
 *      user(SEQ)                        application
 *          flag = 1 Seq = 0, data = 1 ->
 *
 *                <-  ACK = 1
 *
 *                Seq = 1, data = 0 ->
 *
 *                Seq = 1, data = 50 ->
 *
 *                <- ACK = 51
 *
 *                Seq = 51, data = 30 ->
 *
 *                <- ACK = 81
 *
 *
 *    4 ways disconnection
 *                   disconnect ->
 *                 <- wait a sec
 *                 <- ok
 *                      ok ->
 *
 *
 *  3 Network layer: IP
 *  2 Data link layer: Ethernet
 *  1 Physical layer: cable ..
 *
 *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *
 *              client
 *               |
 *          load balancer(public ip)
 *          /    |      \
 *       node1  node2  node3
 *          \    |    /
 *           Database
 *
 *   client -> server
 *       <- session id
 *   cookie[Session id] -> server
 *
 *   session cookie
 *
 *   session在不同node中会造成错误，策略：redits global session or stick session（固定访问node）
 *
 *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *
 *   Http :
 *      1. http method: get / post / delete / put, patch
 *      2. http status code: 2xx success, 4xx client side error, 5xx server side error 3xx redirect
 *      3. http header: content-type / accept / authorization / ...
 *      4. http endpoint: /xx/xxx
 *
 *   rest :
 *      1. based on http
 *      2. stateless 不会用上次request的info
 *      3. /noun （endpoint 用名词）
 *      4. xml / json
 *   *      *      *      *      *      *      *      *      *      *      *      *
 *   Employee:
 *      1. retrieve all employees
 *          endpoint: /employees?name=xx&age= -> request parameter
 *          http method: get
 *          status code: 200 ok / 400 bad request / 500 internal server error
 *          response body:
 *              {
 *                  "data": [
 *                      {
 *                          "id": xx,
 *                          "age": xx,
 *                          "name": xx
 *                      },
 *                      {
 *                          ..
 *                      }
 *                  ]
 *              }
 *      2. get employee by id
 *          endpoint: /employees/{id} -> path variable
 *          method: get
 *          response body:
 *              {
 *                  "data": {
 *                              "id": xx,
 *                              "age": xx,
 *                              "name": xx
 *                          }
 *              }
 *          ..
 *      3. create new emp
 *          endpoint: /employees
 *          http method: post
 *          status code: 201 Created
 *          request body:
 *              {
 *                  "name": xx,
 *                  "age": xx
 *              }
 *          response body:
 *              {
 *                  "id"/"relocation-id" :xx
 *              }
 *      4. update emp
 *          endpoint: /employees/{id}
 *          http method: patch: partial update / put : update entire resource
 *          status code : 204 No content OK / xx
 *          request body:
 *              {
 *                  "name": xx,
 *                  "age": xx
 *              }
 *
 *  idempotent:幂等REST API在REST API的上下文中,当生成多个相同的请求与生成单个请求具有相同的效果时 - 然后该REST API称为幂等
 *  *   *   *   *  *   *    *  *   *   *   *  *   **  *   *   *   *  *   **  *   *   *   *  *   **  *   *   *   *  *   **  *   *   *   *  *   **  *   *   *   *  *   *
 *  古老版本
 *  client -> request -> server -> connection -> thread pool
 *                          -> thread -> endpoint + http method -> servlet class
 *
 * Spring MVC old impl
 *   *  client -> request -> server -> connection -> thread pool
 *  *                          -> thread -> dispatcher servlet(/*) -> handler mapping -> controller -> func1(/employees + get) ,  func2(/employees + post)
 *                                                |
 *                                             view resolver
 *                                               |
 *                                             jsp / html （直接返回view而不是json）
 * Spring MVC rest impl 做成了前后端分离
 *   *  client -> request -> server -> connection -> thread pool
 *  *                          -> thread -> dispatcher servlet(/*) -> handler mapping -> controller(@ResponseBody) -> func1(/employees + get) ,  func2(/employees + post)
 *                                                |
 *                                          Http message converter
 *                                               |
 *                                          jackson library
 *                                              |
 *                                          obj -> json
 *   *    *    *   *    *   *   *    *   *   *    *   *   *    *   *   *    *
 *  tomorrow : 11:00 am CDT
 *
 *  Spring Boot rest api example
 */