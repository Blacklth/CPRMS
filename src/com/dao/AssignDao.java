package com.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.dbmanage.Dbmanage;
import com.vo.Assign;
import com.vo.Paper;

public class AssignDao {
	public ArrayList <Assign> searchAssignedPaper() {
        // 查询已分配论文
        Dbmanage dbmanage = new Dbmanage();
        Connection conn = null;
        Statement sta = null;
        ResultSet rs = null;
        ArrayList <Assign>  assignList = new ArrayList <Assign> ();
        Assign assign = null;
        try {
            conn = dbmanage.initDB();
            sta = conn.createStatement();
            //System.out.println(user_account);
            String sql = "SELECT * FROM assigned_to order by paper_number ";
            rs = sta.executeQuery(sql);
            while (rs.next()) {
                assign= new Assign();
                assign.setPaper_number(Integer.parseInt(rs.getString("paper_number")));
                assign.setPc_code(rs.getString("pc_code"));
                assignList.add(assign);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        } finally {
            // 执行完关闭数据库
            dbmanage.closeDB(rs, sta, conn);
        }
        // 返回查询结果
        return assignList;
	}
	public ArrayList <Assign> searchAssignedPaper(int paper_number) {
        // 查询已分配论文
        Dbmanage dbmanage = new Dbmanage();
        Connection conn = null;
        Statement sta = null;
        ResultSet rs = null;
        ArrayList <Assign>  assignList = new ArrayList <Assign> ();
        Assign assign = null;
        try {
            conn = dbmanage.initDB();
            sta = conn.createStatement();
            //System.out.println(user_account);
            // System.out.println(user_pwd);
            String sql = "SELECT * FROM assigned_to WHERE paper_number = '"
            		+ paper_number
            		+"' order by paper_number ";
            rs = sta.executeQuery(sql);
            while (rs.next()) {
                assign= new Assign();
                assign.setPaper_number(Integer.parseInt(rs.getString("paper_number")));
                assign.setPc_code(rs.getString("pc_code"));
                assignList.add(assign);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        } finally {
            // 执行完关闭数据库
            dbmanage.closeDB(rs, sta, conn);
        }
        // 返回查询结果
        return assignList;
	}
	public ArrayList <Paper> searchUnAssignedPaper() {
        // 查询未分配论文
        Dbmanage dbmanage = new Dbmanage();
        Connection conn = null;
        Statement sta = null;
        ResultSet rs = null;
        ArrayList <Paper>  paperList = new ArrayList <Paper> ();
        Paper paper = null;
        try {
            conn = dbmanage.initDB();
            sta = conn.createStatement();
            //System.out.println(user_account);
            // System.out.println(user_pwd);
            String sql = "SELECT paper_number FROM paper WHERE paper_number NOT IN ( SELECT paper_number FROM assigned_to)";
            rs = sta.executeQuery(sql);
            while (rs.next()) {
                paper = new Paper();
                paper.setPaper_number(Integer.parseInt(rs.getString("paper_number")));
                paperList.add(paper);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        } finally {
            // 执行完关闭数据库
            dbmanage.closeDB(rs, sta, conn);
        }
        // 返回查询结果
        return paperList;
	}
	public void assigPaper(String pc_code,int paper_number) {
        // 分配论文
        Dbmanage dbmanage = new Dbmanage();
        Connection conn = null;
        Statement sta = null;
        ResultSet rs = null;
        try {
            conn = dbmanage.initDB();
            sta = conn.createStatement();
            //System.out.println(user_account);
            // System.out.println(user_pwd);
            String sql = "INSERT INTO assigned_to (pc_code,paper_number) VALUES('"
	                + pc_code
	                + "','"
	                + paper_number
	                + "')";
	        sta.executeUpdate(sql);

        } catch (SQLException e) {

            e.printStackTrace();
        } finally {
            // 执行完关闭数据库
            dbmanage.closeDB( sta, conn);
        }
	}
	public void deleteAssign(int paper_number,String pc_code) {
	    // 删除分配
	    Dbmanage dbmanage = new Dbmanage();
	    Connection conn = null;
	    Statement sta = null;

	    try {
	        conn = dbmanage.initDB();
	        sta = conn.createStatement();
	    	//System.out.println(user_pwd);
	        String sql = "delete from assigned_to where paper_number ='"
	                + paper_number
	                + "' and pc_code = '"
	        		+pc_code
	        		+"'";
	        sta.executeUpdate(sql);
	    } catch (SQLException e) {

	        e.printStackTrace();
	    } finally {
	        // 执行完关闭数据库
	        dbmanage.closeDB(sta, conn);
	    }
	}
}
