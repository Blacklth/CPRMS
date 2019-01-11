package com.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.dbmanage.Dbmanage;
import com.vo.Prefers;

public class PrefersDao {
	public void insertPrefers(Prefers prefers) {
	    // 添加偏好
	    Dbmanage dbmanage = new Dbmanage();
	    Connection conn = null;
	    Statement sta = null;

	    try {
	        conn = dbmanage.initDB();
	        sta = conn.createStatement();
	        String pc_code = prefers.getPc_code();
	        int paper_number = prefers.getPaper_number();
	        int preference = prefers.getPreference();
	    	//System.out.println(..);
	        String sql = "INSERT INTO prefers (pc_code,paper_number,preference) VALUES('"
	                + pc_code
	                + "','"
	                + paper_number
	                + "','"
	                + preference
	                + "')";
	        sta.executeUpdate(sql);
	    } catch (SQLException e) {

	        e.printStackTrace();
	    } finally {
	        // 执行完关闭数据库
	        dbmanage.closeDB(sta, conn);
	    }
	}
	public void setPrefers(Prefers prefers) {
	    // 修改偏好
	    Dbmanage dbmanage = new Dbmanage();
	    Connection conn = null;
	    Statement sta = null;

	    try {
	        conn = dbmanage.initDB();
	        sta = conn.createStatement();
	        String pc_code = prefers.getPc_code();
	        int paper_number = prefers.getPaper_number();
	        int preference = prefers.getPreference();
	    	//System.out.println(..);
	        String sql = "UPDATE  prefers  SET preference = "
	                + preference
	                + " WHERE pc_code = '"
	                + pc_code
	                + "' and paper_number = "
	                + paper_number;
	        sta.executeUpdate(sql);
	    } catch (SQLException e) {

	        e.printStackTrace();
	    } finally {
	        // 执行完关闭数据库
	        dbmanage.closeDB(sta, conn);
	    }
	}
	public ArrayList<Prefers> searchPrefers(int paper_number) {
        // 查询偏好
        Dbmanage dbmanage = new Dbmanage();
        Connection conn = null;
        Statement sta = null;
        ResultSet rs = null;
        ArrayList<Prefers> prefersList = new ArrayList<Prefers> ();
        Prefers prefers = null;
        try {
            conn = dbmanage.initDB();
            sta = conn.createStatement();
            //System.out.println(user_account);
            // System.out.println(user_pwd);
            String sql = "SELECT * FROM prefers WHERE paper_number = '"
            		+paper_number
            		+"' and pc_code not in ( SELECT pc_code FROM assigned_to  WHERE paper_number = '"
            		+paper_number
            		+ "') order by preference desc";
            rs = sta.executeQuery(sql);
            while (rs.next()) {
                prefers = new Prefers();
                prefers.setPaper_number(Integer.parseInt(rs.getString("paper_number")));
                prefers.setPc_code(rs.getString("pc_code"));
                prefers.setPreference(Integer.parseInt(rs.getString("preference")));
                prefersList.add(prefers);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        } finally {
            // 执行完关闭数据库
            dbmanage.closeDB(rs, sta, conn);
        }
        // 返回查询结果
        return prefersList;
	}
	public ArrayList<Prefers> searchPrefers(String pc_code) {
        // 查询偏好
        Dbmanage dbmanage = new Dbmanage();
        Connection conn = null;
        Statement sta = null;
        ResultSet rs = null;
        ArrayList<Prefers> prefersList = new ArrayList<Prefers> ();
        Prefers prefers = null;
        try {
            conn = dbmanage.initDB();
            sta = conn.createStatement();
            //System.out.println(user_account);
            // System.out.println(user_pwd);
            String sql = "SELECT * FROM prefers WHERE pc_code = '"
            		+pc_code
            		+"'";
            rs = sta.executeQuery(sql);
            while (rs.next()) {
                prefers = new Prefers();
                prefers.setPaper_number(Integer.parseInt(rs.getString("paper_number")));
                prefers.setPc_code(rs.getString("pc_code"));
                prefers.setPreference(Integer.parseInt(rs.getString("preference")));
                prefersList.add(prefers);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        } finally {
            // 执行完关闭数据库
            dbmanage.closeDB(rs, sta, conn);
        }
        // 返回查询结果
        return prefersList;
	}
	public ArrayList<Prefers> searchUnPrefers(String pc_code) {
        // 查询未设置偏好
        Dbmanage dbmanage = new Dbmanage();
        Connection conn = null;
        Statement sta = null;
        ResultSet rs = null;
        ArrayList<Prefers> prefersList = new ArrayList<Prefers> ();
        Prefers prefers = null;
        try {
            conn = dbmanage.initDB();
            sta = conn.createStatement();
            //System.out.println(user_account);
            // System.out.println(user_pwd);
            String sql = "SELECT paper_number FROM paper WHERE paper_number NOT IN (SELECT paper_number FROM prefers WHERE pc_code = '"
            		+pc_code
            		+"' )";
            rs = sta.executeQuery(sql);
            while (rs.next()) {
                prefers = new Prefers();
                prefers.setPaper_number(Integer.parseInt(rs.getString("paper_number")));
                //prefers.setPc_code(rs.getString("pc_code"));
                //prefers.setPreference(Integer.parseInt(rs.getString("preference")));
                prefersList.add(prefers);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        } finally {
            // 执行完关闭数据库
            dbmanage.closeDB(rs, sta, conn);
        }
        // 返回查询结果
        return prefersList;
	}
}
