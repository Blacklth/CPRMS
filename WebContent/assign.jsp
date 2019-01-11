<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ page import="com.dao.PersonDao,com.vo.Person,java.util.ArrayList,java.util.Iterator,com.dao.MemberDao,com.vo.Member,com.dao.PaperDao,com.vo.Paper,com.dao.AssignDao,com.vo.Assign" %>
  <%@ page import="com.dao.PrefersDao,com.vo.Prefers" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>分配论文</title>
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
	if(cookies != null)
	{
		for(int i=0;i<cookies.length;i++)
		{
			if(cookies[i].getName().equals("person"))
			{	
				name = cookies[i].getValue().split("#")[0];
				email = cookies[i].getValue().split("#")[1];
			}
		}
	}
	String message = (String)(request.getAttribute("message")); 
	String spn = request.getParameter("paper_number");
	System.out.println(message);
	System.out.println(name);
	System.out.println(email);
	Person person = null;
	String title = null;
	ArrayList <Prefers> prefersList = null;
	ArrayList <Assign> assignList = null;
	PaperDao paperdao = new PaperDao();
	MemberDao memberdao = new MemberDao();
	PersonDao personDao = new PersonDao();
	PrefersDao prefersdao = new PrefersDao();
	if(email!=null)
	{
		person = personDao.searchUser(email);
		if(person!=null)
		{
			title = person.getTitle();
			if(spn != null)
			{
				int paper_number = Integer.parseInt(spn);
				AssignDao assigndao = new AssignDao();
				assignList = assigndao.searchAssignedPaper(paper_number);
				prefersList = prefersdao.searchPrefers(paper_number);
			}
			//assignedPaperList = new ArrayList <Paper> ();
			//Iterator <Assign> it1 = assignList.iterator();
			//while(it1.hasNext())
			//{
			//	int paper_number = it1.next().getPaper_number();
			//	Paper paper = paperdao.searchPaper(paper_number);
			//	assignedPaperList.add(paper);
			//}
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
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">
							论文评审
							<b class="caret"></b>
						</a>
						<ul class="dropdown-menu">
							<li><a href="prefers.jsp">设置偏好</a></li>
							<li><a href="reviewPaper.jsp">审阅论文</a></li>
							<li><a href="discuss.jsp">讨论</a></li>
						</ul>
					</li>
					<li class="dropdown active">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">
							委员会管理
							<b class="caret"></b>
						</a>
						<ul class="dropdown-menu">
							<li><a href="managePC.jsp">管理委员会成员</a></li>
							<li class="active"><a href="assignPaper.jsp">分配论文</a></li>
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
		<table class="table table-hover" id="assignedPaper" >
			<caption>已分配</caption>
			<thead>
				<tr>
					<th>论文编号</th>
					<th>标题</th>
					<th>类型</th>
					<th>审稿人委员会编号</th>
					<th>审稿人</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<%
					if(assignList != null)
					{	
						Iterator <Assign> it1 = assignList.iterator();
						while(it1.hasNext())
						{	
							Assign assign = it1.next();
							Paper paper = paperdao.searchPaper(assign.getPaper_number());
							Member member = memberdao.searchMember(assign.getPc_code());
							Person p = personDao.searchUser(member.getPerson_id());
							
			    %>
				<tr>
					<td><%=paper.getPaper_number() %></td>
					<td><%=paper.getTitle() %></td>
					<td><%=paper.getPaper_type()%></td>
					<td><%=member.getPc_code()%></td>
					<td><%=p.getName()%></td>
					<td>
						<a href="DelAssign?paper_number=<%=paper.getPaper_number() %>&pc_code=<%=member.getPc_code() %>">
					  	<button id="btnDel" type="submit" class="btn  btn-danger" >
            				<span class="glyphicon glyphicon-minus" ></span>删除
       				 	</button>
       				 	</a>
					</td>
				</tr>   
			    <% } }%>
			</tbody>
		</table>
	</div>
	<div class="container">               
		<table class="table table-hover" id="assignedPaper" >
			<caption>委员会成员对该论文的偏好等级</caption>
			<thead>
				<tr>
					<th>论文编号</th>
					<th>标题</th>
					<th>类型</th>
					<th>审稿人委员会编号</th>
					<th>审稿人</th>
					<th>偏好等级</th>
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
							Member member = memberdao.searchMember(prefers.getPc_code());
							Person p = personDao.searchUser(member.getPerson_id());
							
			    %>
				<tr>
					<td><%=paper.getPaper_number() %></td>
					<td><%=paper.getTitle() %></td>
					<td><%=paper.getPaper_type()%></td>
					<td><%=member.getPc_code()%></td>
					<td><%=p.getName()%></td>
					<td><%=prefers.getPreference() %></td>
					<td>
						<a href="AddAssign?paper_number=<%=paper.getPaper_number() %>&pc_code=<%=member.getPc_code() %>">
						<button id="build" type="button" class="btn  btn-success">
            				<span class="glyphicon glyphicon-plus" ></span>添加分配
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