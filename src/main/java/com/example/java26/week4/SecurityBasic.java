package com.example.java26.week4;


import java.util.PriorityQueue;

/**
 *  rest api security
 *  1. authentication -> 401 身份验证失败
 *  2. authorization -> 403 / role verification failed
 *      JWT = json web token
 *      header.payload.signature      （header会存jwt算法， payload包含role，exprie day  ； signature把前面两个加密）意味着token丢了别人可以找到payload信息
 *      encode(header.payload.encrypt(header.payload))
 *
 *     OAuth2.0
 *      1. implicit flow:  client -> our server(security center)
 *      2. explicit flow:
 *              client -> 3rd party company(security center)
 *                 |
 *               our server
 *
 *          Authentication
 *          a. client visit our server
 *          b. server redirect your page to 3rd party login page
 *          c. 3rd party server redirect your result to an url (authentication code)
 *          d. client send authentication code to our server
 *          e. our server verify authentication code with 3rd party company
 *          Authorization
 *          f. our server generates JWT to client
 *
 *
 *  3. https = http + ssl / tls                 https 会加密传输数据
 *              certificate authority (CA)
 *
 *      client                              server(certificate)(private key + public key)
 *                  -> hi ->
 *              <- certificate + public key
 *  random string  -> public key[random string] ->
 *                <-  hash(random string)
 *                  generate symmetric key
 *                  -> symmetric key[data] ->
 *                  <- symmetric key[data] <-
 *  4. SQL injection / Log injection
 *  5. CSRF
 *      如果并不是主动打开browser，通过link弹出默认browser存了session带着sessionid cookie访问后端。
 *  6. CORS
 *        pre-flight request ->
 *      Bmazon -> Amazon
 *  7. DDOS
 *  8. data security
 *  ..
 *
 *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *
 *   JWT/Token Filter -> UsernamePasswordAuthenticationFilter -> controller
 *
 *   Authentication
 *   1. UsernamePasswordAuthenticationFilter
 *      read username password from form data
 *   2. Dao...Provider (UserDetailsService interface)
 *   3. generate jwt token in (successful handler interface)
 *
 *   Authorization
 *   1. write customized JWT/Token Filter
 *      read token from header
 *      analyze token
 *      if valid -> save user info into Security Context(ThreadLocal)  记得清除
 *
 *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *
 *  microservice, spring cloud
 *  1. introduction
 *  2. run spring cloud
 */


class MyTest {
    public static void main(String[] args) {
        PriorityQueue<Double> pq = new PriorityQueue<>((a, b) -> Double.compare(b, a));
        pq.offer(5.9);
        System.out.println(pq);
    }
}