package com.mixedcs.academy.service;


import com.mixedcs.academy.config.AcademyProps;
import com.mixedcs.academy.constants.AcademyConstants;
import com.mixedcs.academy.model.Contact;
import com.mixedcs.academy.repository.ContactRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service

public class ContactService {
    @Autowired
    private ContactRepo contactRepo;

    @Autowired
    AcademyProps academyProps;

    public boolean saveMessageDetails(Contact contact){
        boolean isSaved = false;
        contact.setStatus(AcademyConstants.OPEN);
        Contact savedContact = contactRepo.save(contact);
        if(null != savedContact && savedContact.getContactId()>0) {
            isSaved = true;
        }
        return isSaved;
    }

    public Page<Contact> findMessageWithOpenStatus(int pageNum, String sortField, String sortDir){
        int pageSize = academyProps.getPageSize();
        if(null!=academyProps.getContact() && null!=academyProps.getContact().get("pageSize")){
            pageSize = Integer.parseInt(academyProps.getContact().get("pageSize").trim());
        }
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize,
                sortDir.equals("asc") ? Sort.by(sortField).ascending()
                        : Sort.by(sortField).descending());
        Page<Contact> messagePage = contactRepo.findByStatusWithQuery(
                AcademyConstants.OPEN,pageable);
        return messagePage;
    }

    public List<Contact> findMessageWithOpenStatus(){
        List<Contact> contactMessage = contactRepo.findByStatus(AcademyConstants.OPEN);
        return contactMessage;
    }

    public boolean updateMessageStatus(int contactId){
        boolean isUpdated = false;
        Optional<Contact> contact = contactRepo.findById(contactId);
        contact.ifPresent(contact1 -> {
            contact1.setStatus(AcademyConstants.CLOSE);
        });
        Contact updatedContact = contactRepo.save(contact.get());
        if(null != updatedContact && updatedContact.getUpdatedBy()!=null) {
            isUpdated = true;
        }
        return isUpdated;
    }

}
