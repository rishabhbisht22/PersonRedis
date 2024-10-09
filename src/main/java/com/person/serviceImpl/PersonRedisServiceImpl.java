package com.person.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.person.entity.Person;
import com.person.helper.Constants;
import com.person.helper.ErrorConstants;
import com.person.service.PersonRedisService;

@Service
public class PersonRedisServiceImpl implements PersonRedisService{

	@Autowired
	private RedisTemplate redisTemplate;

	@Override
	public Object savePersonData(Person person) {
		
		List<Person> findPerson = redisTemplate.opsForHash().values(Constants.HASH_KEY);
		List<Person> collect = findPerson.stream().filter(check -> check.getEmail()
				               .equalsIgnoreCase(person.getEmail())).collect(Collectors.toList());
		if(CollectionUtils.isEmpty(collect))
		{	
		     redisTemplate.opsForHash().put(Constants.HASH_KEY, person.getId(), person);
		     return person;
		}
		return ErrorConstants.EMAIL_ALREADY_EXISTS;
	}

	@Override
	public List<Person> getAllPersonList() {
		
		List<Person> list =  redisTemplate.opsForHash().values(Constants.HASH_KEY);
		if(CollectionUtils.isEmpty(list))
		{
			return null;
		}	
		return list;
	}

	@Override
	public Object getPersonById(int id) {
		
	    Person findPerson =  (Person) redisTemplate.opsForHash().get(Constants.HASH_KEY,id);
		if(ObjectUtils.isEmpty(findPerson))
		{
			return ErrorConstants.PERSON_NOT_FOUND;
		}	
		return findPerson;
	}

	@Override
	public Object deletePerson(int id) {
	
		Person findPerson =  (Person) redisTemplate.opsForHash().get(Constants.HASH_KEY,id);
		if(!ObjectUtils.isEmpty(findPerson))
		{	
		     redisTemplate.opsForHash().delete(Constants.HASH_KEY, id);
		     return id;
		}
		return ErrorConstants.PERSON_NOT_FOUND;
	}

	@Override
	public Object updatePersonData(Person person) {
		
	    Person findPerson =  (Person) redisTemplate.opsForHash().get(Constants.HASH_KEY,person.getId());
        if(!ObjectUtils.isEmpty(findPerson))
        {
    		List<Person> personByEmail = redisTemplate.opsForHash().values(Constants.HASH_KEY);
    		List<Person> filterByEmail = personByEmail.stream().filter(check -> check.getEmail()
    				               .equalsIgnoreCase(person.getEmail())).collect(Collectors.toList());
    		if(CollectionUtils.isEmpty(filterByEmail) || filterByEmail.get(0).getId()==person.getId())
    		{
    			 redisTemplate.opsForHash().put(Constants.HASH_KEY, person.getId(), person);
    		     return person;
    		}	
    		return ErrorConstants.EMAIL_ALREADY_EXISTS;
        }	
		return ErrorConstants.PERSON_NOT_FOUND;
	}
		
}
