package com.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.service.ChairService;
import com.service.UserService;
import com.vo.Person;

/**
 * Servlet implementation class AddMember
 */

public class AddMember extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddMember() {
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
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String pc_code = request.getParameter("pc_code");
		
        Person person = UserService.getPerson(email, name);
        // ���÷����ж��û��Ƿ����
        String message = "���޴��ˣ���������ȷ����Ϣ��";
        if (person == null) {
            // ����û������ڣ����µ�¼
            request.setAttribute("message", message);
            request.getRequestDispatcher("managePC.jsp").forward(request,response);
        } else {
            ChairService.addMember(person, pc_code);
        	response.sendRedirect("managePC.jsp");
        }
	}

}
