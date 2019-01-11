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
        // 设置编码格式为 UTF-8
        String name = request.getParameter("name");
        String title = request.getParameter("title");
        String institution = request.getParameter("institution");
        String email = request.getParameter("email");
        String telephone = request.getParameter("telephone");
        // 前台得到 用户输入数据
        Person person = new Person();
        // 实例化一个VO对象
        person.setName(name);
        person.setEmail(email);
        person.setInstitution(institution);
        person.setTitle(title);
        person.setTelephone(telephone);
        // 将前台得到的数据存入ＶＯ
        UserService.addPerson(person);
        // 调用增加用户方法
        request.setAttribute("email", email);
       request.getRequestDispatcher("index.jsp").forward(request,response);
        // 转到登录页面
	}

}
