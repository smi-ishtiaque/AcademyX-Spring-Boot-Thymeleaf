package com.mixedcs.academy.service;

import com.mixedcs.academy.constants.AcademyConstants;
import com.mixedcs.academy.model.Person;
import com.mixedcs.academy.model.Roles;
import com.mixedcs.academy.repository.PersonRepo;
import com.mixedcs.academy.repository.RolesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    @Autowired
    private PersonRepo personRepository;

    @Autowired
    private RolesRepo rolesRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public boolean createNewPerson(Person person){
        boolean isSaved = false;
        Roles role = rolesRepository.getByRoleName(AcademyConstants.TRAINEE_ROLE);
        person.setRoles(role);
        person.setPwd(passwordEncoder.encode(person.getPwd()));
        person = personRepository.save(person);
        if (null != person && person.getPersonId() > 0)
        {
            isSaved = true;
        }
        return isSaved;
    }
}
