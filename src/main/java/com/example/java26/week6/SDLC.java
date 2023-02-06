package com.example.java26.week6;

/**
 *  Agile - Scrum(Sprint = 2 weeks / Production backlog = TODO List)
 *      1. Sprint planning meeting
 *          stories / tickets
 *          points = hours / difficulties(fibonacci numbers)
 *      2. Daily stand up meeting(10min ~ 30min)
 *      3. Retrospective meeting / Sprint review meeting
 *      4. Demo review meeting (every few sprints)
 *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *
 *  Daily work
 *      1. meeting
 *      2. TDD
 *          ..
 *      3. PR (pull request code review)
 *      4. production support
 *
 *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *
 *  production support
 *      master --------v1----------------------------------------------------------------
 *                      \   / pr
 *      hotfix branch    ---
 *                          \ pr
 *      release branch ----------------------------------------------v3--------
 *
 *      development branch ------------
 *                             \     /
 *      feature branch          -----
 *
 *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *
 *  Dev (local env) - QA (remote env1) - UAT/Release (remote env2) - Prod (remote env3)
 *  Jenkins
 *                     docker image / jar / war
 *                                  |
 *      build - test - report - package - deploy - QA
 *                       |
 *                    security
 *                    code coverage
 *                     |
 *                   Sonar qube
 *
 *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *
 *   Manager
 *   Scrum master
 *   DBA
 *
 *   Backend / Frontend / Full stack
 *   QA
 *   BA
 */