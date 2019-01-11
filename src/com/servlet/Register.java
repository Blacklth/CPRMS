package com.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.service.UserService;
import com.vo.Person;



/**
 * Servlet implementation class Register
 */

public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
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
        // ���ñ����ʽΪ UTF-8
        String name = request.getParameter("name");
        String title = request.getParameter("title");
        String institution = request.getParameter("institution");
        String email = request.getParameter("email");
        String telephone = request.getParameter("telephone");
        // ǰ̨�õ� �û���������
        Person person = new Person();
        // ʵ����һ��VO����
        person.setName(name);
        person.setEmail(email);
        person.setInstitution(institution);
        person.setTitle(title);
        person.setTelephone(telephone);
        // ��ǰ̨�õ������ݴ���֣�
        UserService.addPerson(person);
        // ���������û�����
        request.setAttribute("email", email);
       request.getRequestDispatcher("index.jsp").forward(request,response);
        // ת����¼ҳ��
	}

}
