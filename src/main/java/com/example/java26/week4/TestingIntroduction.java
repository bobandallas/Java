package com.example.java26.week4;

class Solution {
    /**
     * question: two sum
     * input:   List<Integer> / int[] / .. ,   int target
     * output:  boolean / List<int[]> / List<List<Integer>> / int[] / ..
     *
     * cases:
     *      1. (null, target)
     *      2. negative value?
     *      3. overflow ? long?
     *      4. return list sorted?
     *      5. int[] ? index ? value? ascending order
     *      6. List<Integer> [null, 5, 5]
     *      7. time complexity ? space complexity
     *      8. ...
     *
     */

    public static void main(String[] args) {
        try {
            twoSum(null, 0);
            System.out.println("exception test case failed");
        } catch (IllegalArgumentException e) {
            System.out.println("exception test case passed");
        } catch (RuntimeException e) {
            System.out.println("wrong exception");
        }
        System.out.println(twoSum(new int[]{5, 7}, 12) ? "xx" : "xx");
    }

    public static boolean twoSum(int[] arr, int target) {
        if(arr == null) {
            throw new IllegalArgumentException("xx");
        }
        return false;
    }
}

/**
 *  svn : version
 *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *
 *  git
 *      v1.0 -> v1.1 -> v1.2  -> v1.3 remote repository
 *                                  |
 *
 *                               developer v1.3 local repository
 *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *
 *  git
 *
 *      master branch -> 1.0 -> 1.1 -> 1.2
 *                                            \
 *      release branch -> 1.0 -> 1.1 -> 1.2 -> 2.0  (UAT / pre-production) -> 2.1
 *                                                                          /
 *      development branch  -> ... -> 2.0 -> 2.1 commit1 -> 2.1 commit2 ..
 *                                                  \               /
 *                                              feature branch -> local development env
 *
 *      pull request approved -> merge fail -> handle conflict -> merge success
 *      pull request approved -> merge fail -> handle conflict -> pull request review approved -> merge success
 *
 *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *
 *  development branch jenkins pipeline (pipeline / cicd)
 *
 *     feature merged to development branch -> trigger jenkins -> build -> test -> report -> package -> deploy to development env server
 *
 *     feature merged to development branch -> trigger jenkins -> build -> test -> report -> package -> deploy to temporary server -> automation test -> deploy to development server
 *
 *     feature merged to development branch -> trigger jenkins -> build -> test -> report -> package -> deploy to development server -> automation test -> failed , rollback to previous version
 *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *
 *  Test
 *      1. unit test : Junit + Mockito
 *          service -> Mock(repository)
 *                      when(repository.method(a)).thenReturn(b)
 *          service.func1(a)
 *      2. integration test : MockMVC/ Postman      (完整的启动)
 *      3. functional test  (dataflow 测试总的)
 *      4. performance test / pressure test : Jmeter
 *      5. security test : ..
 *      6. regression test  : 测试之前的func
 *      7. automation test : selenium 自动模拟button点来点去
 *      ...
 *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *
 *  Homework
 *     unit test for : FizzBuzz => System.out.println();
 *
 *  Tomorrow:
 *      1. example of fizz buzz test
 *      2. how to use mockito
 *      3. mockito with service layer
 *      4. security
 *
 *
 *  git commands
 *  git checkout branch
 *  git add / git commit / git push / git pull ...
 */