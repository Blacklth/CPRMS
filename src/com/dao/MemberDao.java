package com.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

import com.dbmanage.Dbmanage;
import com.vo.Member;
import com.vo.Person;

public class MemberDao {
	public ArrayList<Member> searchAllMember() {
        // ��ѯ����ίԱ���Ա
        Dbmanage dbmanage = new Dbmanage();
        Connection conn = null;
        Statement sta = null;
        ResultSet rs = null;
        ResultSet rs1 = null;
        ArrayList<Member> memberList = new ArrayList<Member> ();
        Member member = null;
        try {
            conn = dbmanage.initDB();
            sta = conn.createStatement();
            //System.out.println(user_account);
            // System.out.println(user_pwd);
            String sql = "SELECT * FROM pc_member ";
            rs = sta.executeQuery(sql);
            while (rs.next()) {
                member = new Member();
                member.setPerson_id(Integer.parseInt(rs.getString("person_id")));
                member.setPc_code(rs.getString("pc_code"));
                memberList.add(member);
            }
            sql = "SELECT * FROM pc_chair ";
            rs1 = sta.executeQuery(sql);
            while(rs1.next())
            {
            	String pc_code = rs1.getString("pc_code");
            	Iterator <Member> it1 = memberList.iterator();
    	        while(it1.hasNext()){
    	        	 member = it1.next();
    	            if(member.getPc_code().equals(pc_code))
    	            {
    	            	member.setIs_chair(true);
    	            }
    	        }
            }
            rs1.close();
        } catch (SQLException e) {

            e.printStackTrace();
        } finally {
            // ִ����ر����ݿ�
            dbmanage.closeDB(rs, sta, conn);
        }
        // ���ز�ѯ���
        return memberList;
	}
	public void insertMember(Person person,String pc_code) {
	    // ��ӳ�Ա
	    Dbmanage dbmanage = new Dbmanage();
	    Connection conn = null;
	    Statement sta = null;

	    try {
	        conn = dbmanage.initDB();
	        sta = conn.createStatement();
	        int person_id = person.getPerson_id();
	        String sql = "INSERT INTO pc_member (person_id,pc_code) VALUES('"
	                + person_id
	                + "','"
	                + pc_code
	                + "')";
	        sta.executeUpdate(sql);
	    } catch (SQLException e) {

	        e.printStackTrace();
	    } finally {
	        // ִ����ر����ݿ�
	        dbmanage.closeDB(sta, conn);
	    }
	}
	public void deleteMember(int person_id) {
	    //ɾ����Ա
	    Dbmanage dbmanage = new Dbmanage();
	    Connection conn = null;
	    Statement sta = null;

	    try {
	        conn = dbmanage.initDB();
	        sta = conn.createStatement();
	    	//System.out.println(user_pwd);
	        String sql = "delete from pc_member where person_id ='"
	                + person_id
	                + "'";
	        sta.executeUpdate(sql);
	    } catch (SQLException e) {

	        e.printStackTrace();
	    } finally {
	        // ִ����ر����ݿ�
	        dbmanage.closeDB(sta, conn);
	    }
	}
	public Member searchMember(int person_id) {
        // ��ѯ��Ա
        Dbmanage dbmanage = new Dbmanage();
        Connection conn = null;
        Statement sta = null;
        ResultSet rs = null;
        ResultSet rs1 = null;
        Member member = null;
        try {
            conn = dbmanage.initDB();
            sta = conn.createStatement();
            //System.out.println(user_account);
            // System.out.println(user_pwd);
            String sql = "SELECT * FROM pc_member WHERE person_id= '"
                    + person_id +  "'";
            rs = sta.executeQuery(sql);
            while (rs.next()) {
                member = new Member();
                member.setPerson_id(Integer.parseInt(rs.getString("person_id")));
                member.setPc_code(rs.getString("pc_code"));
            }
            if(member != null)
            {
                sql = "SELECT * FROM pc_chair ";
                rs1 = sta.executeQuery(sql);
                while(rs1.next())
                {
                	String chair= rs1.getString("pc_code");
        	         if(member.getPc_code().equals(chair))
        	          {
        	            member.setIs_chair(true);
        	           }
        	       
                }
                rs1.close();
            }

        } catch (SQLException e) {

            e.printStackTrace();
        } finally {
            // ִ����ر����ݿ�
            dbmanage.closeDB(rs, sta, conn);
        }
        // ���ز�ѯ���
        return member;
	}
	public Member searchMember(String pc_code) {
        // ��ѯ��Ա
        Dbmanage dbmanage = new Dbmanage();
        Connection conn = null;
        Statement sta = null;
        ResultSet rs = null;
        ResultSet rs1 = null;
        Member member = null;
        try {
            conn = dbmanage.initDB();
            sta = conn.createStatement();
            //System.out.println(user_account);
            // System.out.println(user_pwd);
            String sql = "SELECT * FROM pc_member WHERE pc_code = '"
                    + pc_code +  "'";
            rs = sta.executeQuery(sql);
            while (rs.next()) {
                member = new Member();
                member.setPerson_id(Integer.parseInt(rs.getString("person_id")));
                member.setPc_code(rs.getString("pc_code"));
            }

        } catch (SQLException e) {

            e.printStackTrace();
        } finally {
            // ִ����ر����ݿ�
            dbmanage.closeDB(rs, sta, conn);
        }
        // ���ز�ѯ���
        return member;
	}
}
