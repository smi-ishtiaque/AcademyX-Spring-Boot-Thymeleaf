package com.mixedcs.academy.repository;

import com.mixedcs.academy.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepo extends JpaRepository<Person, Integer> {
    Person readByEmail(String email);
}
