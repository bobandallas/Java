package com.example.java26.week3.rest.demo2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Rest2DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(Rest2DemoApplication.class, args);
    }
}
/**
 *  TODO
 *      1. exception
 *      2. log
 *      3. documentation (swagger)
 *      4. rest template timeout
 *      5. 3rd party api no response
 *          a. retry(timeout + times)
 *          b. cache / database + scheduler
 *              user -read-> cache / db  -if cannot find data-> 3rd party api
 *          c. circuit breaker 熔断器
 *              user -> circuit breaker(on) -> return default message
 *                             | (backend thread)
 *                             --> try to visit 3rd party api -> cannot connect -> do nothing
 *                                          |
 *                                          --> can visit -> turn it off
 *
 *
 * * * * * * * * * * * * * * * * * * * * *
 * TDD(test driven development)
 *      1. requirement
 *      2. corner cases
 *      3. design flow / OOD / interface / abstract class / algorithm / api
 *      4. write unit test cases + integration test cases
 *      5. impl TODO
 *      6. run test cases
 *      7. git push -> pull request code review -> merge -> trigger jenkins pipeline -> development env / qa env / dev env
 *
 *
 * * * * * * * * * * * * * * * * * * * *
 * /user/{id @PathVariable}/courses?x=x @RequestParam
 *
 * /user/{id}/courses/{c_id}
 * * * * * * * * * * * * * * * * * * * *
 * 1. microservice
 *         user
 *          |
 *      serverA      <->      serverB(3rd party company)
 *
 *
 *         user
 *          |
 *         serverA
 *          /     \
 *      serverB   serverC
 * * * * * * * * * * * * * * * * * * * *
 *
 *   /teacher/{t_id}/student/{s_id}
 *   /junction-table/{id}
 *   /junction-table?xxx=
 *
 */