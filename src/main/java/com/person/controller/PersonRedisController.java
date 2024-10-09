package com.person.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.person.entity.Person;
import com.person.helper.Constants;
import com.person.helper.ErrorConstants;
import com.person.response.Response;
import com.person.service.PersonRedisService;

@RequestMapping("/personRedis/")
@RestController
public class PersonRedisController {

	@Autowired
	private PersonRedisService personRedisService;
	
	private static final Logger log = LoggerFactory.getLogger(PersonRedisController.class);
	
	@PostMapping("save")
	public ResponseEntity<Response> savePersonData(@RequestBody Person person)
	{
		Object data = personRedisService.savePersonData(person);
		if(ObjectUtils.isEmpty(data))
		{
			log.debug("Error occurred while saving person details");
			return ResponseEntity.ok().body(new Response<>(false, ErrorConstants.PERSON_SAVED_ERROR));
		}	
		else if(data.equals(ErrorConstants.EMAIL_ALREADY_EXISTS))
		{
			log.debug("Email id already exist and the given email id is :{}",person.getEmail());
			return ResponseEntity.ok().body(new Response<>(false, ErrorConstants.EMAIL_ALREADY_EXISTS));
		}	
		log.debug("Person details saved successfully");
		return ResponseEntity.ok().body(new Response<>(true, Constants.PERSON_DETAILS_SAVED, data));
	}
	
	@GetMapping("get")
	public ResponseEntity<Response> getAllPerson()
	{
		List<Person> data = personRedisService.getAllPersonList();
		if(CollectionUtils.isEmpty(data))
		{
			log.debug("Person details list is empty");
			return ResponseEntity.ok().body(new Response<>(false, ErrorConstants.PERSON_LIST_EMPTY));
		}	
		log.debug("Person details list fetched successfully");
		return ResponseEntity.ok().body(new Response<>(true, Constants.PERSON_LIST_FETCHED, data));
	}
	
	@GetMapping("getById")
	public ResponseEntity<Response> getPersonDataById(@RequestParam int id)
	{
		Object data = personRedisService.getPersonById(id);
		if(data.equals(ErrorConstants.PERSON_NOT_FOUND))
		{
			log.debug("Person details not found for this given id :{}",id);
			return ResponseEntity.ok().body(new Response<>(false, ErrorConstants.PERSON_NOT_FOUND));
		}	
        log.debug("Person details fetched successfully for this given id :{}",id);
        return ResponseEntity.ok().body(new Response<>(true, Constants.PERSON_DETAILS_FETCHED, data));
	}
	
	@DeleteMapping("delete")
	public ResponseEntity<Response> deletePerson(@RequestParam int id)
	{
		Object data = personRedisService.deletePerson(id);
		if(data.equals(ErrorConstants.PERSON_NOT_FOUND))
		{
			log.debug("Person details not found for this given id :{}",id);
			return ResponseEntity.ok().body(new Response<>(false, ErrorConstants.PERSON_NOT_FOUND));
		}	
		log.debug("Person details deleted successfully for this given id :{}",id);
		return ResponseEntity.ok().body(new Response<>(true, Constants.PERSON_DETAILS_DELETED, data));
	}
	
	@PutMapping("update")
	public ResponseEntity<Response> updatePersonData(@RequestBody Person person)
	{
		Object data = personRedisService.updatePersonData(person);
		if(ObjectUtils.isEmpty(data))
		{
			log.debug("Error occurred while update person details");
			return ResponseEntity.ok().body(new Response<>(false, ErrorConstants.PERSON_UPDATE_ERROR));
		}	
		else if(data.equals(ErrorConstants.PERSON_NOT_FOUND))
		{
			log.debug("Person details not found for this given id :{}",person.getId());
			return ResponseEntity.ok().body(new Response<>(false, ErrorConstants.PERSON_NOT_FOUND));
		}	
		else if(data.equals(ErrorConstants.EMAIL_ALREADY_EXISTS))
		{
			log.debug("Email id already exists and the given email is :{}",person.getEmail());
			return ResponseEntity.ok().body(new Response<>(false, ErrorConstants.EMAIL_ALREADY_EXISTS));
		}	
		log.debug("Person details updated successfully");
		return ResponseEntity.ok().body(new Response<>(true, Constants.PERSON_DETAILS_UPDATED, data));
	}
}
