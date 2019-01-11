package com.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.vo.*;
import com.dbmanage.*;

public class PersonDao {
	
	public void insertUser(Person person) {
	    // 添加人员
	    Dbmanage dbmanage = new Dbmanage();
	    Connection conn = null;
	    Statement sta = null;

	    try {
	        conn = dbmanage.initDB();
	        sta = conn.createStatement();
	    	String email = person.getEmail();
	    	String title = person.getTitle();
	    	String name = person.getName();
	    	String institution = person.getInstitution();
	    	String telephone = person.getTelephone();
	    	//System.out.println(user_nickname);
	    	//System.out.println(user_account);
	    	//System.out.println(user_pwd);
	        String sql = "INSERT INTO person (email,title,name,institution,telephone_number) VALUES('"
	                + email
	                + "','"
	                + title
	                + "','"
	                + name
	                + "','"
	                + institution
	                + "','"
	                + telephone
	                + "')";
	        sta.executeUpdate(sql);
	    } catch (SQLException e) {

	        e.printStackTrace();
	    } finally {
	        // 执行完关闭数据库
	        dbmanage.closeDB(sta, conn);
	    }
	}
	
	public Person searchUser(String email, String name) {
        // 查找人员
        Dbmanage dbmanage = new Dbmanage();
        Connection conn = null;
        Statement sta = null;
        ResultSet rs = null;
        Person person = null;
        try {
            conn = dbmanage.initDB();
            sta = conn.createStatement();
            //System.out.println(user_account);
            // System.out.println(user_pwd);
            String sql = "SELECT * FROM person WHERE email = '"
                    + email + "' AND name= '" + name + "'";
            rs = sta.executeQuery(sql);
            while (rs.next()) {
                person = new Person();
                person.setPerson_id(Integer.parseInt(rs.getString("person_id")));
                person.setEmail(rs.getString("email"));
                person.setName(rs.getString("name"));
                // System.out.println(person.getEmail());
                //System.out.println(person.getName());
            }

        } catch (SQLException e) {

            e.printStackTrace();
        } finally {
            // 执行完关闭数据库
            dbmanage.closeDB(rs, sta, conn);
        }
        // 返回查询结果
        return person;
	}
	public Person searchUser(String email) {
        // 查找人员
        Dbmanage dbmanage = new Dbmanage();
        Connection conn = null;
        Statement sta = null;
        ResultSet rs = null;
        Person person= null;
        try {
            conn = dbmanage.initDB();
            sta = conn.createStatement();
            //System.out.println(user_account);
            // System.out.println(user_pwd);
            String sql = "SELECT * FROM person WHERE email = '"
                    + email +  "'";
            rs = sta.executeQuery(sql);
            while (rs.next()) {
                person = new Person();
                person.setPerson_id(Integer.parseInt(rs.getString("person_id")));
                person.setEmail(rs.getString("email"));
                person.setTitle(rs.getString("title"));
                person.setName(rs.getString("name"));
                person.setInstitution(rs.getString("institution"));
                person.setTelephone(rs.getString("telephone_number"));
            }

        } catch (SQLException e) {

            e.printStackTrace();
        } finally {
            // 执行完关闭数据库
            dbmanage.closeDB(rs, sta, conn);
        }
        // 返回查询结果
        return person;
	}
	public Person searchUser(int person_id) {
        // 查找人员
        Dbmanage dbmanage = new Dbmanage();
        Connection conn = null;
        Statement sta = null;
        ResultSet rs = null;
        Person person= null;
        try {
            conn = dbmanage.initDB();
            sta = conn.createStatement();
            //System.out.println(user_account);
            // System.out.println(user_pwd);
            String sql = "SELECT * FROM person WHERE person_id = '"
                    + person_id +  "'";
            rs = sta.executeQuery(sql);
            while (rs.next()) {
                person = new Person();
                person.setPerson_id(Integer.parseInt(rs.getString("person_id")));
                person.setEmail(rs.getString("email"));
                person.setTitle(rs.getString("title"));
                person.setName(rs.getString("name"));
                person.setInstitution(rs.getString("institution"));
                person.setTelephone(rs.getString("telephone_number"));
            }

        } catch (SQLException e) {

            e.printStackTrace();
        } finally {
            // 执行完关闭数据库
            dbmanage.closeDB(rs, sta, conn);
        }
        // 返回查询结果
        return person;
	}
}
