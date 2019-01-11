package com.service;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.dao.AssignDao;
import com.dao.AuthorDao;
import com.dao.MemberDao;
import com.dao.PaperDao;
import com.dao.PersonDao;
import com.dao.ReportDao;
import com.service.JspToHtml;
import com.vo.Author;
import com.vo.Paper;
import com.vo.Person;
import com.vo.Report;
public class ChairService {

	static public void addMember(Person person,String pc_code)
	{
		 MemberDao memberdao = new MemberDao();
         memberdao.insertMember(person,pc_code);
	}
	static public void delMember(int person_id)
	{
		 MemberDao memberdao = new MemberDao();
		 memberdao.deleteMember(person_id);
	}
	static public void addAssign(String pc_code,int paper_number)
	{
		AssignDao assigndao = new AssignDao ();
	    assigndao.assigPaper(pc_code, paper_number);
	}
	static public void delAssign(String pc_code,int paper_number)
	{
		AssignDao assigndao = new AssignDao ();
		assigndao.deleteAssign(paper_number, pc_code);
	}
	static public boolean accept(String spn) throws IOException, FileNotFoundException
	{
		if(spn != null)
		{
			int paper_number = Integer.parseInt(spn);
			PaperDao paperdao = new PaperDao();
			PersonDao personDao = new PersonDao();
			AuthorDao authordao = new AuthorDao();
			Paper paper = paperdao.searchPaper(paper_number);
			ReportDao reportdao = new ReportDao();
			Person conAuthor = null;
			ArrayList<Author> authorList = authordao.searchAuthor(paper_number);
			 ArrayList <Report> reportList = reportdao.searchReport(paper_number);
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
	        	}
			}
			
		   // 收件人的电子邮件
			String to = conAuthor.getEmail();

		   // 发件人的电子邮件
			String from = "201630665021@mail.scut.edu.cn";
		   // 假设你是从本地主机发送电子邮件
		   String host = "localhost";

		   // 获取系统属性对象
		   Properties properties = System.getProperties();
		   
