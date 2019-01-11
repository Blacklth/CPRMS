package com.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.dbmanage.Dbmanage;
import com.vo.Paper;


public class PaperDao {
	public void insertAbs(Paper paper) {
	    // �ύժҪ
	    Dbmanage dbmanage = new Dbmanage();
	    Connection conn = null;
	    Statement sta = null;

	    try {
	        conn = dbmanage.initDB();
	        sta = conn.createStatement();
	    	String title = paper.getTitle();
	    	String abs = paper.getAbs();
	    	String type = paper.getPaper_type();
	    	String isPc = paper.getIs_pc();
	    	//System.out.println(...);
	        String sql = "INSERT INTO paper (title,paper_type,abstract,is_pc) VALUES('"
	                + title
	                + "','"
	                + type
	                + "','"
	                + abs
	                + "','"
	                + isPc
	                + "')";
	        sta.executeUpdate(sql);
	    } catch (SQLException e) {

	        e.printStackTrace();
	    } finally {
	        // ִ����ر����ݿ�
	        dbmanage.closeDB(sta, conn);
	    }
	}
	public Paper searchPaper(String title) {
        // ��ѯ����
        Dbmanage dbmanage = new Dbmanage();
        Connection conn = null;
        Statement sta = null;
        ResultSet rs = null;
        Paper paper= null;
        try {
            conn = dbmanage.initDB();
            sta = conn.createStatement();
            //System.out.println(user_account);
            // System.out.println(user_pwd);
            String sql = "SELECT * FROM paper WHERE title = '"
                    + title +  "'";
            rs = sta.executeQuery(sql);
            while (rs.next()) {
                paper= new Paper();
                paper.setPaper_number(Integer.parseInt(rs.getString("paper_number")));
                paper.setTitle(rs.getString("title"));
                paper.setDecision(rs.getString("decision"));
                paper.setAbs(rs.getString("abstract"));
                paper.setPaper_type(rs.getString("paper_type"));
                paper.setIs_pc(rs.getString("is_pc"));
            }

        } catch (SQLException e) {

            e.printStackTrace();
        } finally {
            // ִ����ر����ݿ�
            dbmanage.closeDB(rs, sta, conn);
        }
        // ���ز�ѯ���
        return paper;
	}
	public ArrayList<Paper> searchAllPaper() {
        // ��ѯ��������
        Dbmanage dbmanage = new Dbmanage();
        Connection conn = null;
        Statement sta = null;
        ResultSet rs = null;
        ArrayList<Paper> paperList = new ArrayList<Paper> ();;
        Paper paper= null;
        try {
            conn = dbmanage.initDB();
            sta = conn.createStatement();
            //System.out.println(user_account);
            // System.out.println(user_pwd);
            String sql = "SELECT * FROM paper ";
            rs = sta.executeQuery(sql);
            while (rs.next()) {
                paper= new Paper();
                paper.setPaper_number(Integer.parseInt(rs.getString("paper_number")));
                paper.setTitle(rs.getString("title"));
                paper.setDecision(rs.getString("decision"));
                paper.setAbs(rs.getString("abstract"));
                paper.setPaper_type(rs.getString("paper_type"));
                paper.setIs_pc(rs.getString("is_pc"));
                paperList.add(paper);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        } finally {
            // ִ����ر����ݿ�
            dbmanage.closeDB(rs, sta, conn);
        }
        // ���ز�ѯ���
        return paperList;
	}
	
	public Paper searchPaper(int paper_number) {
        // ��ѯ����
        Dbmanage dbmanage = new Dbmanage();
        Connection conn = null;
        Statement sta = null;
        ResultSet rs = null;
        Paper paper= null;
        try {
            conn = dbmanage.initDB();
            sta = conn.createStatement();
            //System.out.println(user_account);
            // System.out.println(user_pwd);
            String sql = "SELECT * FROM paper WHERE paper_number = '"
                    + paper_number +  "'";
            rs = sta.executeQuery(sql);
            while (rs.next()) {
                paper= new Paper();
                paper.setPaper_number(Integer.parseInt(rs.getString("paper_number")));
                paper.setTitle(rs.getString("title"));
                paper.setDecision(rs.getString("decision"));
                paper.setAbs(rs.getString("abstract"));
                paper.setPaper_type(rs.getString("paper_type"));
                paper.setIs_pc(rs.getString("is_pc"));
            }

        } catch (SQLException e) {

            e.printStackTrace();
        } finally {
            // ִ����ر����ݿ�
            dbmanage.closeDB(rs, sta, conn);
        }
        // ���ز�ѯ���
        return paper;
	}
	
	public void acceptPaper(int paper_number) {
	    // ��������
	    Dbmanage dbmanage = new Dbmanage();
	    Connection conn = null;
	    Statement sta = null;

	    try {
	        conn = dbmanage.initDB();
	        sta = conn.createStatement();
	    	//System.out.println(..);
	        String sql = "UPDATE  paper  SET decision = 'accept' WHERE  paper_number = "
	                + paper_number;
	        sta.executeUpdate(sql);
	    } catch (SQLException e) {

	        e.printStackTrace();
	    } finally {
	        // ִ����ر����ݿ�
	        dbmanage.closeDB(sta, conn);
	    }
	}
	public void rejectPaper(int paper_number) {
	    // �ܾ�����
	    Dbmanage dbmanage = new Dbmanage();
	    Connection conn = null;
	    Statement sta = null;

	    try {
	        conn = dbmanage.initDB();
	        sta = conn.createStatement();
	    	//System.out.println(..);
	        String sql = "UPDATE  paper  SET decision = 'reject' WHERE  paper_number = "
	                + paper_number;
	        sta.executeUpdate(sql);
	    } catch (SQLException e) {

	        e.printStackTrace();
	    } finally {
	        // ִ����ر����ݿ�
	        dbmanage.closeDB(sta, conn);
	    }
	}
	public void delPaper(int paper_number) {
	    // �ܾ�����
	    Dbmanage dbmanage = new Dbmanage();
	    Connection conn = null;
	    Statement sta = null;

	    try {
	        conn = dbmanage.initDB();
	        sta = conn.createStatement();
	    	//System.out.println(..);
	        String sql = "DELETE from  paper  WHERE  paper_number = "
	                + paper_number;
	        sta.executeUpdate(sql);
	    } catch (SQLException e) {

	        e.printStackTrace();
	    } finally {
	        // ִ����ر����ݿ�
	        dbmanage.closeDB(sta, conn);
	    }
	}
}
