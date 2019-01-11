package com.service;

import com.dao.PersonDao;
import com.vo.Person;

public class UserService {
	static public Person getPerson(String email, String name)
	{	
		PersonDao personDao = new PersonDao();
        Person person = personDao.searchUser(email, name);
		return person;
	}
	static public void addPerson(Person person)
	{	
		PersonDao personDao = new PersonDao();
		personDao.insertUser(person);
	}
	static public Person getPerson(String email)
	{	
		PersonDao personDao = new PersonDao();
        Person person = personDao.searchUser(email);
		return person;
	}
}
