package com.service;
import com.dao.DiscussionDao;
import com.dao.PrefersDao;
import com.dao.ReportDao;
import com.vo.Discussion;
import com.vo.Prefers;
import com.vo.Report;

public class MemberService {
	
	static public void setPrefers(Prefers prefers,String pre)
	{
	    PrefersDao prefersdao = new PrefersDao();	    
	    if(pre!=null)
	    {
	    	prefersdao.setPrefers(prefers);
	    }
	    else
	    {
	    	prefersdao.insertPrefers(prefers);
	    }
	}
	static public void setReport(Report report)
	{
		ReportDao reportdao = new ReportDao();
    	reportdao.insertReport(report);
	}
	static public void setDiscuss(Discussion discuss)
	{
		DiscussionDao discussdao = new DiscussionDao();
		discussdao.insertDiscussion(discuss);
	}
}
