<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ page import="com.dao.PersonDao,com.vo.Person,java.util.ArrayList,java.util.Iterator,com.dao.MemberDao,com.vo.Member" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理委员会成员</title>
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
	String message = (String)(request.getAttribute("message")); 
	Person person = null;
	Member m = null;
	String title = null;
	ArrayList<Member> memberList = null;
	MemberDao memberdao = null;
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
		PersonDao personDao = new PersonDao();
		person = personDao.searchUser(email);
		if(person!=null)
		{
			title = person.getTitle();
			m = memberdao.searchMember(person.getPerson_id());
			if(m != null && m.isIs_chair() == true)
			{
				memberList = new ArrayList<Member> ();
				memberList = memberdao.searchAllMember();
				Iterator <Member> it1 = memberList.iterator();
		        while(it1.hasNext()){
		            Member member = it1.next();
		            Person p =  personDao.searchUser(member.getPerson_id());
		            member.setEmail(p.getEmail());
		            member.setTitle(p.getTitle());
		            member.setName(p.getName());
		            member.setInstitution(p.getInstitution());
		            member.setTelephone(p.getTelephone());
		        }
			}
			else
			{
				response.sendRedirect("error.jsp?error=noChair");
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
					<li class="dropdown active">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">
							委员会管理
							<b class="caret"></b>
						</a>
						<ul class="dropdown-menu">
							<li class="active"><a href="managePC.jsp">管理委员会成员</a></li>
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
		<table class="table table-hover" >
			<caption>委员会主席</caption>
			<thead>
				<tr>
					<th>人员编号</th>
					<th>委员会编号</th>
					<th>头衔</th>
					<th>姓名</th>
					<th>机构</th>
					<th>邮箱</th>
					<th>联系方式</th>
				</tr>
			</thead>
			<tbody>
				<%
					if(memberList != null)
					{
						Iterator <Member> it2 = memberList.iterator();
			        	while(it2.hasNext()){
			        		Member member = it2.next();
			        		if(member.isIs_chair()){
			    %>
				<tr>
					<td><%=member.getPerson_id() %></td>
					<td><%=member.getPc_code() %></td>
					<td><%=member.getTitle()%></td>
					<td><%=member.getName()%></td>
					<td><%=member.getInstitution()%></td>
					<td><%=member.getEmail()%></td>
					<td><%=member.getTelephone()%></td>
				</tr>   
			    <%  } }  }%>
			</tbody>
		</table>
	</div>
	<div class="container"> 
		<form action="DelMember" method="get">          
		<table class="table table-hover" id="member">
			<caption>委员会成员</caption>
			<thead>
				<tr>
					<th></th>
					<th>人员编号</th>
					<th>委员会编号</th>
					<th>头衔</th>
					<th>姓名</th>
					<th>机构</th>
					<th>邮箱</th>
					<th>联系方式</th>
				</tr>
			</thead>
			<tbody>
				<%
					if(memberList != null)
					{
						Iterator <Member> it2 = memberList.iterator();
			        	while(it2.hasNext()){
			        		Member member = it2.next();
			        		if(!member.isIs_chair()){
			    %>
				<tr>
					<td>
                    	<input name="member" type="checkbox" value="<%=member.getPerson_id() %>">
                    </td>
					<td><%=member.getPerson_id() %></td>
					<td><%=member.getPc_code() %></td>
					<td><%=member.getTitle()%></td>
					<td><%=member.getName()%></td>
					<td><%=member.getInstitution()%></td>
					<td><%=member.getEmail()%></td>
					<td><%=member.getTelephone()%></td>
				</tr>   
			    <%  } }  }%>
			</tbody>
		</table>
        <button id="build" type="button" class="btn  btn-success" data-toggle="modal" data-target="#addMember">
            <span class="glyphicon glyphicon-plus" ></span>添加 
        </button>
        <button id="btnDel" type="submit" class="btn  btn-danger" >
            <span class="glyphicon glyphicon-minus" ></span>删除
        </button>
        </form>
	</div>
	<!-- 添加窗口 -->
    <div id="addMember" class="modal fade" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-body">
                    <button class="close" data-dismiss="modal">
                        <span>&times;</span>
                    </button>
                </div>
                <div class="modal-title">
                    <h1 class="text-center">添加委员会成员</h1>
                </div>
                <div class="modal-body">
                    <form class="form-group" method="post" action="AddMember">
                            <div class="form-group">
                                <label for="">姓名</label>
                                <input name= "name" class="form-control" type="text" placeholder="name">
                            </div>
                            <div class="form-group">
                                <label for="">联系邮箱</label>
                                <input name= "email" class="form-control" type="email" placeholder="email">
                            </div>
                            <div class="form-group">
                                <label for="">委员会成员编号</label>
                                <input name= "pc_code" class="form-control" type="text" placeholder="pc code">
                            </div>
                             <%if (message != null) { %> 
							<div class="alert alert-danger"><%=message %></div>
							<%} %>
                            <div class="alert alert-info">注：该人员需已在本系统注册,否则无法添加！</div>
                            <div class="text-right">
                                <button class="btn btn-default" type="submit">确认添加</button>
                                <button class="btn btn-danger" data-dismiss="modal">取消</button>
                            </div>
                            
                    </form>
                </div>
            </div>
        </div>
    </div>
    <%if (message != null) { %> 
    <script type="text/javascript">
          $(function(){
                   $('#addMember').modal('show')
          });
   </script>
   <%} %>
</body>
</html>