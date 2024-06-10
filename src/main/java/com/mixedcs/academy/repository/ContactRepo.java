package com.mixedcs.academy.repository;



import com.mixedcs.academy.model.Contact;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

// S M Ishtiaque
/*
@Repository stereotype annotation is used to add a bean of this class
type to the Spring context and indicate that given Bean is used to perform
DB related operations and
* */

@Repository
public interface ContactRepo extends CrudRepository<Contact, Integer> {


    List<Contact> findByStatus(String status);

    @Query("SELECT c FROM Contact c WHERE c.status = :status")
        //@Query(value = "SELECT * FROM contact_message c WHERE c.status = :status",nativeQuery = true)
    Page<Contact> findByStatusWithQuery(@Param("status") String status, Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE Contact c SET c.status = ?1 WHERE c.contactId = ?2")
    int updateStatusById(String status, int id);

    Page<Contact> findOpenMessage(@Param("status") String status, Pageable pageable);

    @Transactional
    @Modifying
    int updateMessageStatus(String status, int id);

    @Query(nativeQuery = true)
    Page<Contact> findOpenMessageNative(@Param("status") String status, Pageable pageable);

    @Transactional
    @Modifying
    @Query(nativeQuery = true)
    int updateMessageStatusNative(String status, int id);

}