		   properties.setProperty("mail.user", "201630665021@mail.scut.edu.cn");
		   properties.setProperty("mail.password", "Liao981210");
		   // 设置邮件服务器
		   properties.setProperty("mail.smtp.host", host);
		   // 获取默认的Session对象。
		   Session mailSession = Session.getDefaultInstance(properties);
		   try{
		      // 创建一个默认的MimeMessage对象。
		      MimeMessage message = new MimeMessage(mailSession);
		      // 设置 From: 头部的header字段
		      message.setFrom(new InternetAddress(from));
		      // 设置 To: 头部的header字段
		      message.addRecipient(Message.RecipientType.TO,
		                               new InternetAddress(to));
		      // 设置 Subject: header字段
		      message.setSubject("论文通知函");
		      // 现在设置的实际消息
		      String text = "尊敬的"+conAuthor.getName()+"：\r\n" + 
		      		"\r\n" + 
		      		"IFIP 2.6的会议程序委员会收到了来自全世界的多篇论文提交。因此，我们必须在许多优秀的提交中做出决定。很荣幸通知您，您的论文《"+paper.getTitle()+"》 编号"+paper.getPaper_number()+"已被本会议接收。 \r\n" + 
		      		"\r\n" + 
		      		"谨致问候 \r\n" + 
		      		"\r\n" + 
		      		"Fred Lochovsky \r\n" + 
		      		"程序委员会主席 ";
		      //message.setText(text);

		   // 创建消息部分
		      BodyPart messageBodyPart = new MimeBodyPart();

		      // 填充消息
		      messageBodyPart.setText(text);
		      
		      // 创建多媒体消息
		      Multipart multipart = new MimeMultipart();

		      // 设置文本消息部分
		      multipart.addBodyPart(messageBodyPart);

		      // 附件部分
		      messageBodyPart = new MimeBodyPart();
		      
		      if(reportList != null )
		      {
			      Iterator <Report> it1 = reportList.iterator();
			      int k=0;
			      while(it1.hasNext())
			      {		
			    	  k++;
			    	  Report report = it1.next();
			    	  String html=JspToHtml.getCode("http://localhost:8080/CPRMS/report.jsp?paper_number="+report.getPaper_number()+"&pc_code="+report.getPc_code());
			    	  File file = new File("D:/report"+k+".html");
			    	  if(!file.exists()){ 
			              try { 
			                  file.createNewFile(); 
			              } catch (IOException e) { 
			                  e.printStackTrace(); 
			             } 
			    	  }
			    	  OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
			    	  writer.write(html);
			    	  writer.flush();
			    	  writer.close();
			    	  BodyPart bp = new MimeBodyPart();
			    	  String filename = "report"+k+".html";
			            // 得到数据源  
			            FileDataSource fds = new FileDataSource(file);  
			            // 得到附件本身并至入BodyPart  
			            bp.setDataHandler(new DataHandler(fds));  
			            bp.setFileName(filename);  
			            multipart.addBodyPart(bp);
			      }
		      }

		      message.setContent(multipart);
		      
		      
		      Transport transport = mailSession.getTransport("smtp");
		      transport.connect("smtp.mail.scut.edu.cn","201630665021@mail.scut.edu.cn","Liao981210");
		      transport.sendMessage(message,message.getAllRecipients());
		      transport.close();
		      
		      paperdao.acceptPaper(paper_number);
		      return true;
		   }catch (MessagingException mex) {
		      mex.printStackTrace();
		   }
		   
		}
		return false;
	}
	static public boolean reject(String spn) throws IOException, FileNotFoundException
	{
		if(spn != null)
		{
			int paper_number = Integer.parseInt(spn);
			PaperDao paperdao = new PaperDao();
			PersonDao personDao = new PersonDao();
			AuthorDao authordao = new AuthorDao();
			Paper paper = paperdao.searchPaper(paper_number);
			ReportDao reportdao = new ReportDao();
			Person conAuthor = null;
			ArrayList<Author> authorList = authordao.searchAuthor(paper_number);
			 ArrayList <Report> reportList = reportdao.searchReport(paper_number);
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
	        	}
			}
			
		   // 收件人的电子邮件
			String to = conAuthor.getEmail();

		   // 发件人的电子邮件
			String from = "201630665021@mail.scut.edu.cn";
		   // 假设你是从本地主机发送电子邮件
		   String host = "localhost";

		   // 获取系统属性对象
		   Properties properties = System.getProperties();
		   
		   properties.setProperty("mail.user", "201630665021@mail.scut.edu.cn");
		   properties.setProperty("mail.password", "Liao981210");
		   // 设置邮件服务器
		   properties.setProperty("mail.smtp.host", host);
		   // 获取默认的Session对象。
		   Session mailSession = Session.getDefaultInstance(properties);
		   try{
		      // 创建一个默认的MimeMessage对象。
		      MimeMessage message = new MimeMessage(mailSession);
		      // 设置 From: 头部的header字段
		      message.setFrom(new InternetAddress(from));
		      // 设置 To: 头部的header字段
		      message.addRecipient(Message.RecipientType.TO,
		                               new InternetAddress(to));
		      // 设置 Subject: header字段
		      message.setSubject("论文通知函");
		      // 现在设置的实际消息
		      String text = "尊敬的"+conAuthor.getName()+"：\r\n" + 
		      		"\r\n" + 
		      		"IFIP 2.6的会议程序委员会收到了来自全世界的多篇论文提交。因此，我们必须在许多优秀的提交中做出决定。很遗憾地通知您，您的论文《"+paper.getTitle()+"》 编号"+paper.getPaper_number()+"未被本会议接收。 \r\n" + 
		      		"\r\n" + 
		      		"谨致问候 \r\n" + 
		      		"\r\n" + 
		      		"Fred Lochovsky \r\n" + 
		      		"程序委员会主席 ";
		      //message.setText(text);

		   // 创建消息部分
		      BodyPart messageBodyPart = new MimeBodyPart();

		      // 填充消息
		      messageBodyPart.setText(text);
		      
		      // 创建多媒体消息
		      Multipart multipart = new MimeMultipart();

		      // 设置文本消息部分
		      multipart.addBodyPart(messageBodyPart);

		      // 附件部分
		      messageBodyPart = new MimeBodyPart();
		      
		      if(reportList != null )
		      {
			      Iterator <Report> it1 = reportList.iterator();
			      int k=0;
			      while(it1.hasNext())
			      {		
			    	  k++;
			    	  Report report = it1.next();
			    	  String html=JspToHtml.getCode("http://localhost:8080/CPRMS/report.jsp?paper_number="+report.getPaper_number()+"&pc_code="+report.getPc_code());
			    	  File file = new File("D:/report"+k+".html");
			    	  if(!file.exists()){ 
			              try { 
			                  file.createNewFile(); 
			              } catch (IOException e) { 
			                  e.printStackTrace(); 
			             } 
			    	  }
			    	  OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
			    	  writer.write(html);
			    	  writer.flush();
			    	  writer.close();
			    	  BodyPart bp = new MimeBodyPart();
			    	  String filename = "report"+k+".html";
			            // 得到数据源  
			            FileDataSource fds = new FileDataSource(file);  
			            // 得到附件本身并至入BodyPart  
			            bp.setDataHandler(new DataHandler(fds));  
			            bp.setFileName(filename);  
			            multipart.addBodyPart(bp);
			      }
		      }

		      message.setContent(multipart);
		      
		      
		      Transport transport = mailSession.getTransport("smtp");
		      transport.connect("smtp.mail.scut.edu.cn","201630665021@mail.scut.edu.cn","Liao981210");
		      transport.sendMessage(message,message.getAllRecipients());
		      transport.close();
		      
		      paperdao.rejectPaper(paper_number);
		      return true;
		   }catch (MessagingException mex) {
		      mex.printStackTrace();

		   }
		}
		return false;
	}
}
