package com.example.java26.week3;

import java.sql.*;


/**
 * Table design
 *          1 - 1
 *   dept   1 - m   emp
 *   id(pk)         id(pk)
 *                  d_id(fk)
 *
 *   dept   m - m   emp
 *   id(pk)         id(pk)
 *
 *
 *   // many to many 的实现 用 associate table来做
 *   dept   1 - m   dept_emp    m - 1   emp
 *   id(pk)         id(pk)              id(pk)
 *                  d_id(fk)
 *                  e_id(fk)
 *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *
 *  University application
 *  student, gpa
 *           math-grade
 *           ..   grade
 *  class, info
 *
 *
 *
 * Student: sid(pk), gpa
 * Grade: sid_cid(pk), sid(fk), cid(fk), grade, semester
 * Course: cid(pk), info
 *--------
 * Student: studentId, studentName
 * Grade: (studentId, classId)(pk), grade
 * ClassInfo: classId, className, semester
 *
 *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *
 *   Super Key(candidate key (primary key))
 *      candidate key => 唯一识别每一行的column，最小的combination
 *      super key 就是candidate key  加上extra column组成的
 *   normalization 建立在已经存在的table上面，随着normaliazation的提升，牺牲join query的效率，表越来越多，减少硬盘上数据存储量
 *
 *   1st : 拆分合并的column
 *      Employee
 *      id,         name
 *      1,      Alex,m,LastName
 *      2,          LastName
 *   2nd : non-prime attributes fully depend on prime attribute
 *          X -> Y          X唯一指向Y
 *      Employee
 *      id(pk),   name
 *
 *      Grade:
 *      (studentId, classId), grade, semester
 *         1         A101     B+      2022Fall
 *         1         A101     A       2023Spring
 *
 *      Book_Author
 *        book_id,  author_id,  book_name, author_name
 *          1        A1          Java       Jerry
 *          1        A2          Java       Tom
 *          2        A2          C#         Tom
 *
 *   3rd : no transitive relations (address info -> address id -> id存在传递关系不被允许)
 *      Employee
 *      id(pk),   name, address_id, address_info
 *
 *    *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *
 *
 *  public ip
 *
 *  0.0.0.0 ~ 255.255.255.255
 */


/**
 *  orm
 *  hql
 *  connection pool
 *  lazy loading(N + 1 problem) vs eager loading
 *  first level cache , 2nd level cache
 *  hibernate vs jpa
 */



public class JdbcExample {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:8080/database_name";

    //  Database credentials
    static final String USER = "username";
    static final String PASS = "password";

    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            //STEP 2: Register JDBC driver -> DriverManager
            Class.forName(JDBC_DRIVER); //static block

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            conn.setAutoCommit(false);
            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            String username = "";
            String password = "";
            String sql1 = "SELECT .... first, last, age FROM Employees WHERE username = "
                    + username + " AND password = xx + " + password;
            String sql2 = "SELECT ... FROM ... WHERE val1 = not null AND val2 = col2 OR val5 = col5.....";
                /*
                 */
            stmt = conn.prepareStatement(sql1);
            ResultSet rs = stmt.executeQuery();

            //STEP 5: Extract data from result set
            //30 columns -> new instance , Student / List<Student> / Set<Student>
            //List<Student> studentList = executeQuery(query);
            while(rs.next()){
                //Retrieve by column name
                int id  = rs.getInt("id");
                int age = rs.getInt("age");
                String first = rs.getString("first");
                String last = rs.getString("last");

                //Display values
                System.out.print("ID: " + id);
                System.out.print(", Age: " + age);
                System.out.print(", First: " + first);
                System.out.println(", Last: " + last);
            }

            conn.commit();
            //STEP 6: Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        } catch(SQLException se) {
            //Handle errors for JDBC
            try {
                conn.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            //log => file
        } catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try
    }//end main
}