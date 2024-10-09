package com.person.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.person.entity.Person;
import com.person.helper.ErrorConstants;
import com.person.repository.PersonRepository;
import com.person.service.PersonService;

@Service
public class PersonServiceImpl implements PersonService{

	@Autowired
	private PersonRepository personRepository;

	@Override
	public Object savePersonDetails(Person person) 
	{	
		List<Person> findPerson = personRepository.findAll().stream().filter(check -> 
		      check.getEmail().equalsIgnoreCase(person.getEmail())).collect(Collectors.toList());
	    if(CollectionUtils.isEmpty(findPerson))
	    {
	    	Person data = personRepository.save(person);
	    	return data;
	    }	
	    return ErrorConstants.EMAIL_ALREADY_EXISTS;
	}

	@Override
	public List<Person> getAllPersonDetailsList() 
	{	
		List<Person> data = personRepository.findAll();
		if(CollectionUtils.isEmpty(data))
		{	
		    return null;
		}
		return data;
	}

	@Override
	public Object getPersonDetailsById(int id) 
	{
		Optional<Person> findPerson = personRepository.findById(id);
		if(findPerson.isPresent())
		{
			return findPerson.get();
		}	
		return ErrorConstants.PERSON_NOT_FOUND;
	}

	@Override
	public Object deletePersonDetails(int id) 
	{
		Optional<Person> findPerson = personRepository.findById(id);
		if(findPerson.isPresent())
		{
			personRepository.deleteById(id);
			return id;
		}	
		return ErrorConstants.PERSON_NOT_FOUND;
	}

	@Override
	public Object updatePersonDetails(Person person) 
	{
		Optional<Person> findPerson = personRepository.findById(person.getId());
		if(findPerson.isPresent())
		{
			Optional<Person> personByEmail = personRepository.findPersonByEmail(person.getEmail());
			if(personByEmail.isEmpty() || personByEmail.get().getId()==person.getId())
			{
				Person data = personRepository.save(person);
				return data;
			}	
			return ErrorConstants.EMAIL_ALREADY_EXISTS;
		}	
		return ErrorConstants.PERSON_NOT_FOUND;
	}
	
}
