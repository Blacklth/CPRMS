package com.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.dbmanage.Dbmanage;
import com.vo.Discussion;
import com.vo.Paper;

public class DiscussionDao {
	public void insertDiscussion(Discussion discussion) {
	    // 添加讨论
	    Dbmanage dbmanage = new Dbmanage();
	    Connection conn = null;
	    Statement sta = null;

	    try {
	        conn = dbmanage.initDB();
	        sta = conn.createStatement();
	        int paper_number = discussion.getPaper_number();
	        String pc_code = discussion.getPc_code();
	        String comments = discussion.getComments();
	    	//System.out.println(pc_code);
	        String sql = "INSERT INTO discussion (paper_number,pc_code,comments) VALUES('"
	                + paper_number
	                + "','"
	                + pc_code
	                + "','"
	                + comments
	                + "')";
	        sta.executeUpdate(sql);
	    } catch (SQLException e) {

	        e.printStackTrace();
	    } finally {
	        // 执行完关闭数据库
	        dbmanage.closeDB(sta, conn);
	    }
	}
	public ArrayList<Discussion> searchDiscussion(String pc_code) {
        // 查询委员会成员参与的讨论
        Dbmanage dbmanage = new Dbmanage();
        Connection conn = null;
        Statement sta = null;
        ResultSet rs = null;
        ArrayList<Discussion> discussionList = new ArrayList<Discussion> ();
        Discussion discussion = null;
        try {
            conn = dbmanage.initDB();
            sta = conn.createStatement();
            //System.out.println(user_account);
            // System.out.println(user_pwd);
            String sql = "SELECT * FROM discussion WHERE pc_code = '"
            		+pc_code
            		+"' GROUP BY paper_number ";
            rs = sta.executeQuery(sql);
            while (rs.next()) {
            	discussion = new Discussion();
            	discussion.setPaper_number(Integer.parseInt(rs.getString("paper_number")));
            	discussion.setPc_code(rs.getString("pc_code"));
            	discussion.setSequence_no(Integer.parseInt(rs.getString("sequence_no")));
            	discussion.setComments(rs.getString("comments"));
            	discussionList.add(discussion);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        } finally {
            // 执行完关闭数据库
            dbmanage.closeDB(rs, sta, conn);
        }
        // 返回查询结果
        return discussionList;
	}
	public ArrayList<Discussion> searchDiscussion(int paper_number) {
        // 查询论文的所有论讨
        Dbmanage dbmanage = new Dbmanage();
        Connection conn = null;
        Statement sta = null;
        ResultSet rs = null;
        ArrayList<Discussion> discussionList = new ArrayList<Discussion> ();
        Discussion discussion = null;
        try {
            conn = dbmanage.initDB();
            sta = conn.createStatement();
            //System.out.println(user_account);
            // System.out.println(user_pwd);
            String sql = "SELECT * FROM discussion WHERE paper_number = '"
            		+paper_number
            		+"'order by sequence_no";
            rs = sta.executeQuery(sql);
            while (rs.next()) {
            	discussion = new Discussion();
            	discussion.setPaper_number(Integer.parseInt(rs.getString("paper_number")));
            	discussion.setPc_code(rs.getString("pc_code"));
            	discussion.setSequence_no(Integer.parseInt(rs.getString("sequence_no")));
            	discussion.setComments(rs.getString("comments"));
            	discussionList.add(discussion);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        } finally {
            // 执行完关闭数据库
            dbmanage.closeDB(rs, sta, conn);
        }
        // 返回查询结果
        return discussionList;
	}
	public ArrayList<Paper> searchNeedDiscussPaper(String pc_code) {
        // 查询需要讨论的论文
        Dbmanage dbmanage = new Dbmanage();
        Connection conn = null;
        Statement sta = null;
        ResultSet rs = null;
        ArrayList<Paper> discussionList = new ArrayList<Paper> ();
        Paper paper = null;
        try {
            conn = dbmanage.initDB();
            sta = conn.createStatement();
            //System.out.println(user_account);
            // System.out.println(user_pwd);
            String sql = "SELECT R.paper_number,pc_code,spread  FROM referee_report,( SELECT paper_number,MAX(avg)-MIN(avg) as spread "
            		+ " FROM(SELECT paper_number,(originality+impact+presentation+technical_depth+overall_rating)/5 as avg "
            		+ " FROM referee_report WHERE paper_number in (	SELECT paper_number	FROM referee_report	GROUP BY paper_number "
            		+ " HAVING COUNT(pc_code) >= 3) ) as T 	GROUP BY paper_number) as R WHERE "
            		+ " referee_report.paper_number = R.paper_number and spread >= 1.0 and pc_code='"  
            		+pc_code
            		+"' and (R.paper_number,pc_code) not in (select paper_number,pc_code from discussion)";
            System.out.println(sql);
            
            rs = sta.executeQuery(sql);
            while (rs.next()) {
            	paper = new Paper();
            	paper.setPaper_number(Integer.parseInt(rs.getString("paper_number")));
            	paper.setSpread(Double.parseDouble(rs.getString("spread")));
            	discussionList.add(paper);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        } finally {
            // 执行完关闭数据库
            dbmanage.closeDB(rs, sta, conn);
        }
        // 返回查询结果
        return discussionList;
	}
}
