package com.dbmanage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Dbmanage {
    public Connection initDB() {
        // 初始化数据库连接方法
        Connection conn = null;
        // 创建一个Connection句柄
        String driverName = "com.mysql.cj.jdbc.Driver";
        String userName = "root";
        String userPasswd = "123456";
        String dbName = "cprms";
        String url = "jdbc:mysql://localhost:3306/" + dbName + "?user="
                + userName + "&password=" + userPasswd+"&serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8&useSSL=false";
        try {
            
        	Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url);
           
        } catch (ClassNotFoundException e) {

            e.printStackTrace();
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return conn;
        // 返回数据库连接
    }

    public void closeDB(Statement sta, Connection conn) {
        // 关闭数据库连接（无结果集）
        try {
            sta.close();
            conn.close();
        } catch (SQLException e) {

            e.printStackTrace();
        }

    }

    public void closeDB(ResultSet rs, Statement sta, Connection conn) {
        // 关闭数据库连接（有结果集）
        try {
            rs.close();
            sta.close();
            conn.close();
        } catch (SQLException e) {

            e.printStackTrace();
        }

    }
}
