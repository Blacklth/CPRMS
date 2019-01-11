package com.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.vo.Paper;

import com.service.PaperService;
/**
 * Servlet implementation class SubmitAbs
 */

public class SubmitAbs extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubmitAbs() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
	    response.setCharacterEncoding("utf-8");
	    String title= request.getParameter("paperTitle");
	    String abs= request.getParameter("paperAbs");
	    String type = request.getParameter("paperType");
	    String isPc = request.getParameter("pc");
	    String authorName1 = request.getParameter("authorName1");
	    String authorEmail1 = request.getParameter("authorEmail1");
	    String authorName2 = request.getParameter("authorName2");
	    String authorEmail2 = request.getParameter("authorEmail2");
	    String authorName3 = request.getParameter("authorName3");
	    String authorEmail3 = request.getParameter("authorEmail3");
	    String conAuthorName = request.getParameter("conAuthorName");
	    String conAuthorEmail = request.getParameter("conAuthorEmail");
	    
	    //System.out.println(authorName1);
	    Paper paper = new Paper();
	    paper.setTitle(title);
	    paper.setAbs(abs);
	    paper.setPaper_type(type);
	    paper.setIs_pc(isPc);
	    
	    paper = PaperService.newPaper(paper);
	    
	    String message = null;
	    if(authorName1 != "" && authorEmail1 != "")
	    {	
	    	boolean flag = PaperService.setAuthor(paper.getPaper_number(), authorEmail1, authorName1);
	    	if(flag != true) {
	    		message = "作者1 查无此人！";
	    	}
	    	 
	    }
	    if(authorName2 != "" && authorEmail2 != "")
	    {
	    	boolean flag = PaperService.setAuthor(paper.getPaper_number(), authorEmail2, authorName2);
	    	if(flag != true) {
	    		message = "作者2 查无此人！";
	    	}
	    }
	    if(authorName3 != "" && authorEmail3 != "")
	    {
	    	boolean flag = PaperService.setAuthor(paper.getPaper_number(), authorEmail3, authorName3);
	    	if(flag != true) {
	    		message = "作者3 查无此人！";
	    	}
	    }
	    if(conAuthorName  != "" && conAuthorEmail != "")
	    {	
	    	boolean flag = PaperService.setConAuthor(paper.getPaper_number(), conAuthorEmail, conAuthorName);
	    	if(flag != true) {
	    		message = "通讯作者 查无此人！";
	    	}
	    }
	    
	    String smessage = "已成功提交摘要信息！";
	    if(message != null )
	    {
	    	request.setAttribute("message", message);
	    	PaperService.delPaper(paper.getPaper_number());
	    }
	    else
	    {
	    	request.setAttribute("smessage", smessage);
	    }
	    request.getRequestDispatcher("submitAbs.jsp").forward(request,response);
	    
	}
}
