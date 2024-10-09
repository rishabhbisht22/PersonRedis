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
import com.person.service.PersonService;

@RequestMapping("/person/")
@RestController
public class PersonController {

	@Autowired
	private PersonService personService;
	
	private static final Logger log = LoggerFactory.getLogger(PersonController.class);
	
	@PostMapping("savePerson")
	public ResponseEntity<Response> savePersonDetails(@RequestBody Person person)
	{
		Object data = personService.savePersonDetails(person);
		if(ObjectUtils.isEmpty(data))
		{
			log.debug("Error occurred while saving person details");
			return ResponseEntity.ok().body(new Response<>(false, ErrorConstants.PERSON_SAVED_ERROR));
		}	
		else if(data.equals(ErrorConstants.EMAIL_ALREADY_EXISTS))
		{
			log.debug("Email Id already exists and the given email is :{}",person.getEmail());;
			return ResponseEntity.ok().body(new Response<>(false, ErrorConstants.EMAIL_ALREADY_EXISTS));
		}
		log.debug("Person details saved successfully");
		return ResponseEntity.ok().body(new Response<>(true, Constants.PERSON_DETAILS_SAVED, data));
	}
	
	@GetMapping("personList")
	public ResponseEntity<Response> getAllPersonDetailsList()
	{
		List<Person> data = personService.getAllPersonDetailsList();
		if(CollectionUtils.isEmpty(data))
		{
			log.debug("Person details list is empty");
			return ResponseEntity.ok().body(new Response<>(false, ErrorConstants.PERSON_LIST_EMPTY));
		}	
		log.debug("Person details list fetched successfully");
		return ResponseEntity.ok().body(new Response<>(true, Constants.PERSON_LIST_FETCHED, data));
	}
	
	@GetMapping("personById")
	public ResponseEntity<Response> getPersonDetailsById(@RequestParam int id)
	{
		Object data = personService.getPersonDetailsById(id);
		if(data.equals(ErrorConstants.PERSON_NOT_FOUND))
		{
			log.debug("Person details not found for this given id :{}",id);
			return ResponseEntity.ok().body(new Response<>(false, ErrorConstants.PERSON_NOT_FOUND));
		}	
		log.debug("Person details fetched successfully for this given id :{}",id);
		return ResponseEntity.ok().body(new Response<>(true, Constants.PERSON_DETAILS_FETCHED, data));
	}
	
	@DeleteMapping("deletePerson")
	public ResponseEntity<Response> deletePersonDetails(@RequestParam int id)
	{
		Object data = personService.deletePersonDetails(id);
		if(data.equals(ErrorConstants.PERSON_NOT_FOUND))
		{
			log.debug("Person details not found for this given id :{}",id);
			return ResponseEntity.ok().body(new Response<>(false, ErrorConstants.PERSON_NOT_FOUND));
		}	
		log.debug("Person details deleted successfully for this given id :{}",id);
		return ResponseEntity.ok().body(new Response<>(true, Constants.PERSON_DETAILS_DELETED, data));
	}
	
	@PutMapping("updatePerson")
	public ResponseEntity<Response> updatePersonDetails(@RequestBody Person person)
	{
		Object data = personService.updatePersonDetails(person);
		if(ObjectUtils.isEmpty(data))
		{
			log.debug("Error occurred while updating person details");
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
