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
			
		   // �ռ��˵ĵ����ʼ�
			String to = conAuthor.getEmail();

		   // �����˵ĵ����ʼ�
			String from = "201630665021@mail.scut.edu.cn";
		   // �������Ǵӱ����������͵����ʼ�
		   String host = "localhost";

		   // ��ȡϵͳ���Զ���
		   Properties properties = System.getProperties();
		   
		   properties.setProperty("mail.user", "201630665021@mail.scut.edu.cn");
		   properties.setProperty("mail.password", "Liao981210");
		   // �����ʼ�������
		   properties.setProperty("mail.smtp.host", host);
		   // ��ȡĬ�ϵ�Session����
		   Session mailSession = Session.getDefaultInstance(properties);
		   try{
		      // ����һ��Ĭ�ϵ�MimeMessage����
		      MimeMessage message = new MimeMessage(mailSession);
		      // ���� From: ͷ����header�ֶ�
		      message.setFrom(new InternetAddress(from));
		      // ���� To: ͷ����header�ֶ�
		      message.addRecipient(Message.RecipientType.TO,
		                               new InternetAddress(to));
		      // ���� Subject: header�ֶ�
		      message.setSubject("����֪ͨ��");
		      // �������õ�ʵ����Ϣ
		      String text = "�𾴵�"+conAuthor.getName()+"��\r\n" + 
		      		"\r\n" + 
		      		"IFIP 2.6�Ļ������ίԱ���յ�������ȫ����Ķ�ƪ�����ύ����ˣ����Ǳ��������������ύ������������������֪ͨ�����������ġ�"+paper.getTitle()+"�� ���"+paper.getPaper_number()+"�ѱ���������ա� \r\n" + 
		      		"\r\n" + 
		      		"�����ʺ� \r\n" + 
		      		"\r\n" + 
		      		"Fred Lochovsky \r\n" + 
		      		"����ίԱ����ϯ ";
		      //message.setText(text);

		   // ������Ϣ����
		      BodyPart messageBodyPart = new MimeBodyPart();

		      // �����Ϣ
		      messageBodyPart.setText(text);
		      
		      // ������ý����Ϣ
		      Multipart multipart = new MimeMultipart();

		      // �����ı���Ϣ����
		      multipart.addBodyPart(messageBodyPart);

		      // ��������
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
			            // �õ�����Դ  
			            FileDataSource fds = new FileDataSource(file);  
			            // �õ�������������BodyPart  
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
			
		   // �ռ��˵ĵ����ʼ�
			String to = conAuthor.getEmail();

		   // �����˵ĵ����ʼ�
			String from = "201630665021@mail.scut.edu.cn";
		   // �������Ǵӱ����������͵����ʼ�
		   String host = "localhost";

		   // ��ȡϵͳ���Զ���
		   Properties properties = System.getProperties();
		   
		   properties.setProperty("mail.user", "201630665021@mail.scut.edu.cn");
		   properties.setProperty("mail.password", "Liao981210");
		   // �����ʼ�������
		   properties.setProperty("mail.smtp.host", host);
		   // ��ȡĬ�ϵ�Session����
		   Session mailSession = Session.getDefaultInstance(properties);
		   try{
		      // ����һ��Ĭ�ϵ�MimeMessage����
		      MimeMessage message = new MimeMessage(mailSession);
		      // ���� From: ͷ����header�ֶ�
		      message.setFrom(new InternetAddress(from));
		      // ���� To: ͷ����header�ֶ�
		      message.addRecipient(Message.RecipientType.TO,
		                               new InternetAddress(to));
		      // ���� Subject: header�ֶ�
		      message.setSubject("����֪ͨ��");
		      // �������õ�ʵ����Ϣ
		      String text = "�𾴵�"+conAuthor.getName()+"��\r\n" + 
		      		"\r\n" + 
		      		"IFIP 2.6�Ļ������ίԱ���յ�������ȫ����Ķ�ƪ�����ύ����ˣ����Ǳ��������������ύ���������������ź���֪ͨ�����������ġ�"+paper.getTitle()+"�� ���"+paper.getPaper_number()+"δ����������ա� \r\n" + 
		      		"\r\n" + 
		      		"�����ʺ� \r\n" + 
		      		"\r\n" + 
		      		"Fred Lochovsky \r\n" + 
		      		"����ίԱ����ϯ ";
		      //message.setText(text);

		   // ������Ϣ����
		      BodyPart messageBodyPart = new MimeBodyPart();

		      // �����Ϣ
		      messageBodyPart.setText(text);
		      
		      // ������ý����Ϣ
		      Multipart multipart = new MimeMultipart();

		      // �����ı���Ϣ����
		      multipart.addBodyPart(messageBodyPart);

		      // ��������
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
			            // �õ�����Դ  
			            FileDataSource fds = new FileDataSource(file);  
			            // �õ�������������BodyPart  
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
