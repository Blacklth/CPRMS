package com.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.dbmanage.Dbmanage;
import com.vo.Report;

public class ReportDao {
	public void insertReport(Report report) {
	    // 添加报告
	    Dbmanage dbmanage = new Dbmanage();
	    Connection conn = null;
	    Statement sta = null;

	    try {
	        conn = dbmanage.initDB();
	        sta = conn.createStatement();
	        int paper_number = report.getPaper_number();
	        String pc_code = report.getPc_code();
	    	String relevant = report.getRelevant();
	    	String technically = report.getTechnically();
	    	String length = report.getLength();
	    	double  originality = report.getOriginality();
	    	double  impact = report.getImpact();
	    	double  presentation = report.getPresentation();
	    	double  depth = report.getDepth();
	    	double  overall_rating = report.getOverall_rating();
	    	double  confidence = report.getConfidence();
	    	String best_paper = report.getBest_paper();
	    	String main_contribution = report.getMain_contribution();
	    	String strong_points = report.getStrong_points();
	    	String weak_points = report.getWeak_points();
	    	String overall_summary = report.getOverall_summary();
	    	String detailed_comments = report.getDetailed_comments();
	    	String confidential_comments = report.getConfidential_comments();
	    	//System.out.println(...);
	        String sql = "INSERT INTO referee_report (pc_code,paper_number,relevant,technically_correct,length_and_content,"
	        		+ "originality,impact,presentation,technical_depth,overall_rating,confidence,best_paper,main_contribution,"
	        		+ "strong_points,weak_points,overall_summary,detailed_comments,confidential_comments) VALUES('"
	                + pc_code+ "','"+ paper_number + "','" + relevant + "','"+  technically + "','"  + length 
	                + "','" + originality + "','" + impact  + "','"  + presentation  + "','" + depth  + "','" + overall_rating
	                + "','"  + confidence + "','"  + best_paper  + "','"  + main_contribution   + "','"  + strong_points
	                + "','" + weak_points  + "','" + overall_summary + "','"+ detailed_comments + "','" + confidential_comments
	                + "')";
	        sta.executeUpdate(sql);
	    } catch (SQLException e) {

	        e.printStackTrace();
	    } finally {
	        // 执行完关闭数据库
	        dbmanage.closeDB(sta, conn);
	    }
	}
	public ArrayList <Report> searchReport(String pc_code) {
        // 查询报告
        Dbmanage dbmanage = new Dbmanage();
        Connection conn = null;
        Statement sta = null;
        ResultSet rs = null;
        ArrayList <Report>  reportList = new ArrayList <Report> ();
        Report report= null;
        try {
            conn = dbmanage.initDB();
            sta = conn.createStatement();
            //System.out.println(user_account);
            // System.out.println(user_pwd);
            String sql = "SELECT paper_number FROM referee_report WHERE pc_code = '"
                    + pc_code+  "'";
            rs = sta.executeQuery(sql);
            while (rs.next()) {
            	report= new Report();
            	report.setPaper_number(Integer.parseInt(rs.getString("paper_number")));
            	report.setPc_code(rs.getString("paper_number"));
            	reportList.add(report);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        } finally {
            // 执行完关闭数据库
            dbmanage.closeDB(rs, sta, conn);
        }
        // 返回查询结果
        return reportList;
	}
	public ArrayList <Report> searchUnReport(String pc_code) {
        // 查询未添加报告
        Dbmanage dbmanage = new Dbmanage();
        Connection conn = null;
        Statement sta = null;
        ResultSet rs = null;
        ArrayList <Report>  reportList = new ArrayList <Report> ();
        Report report= null;
        try {
            conn = dbmanage.initDB();
            sta = conn.createStatement();
            //System.out.println(user_account);
            // System.out.println(user_pwd);
            String sql = "SELECT paper_number,pc_code FROM assigned_to WHERE pc_code = '"
                    + pc_code+  "' and paper_number NOT IN ( SELECT paper_number FROM referee_report WHERE pc_code = '"
                    +pc_code+ "') ";
            rs = sta.executeQuery(sql);
            while (rs.next()) {
            	report= new Report();
            	report.setPaper_number(Integer.parseInt(rs.getString("paper_number")));
            	report.setPc_code(rs.getString("paper_number"));
            	reportList.add(report);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        } finally {
            // 执行完关闭数据库
            dbmanage.closeDB(rs, sta, conn);
        }
        // 返回查询结果
        return reportList;
	}
	public Report searchReport(String pc_code,int paper_number) {
        // 查询报告
        Dbmanage dbmanage = new Dbmanage();
        Connection conn = null;
        Statement sta = null;
        ResultSet rs = null;
        Report report= null;
        try {
            conn = dbmanage.initDB();
            sta = conn.createStatement();
            //System.out.println(user_account);
            // System.out.println(user_pwd);
            String sql = "SELECT * FROM referee_report WHERE pc_code = '"
                    + pc_code+  "' AND paper_number = " + paper_number;
            rs = sta.executeQuery(sql);
            while (rs.next()) {
            	report= new Report();
            	report.setPaper_number(Integer.parseInt(rs.getString("paper_number")));
            	report.setPc_code(rs.getString("pc_code"));
            	report.setRelevant(rs.getString("relevant"));
            	report.setTechnically(rs.getString("technically_correct"));
            	report.setLength(rs.getString("length_and_content"));
            	report.setOriginality(Double.parseDouble(rs.getString("originality")));
            	report.setImpact(Double.parseDouble(rs.getString("impact")));
            	report.setPresentation(Double.parseDouble(rs.getString("presentation")));
            	report.setDepth(Double.parseDouble(rs.getString("technical_depth")));
            	report.setOverall_rating(Double.parseDouble(rs.getString("overall_rating")));
            	report.setConfidence(Double.parseDouble(rs.getString("confidence")));
            	report.setBest_paper(rs.getString("best_paper"));
            	report.setMain_contribution(rs.getString("main_contribution"));
            	report.setStrong_points(rs.getString("strong_points"));
            	report.setWeak_points(rs.getString("weak_points"));
            	report.setOverall_summary(rs.getString("overall_summary"));
            	report.setDetailed_comments(rs.getString("detailed_comments"));
            	report.setConfidential_comments(rs.getString("confidential_comments"));
            }

        } catch (SQLException e) {

            e.printStackTrace();
        } finally {
            // 执行完关闭数据库
            dbmanage.closeDB(rs, sta, conn);
        }
        // 返回查询结果
        return report;
	}
	public ArrayList <Report> searchReport(int paper_number) {
        // 查询报告
        Dbmanage dbmanage = new Dbmanage();
        Connection conn = null;
        Statement sta = null;
        ResultSet rs = null;
        ArrayList <Report> reportList = new ArrayList <Report>();
        Report report= null;
        try {
            conn = dbmanage.initDB();
            sta = conn.createStatement();
            //System.out.println(user_account);
            // System.out.println(user_pwd);
            String sql = "SELECT * FROM referee_report WHERE paper_number = "
                      + paper_number;
            rs = sta.executeQuery(sql);
            while (rs.next()) {
            	report= new Report();
            	report.setPaper_number(Integer.parseInt(rs.getString("paper_number")));
            	report.setPc_code(rs.getString("pc_code"));
            	report.setRelevant(rs.getString("relevant"));
            	report.setTechnically(rs.getString("technically_correct"));
            	report.setLength(rs.getString("length_and_content"));
            	report.setOriginality(Double.parseDouble(rs.getString("originality")));
            	report.setImpact(Double.parseDouble(rs.getString("impact")));
            	report.setPresentation(Double.parseDouble(rs.getString("presentation")));
            	report.setDepth(Double.parseDouble(rs.getString("technical_depth")));
            	report.setOverall_rating(Double.parseDouble(rs.getString("overall_rating")));
            	report.setConfidence(Double.parseDouble(rs.getString("confidence")));
            	report.setBest_paper(rs.getString("best_paper"));
            	report.setMain_contribution(rs.getString("main_contribution"));
            	report.setStrong_points(rs.getString("strong_points"));
            	report.setWeak_points(rs.getString("weak_points"));
            	report.setOverall_summary(rs.getString("overall_summary"));
            	report.setDetailed_comments(rs.getString("detailed_comments"));
            	report.setConfidential_comments(rs.getString("confidential_comments"));
            	reportList.add(report);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        } finally {
            // 执行完关闭数据库
            dbmanage.closeDB(rs, sta, conn);
        }
        // 返回查询结果
        return reportList;
	}
}
