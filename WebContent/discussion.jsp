<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ page import="com.dao.PersonDao,com.vo.Person,java.util.ArrayList,java.util.Iterator,com.dao.MemberDao,com.vo.Member,com.dao.PaperDao,com.vo.Paper,com.dao.ReportDao,com.vo.Report" %>
 <%@ page import="com.dao.DiscussionDao,com.vo.Discussion" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>讨论</title>
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
	Member member = null;
	Paper paper = null;
	String title = null;
	ArrayList <Discussion> discussList = null;
	ArrayList <Report> reportList = null;
	MemberDao memberdao = new MemberDao();
	ReportDao reportdao = new ReportDao();
	PaperDao paperdao = new PaperDao();
	DiscussionDao discussdao = new DiscussionDao();
	if(email!=null)
	{
		PersonDao personDao = new PersonDao();
		person = personDao.searchUser(email);
		if(person!=null)
		{
			title = person.getTitle();
			member = memberdao.searchMember(person.getPerson_id());
			if(spn != null)
			{
				int paper_number = Integer.parseInt(spn);
				paper = paperdao.searchPaper(paper_number);
				reportList = reportdao.searchReport(paper_number);
				discussList = discussdao.searchDiscussion(paper_number);
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
							<li><a href="prefers.jsp">设置偏好</a></li>
							<li><a href="reviewPaper.jsp">审阅论文</a></li>
							<li class="active"><a href="discuss.jsp">讨论</a></li>
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
	<div class = "content" align = "center" style = "margin-left: 250px;margin-right: 250px;">
		<h1>Paper Discussion</h1>
		<hr>
		<h4>Paper Number : <%=paper.getPaper_number() %></h4>
        <h4>Title : <%=paper.getTitle() %></h4>
		<hr>
		<%
			if(reportList != null)
			{	
				Iterator <Report> it1 = reportList.iterator();
				while(it1.hasNext())
				{	
					Report report = it1.next();
					
		 %>
		<p class = "text-left">来自委员会成员<%=report.getPc_code() %>的综述:  <b><%=report.getOverall_summary()  %> </b></p>
		<%} } %>
		<hr>        
		<table class="table table-hover" id="refersPaper" >
			<caption>审查总结</caption>
			<thead>
				<tr>
					<th>审稿人</th>
					<th>相关性</th>
					<th>正确性</th>
					<th>长度</th>
					<th>是否最佳</th>
					<th>独创性</th>
					<th>影响力</th>
					<th>表达</th>
					<th>深度</th>
					<th>总体</th>
					<th>确实性</th>
				</tr>
			</thead>
			<tbody>
				<%
					if(reportList != null)
					{	
						Iterator <Report> it1 = reportList.iterator();
						while(it1.hasNext())
						{	
							Report report = it1.next();
			    %>
				<tr>
					<td><%=report.getPc_code() %></td>
					<td><%=report.getRelevant() %></td>
					<td><%=report.getTechnically()%> </td>
					<td><%=report.getLength()%></td>
					<td><%=report.getBest_paper() %></td>
					<td><%=report.getOriginality() %> </td>
					<td><%=report.getImpact() %> </td>
					<td><%=report.getPresentation() %>  </td>
					<td><%=report.getDepth() %>  </td>
					<td><%=report.getOverall_rating() %> </td>
					<td><%=report.getConfidence() %> </td>
				</tr>   
			    <% } }%>
			</tbody>
		</table>
		<hr>
		<p class = "text-left"> 讨论总结</p>
		<%
			if(discussList != null)
			{	
				Iterator <Discussion> it1 = discussList.iterator();
				while(it1.hasNext())
				{	
					Discussion discuss = it1.next();
					
		 %>
		<p class = "text-left">来自委员会成员<%=discuss.getPc_code() %>的讨论:  <b><%=discuss.getComments() %> </b></p>
		<%} } %>
	</div>
</body>
</html>