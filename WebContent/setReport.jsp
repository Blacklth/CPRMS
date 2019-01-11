<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ page import="com.dao.PersonDao,com.vo.Person,java.util.ArrayList,java.util.Iterator,com.dao.MemberDao,com.vo.Member,com.dao.PaperDao,com.vo.Paper,com.dao.PrefersDao,com.vo.Prefers" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>审阅论文</title>
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
	String title = null;
	MemberDao memberdao = new MemberDao();
	PaperDao paperdao = new PaperDao();
	Paper paper = null;
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
		<div class = "content" align = "left" style = "margin-left: 150px;margin-right: 250px;">     
			<form class="form-group" method="post" action="SetReport?paper_number=<%=paper.getPaper_number() %>&pc_code=<%=member.getPc_code() %>">
        		<p>Paper Number : <%=paper.getPaper_number() %></p>
        		<p>Title : <%=paper.getTitle() %></p>
				<hr />
				<br />
				<p> <b>Use Y(es) or N(o) and if absolutely necessary M(aybe) to answer the following (you must explain (N)o and (M)aybe answers in the comments section) </b></p>
          	  	<div class="form-group">
              		<div>
              			<label for="">The paper is relevant to ACD (Y/N/M): &nbsp;&nbsp;&nbsp;</label>
              			<br />
    					<label class="radio-inline">
       						<input type="radio" name="relevant" value="Y" checked>Y
    					</label>
    					<label class="radio-inline">
       			 			<input type="radio" name="relevant" value="N">N
   				 		</label>
   				 		<label class="radio-inline">
       			 			<input type="radio" name="relevant" value="M">M
   				 		</label>
					</div>
             	</div>
             	<div class="form-group">
              		<div>
              			<label for="">The paper is technically correct (Y/N/M): &nbsp;&nbsp;&nbsp;</label>
              			<br />
    					<label class="radio-inline">
       						<input type="radio" name="technically" value="Y" checked>Y
    					</label>
    					<label class="radio-inline">
       			 			<input type="radio" name="technically" value="N">N
   				 		</label>
   				 		<label class="radio-inline">
       			 			<input type="radio" name="technically" value="M">M
   				 		</label>
					</div>
             	</div>
             	<div class="form-group">
              		<div>
              			<label for="">The length and content of the paper are comparable to the expected final version (Y/N/M): &nbsp;&nbsp;&nbsp;</label>
              			<br />
    					<label class="radio-inline">
       						<input type="radio" name="length" value="Y" checked>Y
    					</label>
    					<label class="radio-inline">
       			 			<input type="radio" name="length" value="N">N
   				 		</label>
   				 		<label class="radio-inline">
       			 			<input type="radio" name="length" value="M">M
   				 		</label>
					</div>
             	</div>
             	<br />
             	<hr />
				<br />
				<p> <b>For the following categories, please assign integer scores from 1 to 8 using the following criteria : </b></p>
				<div class="form-group">
             	<div class = "row">
            		<div class="col-lg-3">
            			<label for="">Originality:</label>
                		<input name="originality" class="form-control" type="number" max="8" min="1" value="8">
                	</div>
               	 	<div class="col-lg-3">
               	 		<label for="">Impact:</label>
                		<input name="impact" class="form-control" type="number" max="8" min="1" value="8">
                	</div>
                	<div class="col-lg-3">
               	 		<label for="">Presentation:</label>
                		<input name="presentation" class="form-control" type="number" max="8" min="1" value="8">
                	</div>
                	<div class="col-lg-3">
               	 		<label for="">Technical Depth:</label>
                		<input name="depth" class="form-control" type="number" max="8" min="1" value="8">
                	</div>
                </div>
             	</div>
             	<div class="form-group">
      				<div class = "row">
      					<div class="col-lg-3">
               	 		<label for="">OVERALL RATING:</label>
                		<input name="overall_rating" class="form-control" type="number" max="8" min="1" value="8">
                		</div>
  			  		</div>
  			  	</div>
  			  	<br />
  			  	<hr />
  			  	<br />
  			  	<div class="form-group">
      				<div class = "row">
      					<div class="col-lg-3">
               	 		<label for="">Reviewer Confidence (0.5-1):</label>
                		<input name="confidence" class="form-control" type="text" >
                		</div>
  			  		</div>
  			  	</div>
  			  	<div class="form-group">
              		<div>
              			<label for="">Best Paper Award (i.e., destined to become a classic)? (Y/N): &nbsp;&nbsp;&nbsp;</label>
              			<br />
    					<label class="radio-inline">
       						<input type="radio" name="best_paper" value="Y" checked>Y
    					</label>
    					<label class="radio-inline">
       			 			<input type="radio" name="best_paper" value="N">N
   				 		</label>
					</div>
             	</div>
             	<br />
  			  	<hr />
  			  	<br />
             	<div class="form-group">
             		<label for="">Main Contribution(s):</label>
              		<textarea name="main_contribution" class="form-control" row="5" > </textarea>
             	</div>
             	<div class="form-group">
             		<label for="">Three strong points of the paper:</label>
              		<textarea name="strong_points" class="form-control" row="5" > </textarea>
             	</div>
             	<div class="form-group">
             		<label for="">Three weak points of the paper:</label>
              		<textarea name="weak_points" class="form-control" row="5" > </textarea>
             	</div>
             	<div class="form-group">
             		<label for="">Overall Summary (the rationale for your recommendation--maximum 3 lines):</label>
              		<textarea name="overall_summary" class="form-control" row="5" > </textarea>
             	</div>
             	<div class="form-group">
             		<label for="">Detailed Comments:</label>
              		<textarea name="detailed_comments" class="form-control" row="5" > </textarea>
             	</div>
             	<div class="form-group">
             		<label for="">Confidential comments to the PC, if any:</label>
              		<textarea name="confidential_comments" class="form-control" row="5" > </textarea>
             	</div>
              	<div class="form-group">
      				<button type="submit" class="btn btn-default">提交</button>
  			  	</div>
    		</form>
    	</div>
	</div>
</body>
</html>