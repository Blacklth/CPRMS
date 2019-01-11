<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.dao.PersonDao,com.vo.Person" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>提交摘要</title>
<link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css">  
<script src="https://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<% 
	Cookie[] cookies = request.getCookies();
	String message = (String)(request.getAttribute("message")); 
	String smessage = (String)(request.getAttribute("smessage")); 
	String name = null;	
	String email = null;
	Person person = null;
	String title = null;
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
		PersonDao personDao = new PersonDao();
		person = personDao.searchUser(email);
		if(person!=null)
		{
			title = person.getTitle();
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
							<li class="active" ><a href="submitAbs.jsp">提交摘要</a></li>
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
	<div class = "content" align = "left" style = "margin-left: 150px;margin-right: 250px;">
		<form class="form-group" method="post" action="SubmitAbs">
        	<div class="form-group">
            	<label for="">论文题目</label>
                <input name="paperTitle" class="form-control" type="text" placeholder="paper title">
             </div>
             <div class="form-group">
             	<label for="">论文摘要</label>
              	<textarea name="paperAbs" class="form-control" row="5" > </textarea>
             </div>
             <div class="form-group">
             	<label for="">论文类型 : &nbsp;&nbsp;&nbsp;</label>
    			<label class="radio-inline">
       			<input type="radio" name="paperType" id="research" value="research" checked>research
    			</label>
    			<label class="radio-inline">
       			<input type="radio" name="paperType" id="industrial" value="industrial">industrial
   				</label>
   				<label class="radio-inline">
       			<input type="radio" name="paperType" id="demo" value="demo">demo
   				</label>
             </div>
             <div class="form-group">
             	<div class = "row">
            		<div class="col-lg-4">
            			<label for="">作者1 姓名</label>
                		<input name="authorName1" class="form-control" type="text" placeholder="author name">
                	</div>
               	 	<div class="col-lg-4">
               	 		<label for="">作者1 邮箱</label>
                		<input name="authorEmail1" class="form-control" type="text" placeholder="author email">
                	</div>
                </div>
             </div>
             <div class="form-group">
             	<div class = "row">
            		<div class="col-lg-4">
            			<label for="">作者2 姓名</label>
                		<input name="authorName2" class="form-control" type="text" placeholder="author name">
                	</div>
               	 	<div class="col-lg-4">
               	 		<label for="">作者2 邮箱</label>
                		<input name="authorEmail2" class="form-control" type="text" placeholder="author email">
                	</div>
                </div>
             </div>
             <div class="form-group">
             	<div class = "row">
            		<div class="col-lg-4">
            			<label for="">作者3 姓名</label>
                		<input name="authorName3" class="form-control" type="text" placeholder="author name">
                	</div>
               	 	<div class="col-lg-4">
               	 		<label for="">作者3 邮箱</label>
                		<input name="authorEmail3" class="form-control" type="text" placeholder="author email">
                	</div>
                </div>
             </div>
             <div class="form-group">
             	<div class = "row">
            		<div class="col-lg-4">
            			<label for="">通讯作者姓名(必填)</label>
                		<input name="conAuthorName" class="form-control" type="text" placeholder="contact_author name">
                	</div>
               	 	<div class="col-lg-4">
               	 		<label for="">通讯作者邮箱(必填)</label>
                		<input name="conAuthorEmail" class="form-control" type="text" placeholder="contact author email">
                	</div>
                </div>
             </div>
             <div class="alert alert-info">注：以上作者均需已在本系统注册,否则无法添加！通讯作者不需要再填写在上述作者中!</div>
             <div class="form-group">
             	<h3><b>声明</b></h3>
              	<div>
              		<label for="">本论文是否有任何作者为程序委员会成员 : &nbsp;&nbsp;&nbsp;</label>
    				<label class="radio-inline">
       				<input type="radio" name="pc" id="is_pc" value="Y" checked>有
    				</label>
    				<label class="radio-inline">
       			 	<input type="radio" name="pc" id="no_pc" value="N">无
   				 	</label>
				</div>
             </div>
             <div class="form-group">
      			<button type="submit" class="btn btn-default">提交摘要</button>
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
                    <h1 class="text-center">提交结果</h1>
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