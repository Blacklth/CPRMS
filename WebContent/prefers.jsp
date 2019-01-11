<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ page import="com.dao.PersonDao,com.vo.Person,java.util.ArrayList,java.util.Iterator,com.dao.MemberDao,com.vo.Member,com.dao.PaperDao,com.vo.Paper,com.dao.PrefersDao,com.vo.Prefers" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>设置偏好</title>
<link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css">  
<script src="https://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<style>

</style>
</head>
<% 
	Cookie[] cookies = request.getCookies();
	String name = null;	
	String email = null;
	Person person = null;
	Member member = null;
	String title = null;
	ArrayList <Prefers> prefersList = null;
	ArrayList <Prefers> unPrefersList = null;
	MemberDao memberdao = null;
	PrefersDao prefersdao = null;
	PaperDao paperdao = null;
	boolean flag = true;
	if(cookies != null)
	{
		for(int i=0;i<cookies.length;i++)
		{
			if(cookies[i].getName().equals("person"))
			{	
				name = cookies[i].getValue().split("#")[0];
				email = cookies[i].getValue().split("#")[1];
				flag = false;
				break;
			}
		}
	}
	if(flag)
	{
		response.sendRedirect("error.jsp?error=noLogin");
	}
	else
	{
		memberdao = new MemberDao();
		prefersdao = new PrefersDao();
		paperdao = new PaperDao();
		PersonDao personDao = new PersonDao();
		person = personDao.searchUser(email);
		if(person!=null)
		{
			title = person.getTitle();
			member = memberdao.searchMember(person.getPerson_id());
			if(member != null)
			{
				prefersList = prefersdao.searchPrefers(member.getPc_code());
				unPrefersList = prefersdao.searchUnPrefers(member.getPc_code());
			}
			else
			{
				response.sendRedirect("error.jsp?error=noMember");
			}	
		}
	}
%>

<body style = "padding: 60px;">
	<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand" href="#">CPRMS</a>
			</div>
			<div>
				<ul class="nav navbar-nav navbar-left">
					<li><a href="index.jsp">Call For Papers</a></li>
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">
							论文提交
							<b class="caret"></b>
						</a>
						<ul class="dropdown-menu">
							<li><a href="submitAbs.jsp">提交摘要</a></li>
							<li><a href="submitPDF.jsp">提交论文</a></li>
							<li><a href="paperInform.jsp">查看论文信息</a></li>
						</ul>
					</li>
					<li class="dropdown active">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">
							论文评审
							<b class="caret"></b>
						</a>
						<ul class="dropdown-menu">
							<li class="active"><a href="prefers.jsp">设置偏好</a></li>
							<li><a href="reviewPaper.jsp">审阅论文</a></li>
							<li><a href="discuss.jsp">讨论</a></li>
						</ul>
					</li>
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">
							委员会管理
							<b class="caret"></b>
						</a>
						<ul class="dropdown-menu">
							<li><a href="managePC.jsp">管理委员会成员</a></li>
							<li><a href="assignPaper.jsp">分配论文</a></li>
							<li><a href="sendLetter.jsp">发送通知函</a></li>
						</ul>
					</li>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<%if(person != null )  {%>
						<li><a href="#">Hello,<%=title %> <%=name %></a></li>
						<li><a href="person.jsp?person_id=<%=person.getPerson_id()%>"><span class="glyphicon glyphicon-user"></span> 查看个人信息</a></li>
						<li><a  href="Logout"><span class="glyphicon glyphicon-log-out"></span> 登出</a></li>
                    <% }else { %> 
      					<li><a data-toggle="modal" data-target="#register" href=""><span class="glyphicon glyphicon-user"></span> 注册</a></li>
      					<li><a data-toggle="modal" data-target="#login" href=""><span class="glyphicon glyphicon-log-in"></span> 登录</a></li>
               		<%} %>
   				</ul>
			</div>
		</div>
	</nav>
	<div class="container">               
		<table class="table table-hover table-responsive " >
			<caption>已设置偏好论文</caption>
			<thead>
				<tr>
					<th>论文编号</th>
					<th>标题</th>
					<th>类型</th>
					<th>偏好等级</th>
					<th></th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<%
					if(prefersList != null)
					{	
						Iterator <Prefers> it1 = prefersList.iterator();
						while(it1.hasNext())
						{	
							Prefers prefers = it1.next();
							Paper paper = paperdao.searchPaper(prefers.getPaper_number());
			    %>
				<tr>
					<td><%=paper.getPaper_number() %></td>
					<td><%=paper.getTitle() %></td>
					<td><%=paper.getPaper_type()%></td>
					<td><%=prefers.getPreference() %></td>
					<td>
						<a href="paper.jsp?paper_number=<%=paper.getPaper_number() %>">
						<button  type="button" class="btn  btn-success">
            				<span class="glyphicon glyphicon-plus" ></span>查看论文信息
       				 	</button>
       				 	</a>
					</td>
					<td>
						<a href="setPrefers.jsp?paper_number=<%=paper.getPaper_number() %>&preference=<%=prefers.getPreference() %>">
						<button  type="button" class="btn  btn-success">
            				<span class="glyphicon glyphicon-plus" ></span>修改偏好
       				 	</button>
       				 	</a>
					</td>
				</tr>   
			    <% } }%>
			</tbody>
		</table>
	</div>
		<div class="container">               
		<table class="table table-hover" >
			<caption>未设置偏好论文</caption>
			<thead>
				<tr>
					<th>论文编号</th>
					<th>标题</th>
					<th>类型</th>
					<th></th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<%
					if(unPrefersList != null)
					{	
						Iterator <Prefers> it1 = unPrefersList.iterator();
						while(it1.hasNext())
						{	
							Prefers prefers = it1.next();
							Paper paper = paperdao.searchPaper(prefers.getPaper_number());
			    %>   
				<tr>
					<td><%=paper.getPaper_number() %></td>
					<td><%=paper.getTitle() %></td>
					<td><%=paper.getPaper_type()%></td>
					<td>
						<a href="paper.jsp?paper_number=<%=paper.getPaper_number() %>">
						<button  type="button" class="btn  btn-success">
            				<span class="glyphicon glyphicon-plus" ></span>查看论文信息
       				 	</button>
       				 	</a>
					</td>
					<td>
						<a href="setPrefers.jsp?paper_number=<%=paper.getPaper_number() %>">
						<button  type="button" class="btn  btn-success">
            				<span class="glyphicon glyphicon-plus" ></span>设置偏好
       				 	</button>
       				 	</a>
					</td>
				</tr>   
			    <% } }%>
			</tbody>
		</table>
	</div>
</body>
</html>