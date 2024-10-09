package com.person.service;

import java.util.List;

import com.person.entity.Person;

public interface PersonService {

	Object savePersonDetails(Person person);

	List<Person> getAllPersonDetailsList();

	Object getPersonDetailsById(int id);

	Object deletePersonDetails(int id);

	Object updatePersonDetails(Person person);

}
