package com.mixedcs.academy.repository;


import com.mixedcs.academy.model.Holiday;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HolidaysRepo extends CrudRepository<Holiday, String> {

}