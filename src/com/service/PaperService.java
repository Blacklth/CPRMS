package com.service;

import com.dao.AuthorDao;
import com.dao.PaperDao;
import com.dao.PersonDao;
import com.vo.Author;
import com.vo.Paper;
import com.vo.Person;

public class PaperService {
	static public Paper newPaper(Paper paper)
	{	
		Paper p = null;
		PaperDao paperdao= new PaperDao();
		paperdao.insertAbs(paper);
		p = paperdao.searchPaper(paper.getTitle());
		
		return p;
	}
	static public void delPaper(int paper_number)
	{
		PaperDao paperdao= new PaperDao();
		paperdao.delPaper(paper_number);
	}
	static public boolean setAuthor(int paper_number,String email,String name)
	{
		Person person = null;
	    PersonDao persondao = new PersonDao();
	    Author author = new Author();
	    AuthorDao authordao = new AuthorDao();
    	person = persondao.searchUser(email, name);
    	if(person != null)
    	{
    		author.setPerson_id(person.getPerson_id());
    		author.setPaper_number(paper_number);
    		author.setIs_contact("N");
    		authordao.insertAuthor(author);
    		return true;
    	}
    	else
    	{
    		return false;
    	}
	}
	static public boolean setConAuthor(int paper_number,String email,String name)
	{
		Person person = null;
	    PersonDao persondao = new PersonDao();
	    Author author = new Author();
	    AuthorDao authordao = new AuthorDao();
    	person = persondao.searchUser(email, name);
    	if(person != null)
    	{
    		author.setPerson_id(person.getPerson_id());
    		author.setPaper_number(paper_number);
    		author.setIs_contact("Y");
    		authordao.insertAuthor(author);
    		return true;
    	}
    	else
    	{
    		return false;
    	}
	}
}
