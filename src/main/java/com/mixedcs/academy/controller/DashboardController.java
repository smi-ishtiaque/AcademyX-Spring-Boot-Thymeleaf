package com.mixedcs.academy.controller;


import com.mixedcs.academy.model.Person;
import com.mixedcs.academy.repository.PersonRepo;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class DashboardController {

    @Autowired
    PersonRepo personRepository;

    @org.springframework.beans.factory.annotation.Value("${academy.pageSize}")
    private int defaultPageSize;

    @Value("${academy.contact.successMessage}")
    private String message;

    @Autowired
    Environment environment;

    @RequestMapping("/dashboard")
    public String displayDashboard(Model model,Authentication authentication, HttpSession session) {
        Person person = personRepository.readByEmail(authentication.getName());
        model.addAttribute("username", person.getName());
        model.addAttribute("roles", authentication.getAuthorities().toString());
        if(null != person.getAcademy() && null != person.getAcademy().getName()){
            model.addAttribute("enrolledClass", person.getAcademy().getName());
        }
        session.setAttribute("loggedInPerson", person);
        logMessages();
        return "dashboard.html";
    }

    private void logMessages() {
        log.error("Error message from the Dashboard page");
        log.warn("Warning message from the Dashboard page");
        log.info("Info message from the Dashboard page");
        log.debug("Debug message from the Dashboard page");
        log.trace("Trace message from the Dashboard page");

        log.error("defaultPageSize value with @Value annotation is : "+defaultPageSize);
        log.error("successMessage value with @Value annotation is : "+message);

        log.error("defaultPageSize value with Environment is : "+environment.getProperty("academy.pageSize"));
        log.error("successMessage value with Environment is : "+environment.getProperty("academy.contact.successMessage"));
        log.error("Java Home environment variable using Environment is : "+environment.getProperty("JAVA_HOME"));
    }

}