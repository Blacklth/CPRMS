<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.dao.PersonDao,com.vo.Person,java.util.ArrayList,com.dao.PaperDao,com.vo.Paper,com.dao.AuthorDao,com.vo.Author,java.util.Iterator,java.io.File" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>论文信息</title>
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
	String spn= request.getParameter("paper_number");
	String fileName = null;
	Person person = null;
	String title = null;
	Paper paper = null;
	Person conAuthor = null;
	PersonDao personDao = null;
	PaperDao paperdao = null;
	AuthorDao authordao = null;
	ArrayList<Author> authorList = null;
	if(email!=null)
	{
		personDao = new PersonDao();
		person = personDao.searchUser(email);
		if(person!=null)
		{
			title = person.getTitle();
			if(spn != null)
			{
				int paper_number = Integer.parseInt(spn);
				paperdao = new PaperDao();
				paper = paperdao.searchPaper(paper_number);
				authordao = new AuthorDao();
				authorList = authordao.searchAuthor(paper_number);
				fileName = paper_number + ".pdf";
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
	<!-- 注册窗口 -->
    <div id="register" class="modal fade" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-body">
                    <button class="close" data-dismiss="modal">
                        <span>&times;</span>
                    </button>
                </div>
                <div class="modal-title">
                    <h1 class="text-center">注册</h1>
                </div>
                <div class="modal-body">
                    <form class="form-group" method="post" action="Register">
                            <div class="form-group">
                                <label for="">姓名</label>
                                <input name= "name" class="form-control" type="text" placeholder="name">
                            </div>
                            <div class="form-group">
                                <label for="">头衔</label>
                                <input name= "title" class="form-control" type="text" placeholder="title">
                            </div>
                            <div class="form-group">
                                <label for="">机构</label>
                                <input name= "institution" class="form-control" type="text" placeholder="institution">
                            </div>
                            <div class="form-group">
                                <label for="">联系邮箱</label>
                                <input name= "email" class="form-control" type="email" placeholder="email">
                            </div>
                            <div class="form-group">
                                <label for="">联系电话</label>
                                <input name= "telephone" class="form-control" type="number" placeholder="telephone">
                            </div>
                            <div class="text-right">
                                <button class="btn btn-default" type="submit">提交</button>
                                <button class="btn btn-danger" data-dismiss="modal">取消</button>
                            </div>
                            <a href="" data-toggle="modal" data-dismiss="modal" data-target="#login">已有账号？点我登录</a>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <div class = "content" align = "left" style = "margin-left: 250px;margin-right: 250px;">
		<p class = "text-left">论文编号 : <b><%=paper.getPaper_number() %> </b></p>
		<p class = "text-left">标题 : <b><%=paper.getTitle() %> </b></p>
		<p class = "text-left">论文类型 : <b><%=paper.getPaper_type() %> </b></p>
		<p class = "text-left">摘要 : <b><%=paper.getAbs()%> </b></p>
		<p class = "text-left">作者中是否有本委员会成员 : 
			<b>
			<%if (paper.getIs_pc().equals("Y")) { %>
				 有
			<% }else { %>
			 	无
			<%} %>
		 	</b>
		</p>
		<p class = "text-left">作者 : 
			<b><%
					if(authorList != null)
					{
						Iterator <Author> it1 = authorList.iterator();
			        	while(it1.hasNext()){
			        		Author author = it1.next();
			        		Person p = personDao.searchUser(author.getPerson_id());
			        		if(author.getIs_contact().equals("Y"))
			        		{
			        			conAuthor = p;
			        		}
			    %>
			    <%=p.getTitle() %> <%=p.getName() %> ;
			    <%} } %>
			</b>
		</p>
		<p class = "text-left">通讯作者  : <b><%=conAuthor.getTitle()%> <%=conAuthor.getName() %> ;</b></p>
		<a href="PAPER/<%=fileName %>">
			<button  type="button" class="btn  btn-default">
			查看文件
       		</button>
       	</a>

	</div>
</body>
</html>