package com.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.service.UserService;
import com.vo.Person;

/**
 * Servlet implementation class Login
 */

public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
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
        String name= request.getParameter("loginname");
        String email= request.getParameter("loginemail");
        // 前台得到用户输入
        Person person = UserService.getPerson(email, name);
        // 调用方法判断用户是否存在
        String message = "查无此人！请输入正确的信息！";
        if (person == null) {
            // 如果用户不存在，重新登录
        	System.out.println("no");
            request.setAttribute("message", message);
            request.getRequestDispatcher("index.jsp").forward(request,response);
        } else {
            // 如果用户存在，检索数据，跳到用户列表显示页面
            //ArrayList<UserVo> list = userDao.selectNotDeleteList();
        	System.out.println("yes");
        	Cookie cookie = new Cookie("person",name+"#"+email);
        	cookie.setMaxAge(60*60*24*30);
        	response.addCookie(cookie);
        	response.sendRedirect("index.jsp");
        }
	}

}