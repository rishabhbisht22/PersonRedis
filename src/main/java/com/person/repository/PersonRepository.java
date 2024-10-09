package com.person.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.person.entity.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer>{

	@Query(value = "SELECT * from person where email = ?1", nativeQuery = true)
	Optional<Person> findPersonByEmail(String email);

}
