<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.dao.PersonDao,com.vo.Person,java.util.ArrayList,com.dao.PaperDao,com.vo.Paper,com.dao.AuthorDao,com.vo.Author,java.util.Iterator" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>上传文件</title>
<link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css">  
<script src="https://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
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
	String smessage = (String)(request.getAttribute("smessage")); 
	String spn = request.getParameter("paper_number");
	System.out.println(message);
	System.out.println(name);
	System.out.println(email);
	Person person = null;
	String title = null;
	Paper paper = null;
	PaperDao paperdao = new PaperDao();
	if(email!=null)
	{
		PersonDao personDao = new PersonDao();
		person = personDao.searchUser(email);
		if(person!=null)
		{
			title = person.getTitle();
			if(spn != null)
			{
				int paper_number = Integer.parseInt(spn);
				paper = paperdao.searchPaper(paper_number);
			}
		}
	}
	
%>
<body style = "padding: 80px;">
	<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand" href="#">CPRMS</a>
			</div>
			<div>
				<ul class="nav navbar-nav navbar-left">
					<li ><a href="index.jsp">Call For Papers</a></li>
					<li class="dropdown active">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">
							论文提交
							<b class="caret"></b>
						</a>
						<ul class="dropdown-menu">
							<li ><a href="submitAbs.jsp">提交摘要</a></li>
							<li class="active" ><a href="submitPDF.jsp">提交论文</a></li>
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
	<div class = "content" align = "left" style = "margin-left: 250px;margin-right: 250px;">
		<p class = "text-left">Paper Number : <b><%=paper.getPaper_number() %> </b></p>
		<p class = "text-left">Title : <b><%=paper.getTitle() %> </b></p>
		<p class = "text-left">Type : <b><%=paper.getPaper_type() %> </b></p>
		<p class = "text-left">Abstract : <b><%=paper.getAbs()%> </b></p>
		<form  class="form-group"  method="post" action="SetPDF?paper_number=<%=paper.getPaper_number() %>" enctype="multipart/form-data">
    		<div class="form-group">
             	<label for="">选择文件 : (文件上传仅支持pdf格式) </label>
            	<input type="file" name="pdf" />
       		</div>
    		<div class="form-group">
      			<button type="submit" class="btn btn-default">上传</button>
  			</div>
		</form>
	</div>
	
	<div id="success" class="modal fade" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-body">
                    <button class="close" data-dismiss="modal">
                        <span>&times;</span>
                    </button>
                </div>
                <div class="modal-title">
                    <h1 class="text-center">上传结果</h1>
                </div>
                <div class="modal-body">
                	<%if (message != null ) { %> 
                	<div class="alert alert-danger"><%=message %></div>
   					<%} %>
					<%if (smessage != null ) { %> 
                	<div class="alert alert-success"><%=smessage %></div>
   					<%} %>
                </div>
            </div>
        </div>
    </div>
    <%if (message != null || smessage != null) { %> 
    <script type="text/javascript">
          $(function(){
                   $('#success').modal('show')
          });
   </script>
   <%} %>
</body>
</html>