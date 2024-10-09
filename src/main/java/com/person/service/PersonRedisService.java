package com.person.service;

import java.util.List;

import com.person.entity.Person;

public interface PersonRedisService {

	Object savePersonData(Person person);

	List<Person> getAllPersonList();

	Object getPersonById(int id);

	Object deletePerson(int id);

	Object updatePersonData(Person person);

}
