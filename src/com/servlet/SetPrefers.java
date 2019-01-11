package com.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.service.MemberService;
import com.vo.Prefers;

/**
 * Servlet implementation class SetPrefers
 */

public class SetPrefers extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SetPrefers() {
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
	    int paper_number = Integer.parseInt(request.getParameter("paper_number"));
	    String pc_code = request.getParameter("pc_code");
	    int preference = Integer.parseInt(request.getParameter("preference"));
	    String pre = request.getParameter("pre");
	    		
	    Prefers prefers = new Prefers();
	    prefers.setPaper_number(paper_number);
	    prefers.setPc_code(pc_code);
	    prefers.setPreference(preference);
	    
	    MemberService.setPrefers(prefers, pre);

	    response.sendRedirect("prefers.jsp");
	
	}

}
