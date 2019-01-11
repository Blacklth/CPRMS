package com.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.dbmanage.Dbmanage;
import com.vo.Author;


public class AuthorDao {
	public void insertAuthor(Author author) {
	    // 提交作者
	    Dbmanage dbmanage = new Dbmanage();
	    Connection conn = null;
	    Statement sta = null;

	    try {
	        conn = dbmanage.initDB();
	        sta = conn.createStatement();
	        int paper_number = author.getPaper_number();
	        int person_id = author.getPerson_id();
	        String is_contact = author.getIs_contact();
	    	//System.out.println(...);
	        String sql = "INSERT INTO author (person_id,paper_number,is_contact) VALUES('"
	                + person_id
	                + "','"
	                + paper_number
	                + "','"
	                + is_contact
	                + "')";
	        sta.executeUpdate(sql);
	    } catch (SQLException e) {

	        e.printStackTrace();
	    } finally {
	        // 执行完关闭数据库
	        dbmanage.closeDB(sta, conn);
	    }
	}
	public ArrayList<Author> searchPaper(int person_id) {
        // 查询论文作者
        Dbmanage dbmanage = new Dbmanage();
        Connection conn = null;
        Statement sta = null;
        ResultSet rs = null;
        ArrayList<Author> authorList = new ArrayList<Author> ();
        Author author = null;
        try {
            conn = dbmanage.initDB();
            sta = conn.createStatement();
            //System.out.println(user_account);
            // System.out.println(user_pwd);
            String sql = "SELECT * FROM author WHERE person_id = '"
                    + person_id +  "'";
            rs = sta.executeQuery(sql);
            while (rs.next()) {
                author = new Author();
                author.setPaper_number(Integer.parseInt(rs.getString("paper_number")));
                author.setPerson_id(Integer.parseInt(rs.getString("person_id")));
                author.setIs_contact(rs.getString("is_contact"));
                authorList.add(author);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        } finally {
            // 执行完关闭数据库
            dbmanage.closeDB(rs, sta, conn);
        }
        // 返回查询结果
        return authorList;
	}
	public ArrayList<Author> searchAuthor(int paper_number) {
        // 查询论文作者
        Dbmanage dbmanage = new Dbmanage();
        Connection conn = null;
        Statement sta = null;
        ResultSet rs = null;
        ArrayList<Author> authorList = new ArrayList<Author> ();
        Author author = null;
        try {
            conn = dbmanage.initDB();
            sta = conn.createStatement();
            //System.out.println(user_account);
            // System.out.println(user_pwd);
            String sql = "SELECT * FROM author WHERE paper_number = '"
                    + paper_number +  "'";
            rs = sta.executeQuery(sql);
            while (rs.next()) {
                author = new Author();
                author.setPaper_number(Integer.parseInt(rs.getString("paper_number")));
                author.setPerson_id(Integer.parseInt(rs.getString("person_id")));
                author.setIs_contact(rs.getString("is_contact"));
                authorList.add(author);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        } finally {
            // 执行完关闭数据库
            dbmanage.closeDB(rs, sta, conn);
        }
        // 返回查询结果
        return authorList;
	}
}
