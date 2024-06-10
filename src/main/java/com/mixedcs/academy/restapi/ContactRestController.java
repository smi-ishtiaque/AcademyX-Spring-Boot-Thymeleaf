package com.mixedcs.academy.restapi;

import com.mixedcs.academy.constants.AcademyConstants;
import com.mixedcs.academy.model.Contact;
import com.mixedcs.academy.model.Response;
import com.mixedcs.academy.repository.ContactRepo;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping(path = "/api/contact",produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
@CrossOrigin(origins="*")

public class ContactRestController {
    @Autowired
    ContactRepo contactRepository;

    @GetMapping("/getMessagesByStatus")
    //@ResponseBody
    public List<Contact> getMessagesByStatus(@RequestParam(name = "status")  String status){
        return contactRepository.findByStatus(status);
    }

    @GetMapping("/getAllMessageByStatus")
    //@ResponseBody
    public List<Contact> getAllMessageByStatus(@RequestBody Contact contact){
        if(null != contact && null != contact.getStatus()){
            return contactRepository.findByStatus(contact.getStatus());
        }else{
            return List.of();
        }
    }

    @PostMapping("/saveMessage")
    // @ResponseBody
    public ResponseEntity<Response> saveMessage(@RequestHeader("invocationFrom") String invocationFrom,
                                            @Valid @RequestBody Contact contact){
        log.info(String.format("Header invocationFrom = %s", invocationFrom));
        contactRepository.save(contact);
        Response response = new Response();
        response.setStatusCode("200");
        response.setStatusMessage("Message Saved Successfully!");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("isMessageSaved", "true")
                .body(response);
    }

    @DeleteMapping("/deleteMessage")
    public ResponseEntity<Response> deleteMessage(RequestEntity<Contact> requestEntity){
        HttpHeaders headers = requestEntity.getHeaders();
        headers.forEach((key, value) -> {
            log.info(String.format(
                    "Header '%s' = %s", key, value.stream().collect(Collectors.joining("|"))));
        });
        Contact contact = requestEntity.getBody();
        contactRepository.deleteById(contact.getContactId());
        Response response = new Response();
        response.setStatusCode("200");
        response.setStatusMessage("Message Successfully Deleted!");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PatchMapping("/closeMessage")
    public ResponseEntity<Response> closeMessage(@RequestBody Contact contactReq){
        Response response = new Response();
        Optional<Contact> contact = contactRepository.findById(contactReq.getContactId());
        if(contact.isPresent()){
            contact.get().setStatus(AcademyConstants.CLOSE);
            contactRepository.save(contact.get());
        }else{
            response.setStatusCode("400");
            response.setStatusMessage("Invalid Contact ID Received!");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(response);
        }
        response.setStatusCode("200");
        response.setStatusMessage("Message Successfully Closed!");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
