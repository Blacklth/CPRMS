package com.servlet;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.service.ChairService;


/**
 * Servlet implementation class Accept
 */

public class Accept extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Accept() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String spn = request.getParameter("paper_number");
		String m = null;
		String sm = null;
		boolean flag = ChairService.accept(spn);
		if(flag)
		{
			sm = "通知函发送成功！";
			request.setAttribute("smessage", sm);
		    request.getRequestDispatcher("sendLetter.jsp").forward(request,response);
		}
		else
		{
		   m = "通知函发送失败！";
		   request.setAttribute("message", m);
		   request.getRequestDispatcher("sendLetter.jsp").forward(request,response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
