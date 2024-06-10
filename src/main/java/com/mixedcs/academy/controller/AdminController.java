package com.mixedcs.academy.controller;

import com.mixedcs.academy.model.Academy;
import com.mixedcs.academy.model.Courses;
import com.mixedcs.academy.model.Person;
import com.mixedcs.academy.repository.AcademyRepo;
import com.mixedcs.academy.repository.CoursesRepo;
import com.mixedcs.academy.repository.PersonRepo;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("admin")
public class AdminController {

    @Autowired
    AcademyRepo academyRepo;

    @Autowired
    PersonRepo personRepository;

    @Autowired
    CoursesRepo coursesRepository;

    @RequestMapping("/displayClasses")
    public ModelAndView displayClasses(Model model) {
        List<Academy> academies = academyRepo.findAll();
        ModelAndView modelAndView = new ModelAndView("classes.html");
        modelAndView.addObject("academies",academies);
        modelAndView.addObject("academies", new Academy());
        return modelAndView;
    }

    @PostMapping("/addNewClass")
    public ModelAndView addNewClass(Model model, @ModelAttribute("academies") Academy academies) {
        academyRepo.save(academies);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayClasses");
        return modelAndView;
    }

    @RequestMapping("/deleteClass")
    public ModelAndView deleteClass(Model model, @RequestParam int id) {
        Optional<Academy> academy = academyRepo.findById(id);
        for(Person person : academy.get().getPersons()){
            person.setAcademy(null);
            personRepository.save(person);
        }
        academyRepo.deleteById(id);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayClasses");
        return modelAndView;
    }

    @GetMapping("/displayTrainee")
    public ModelAndView displayTrainee(Model model, @RequestParam int classId, HttpSession session,
                                        @RequestParam(value = "error", required = false) String error) {
        String errorMessage = null;
        ModelAndView modelAndView = new ModelAndView("trainee.html");
        Optional<Academy> academy = academyRepo.findById(classId);
        modelAndView.addObject("academy",academy.get());
        modelAndView.addObject("person",new Person());
        session.setAttribute("academy",academy.get());
        if(error != null) {
            errorMessage = "Invalid Email entered!!";
            modelAndView.addObject("errorMessage", errorMessage);
        }
        return modelAndView;
    }

    @PostMapping("/addTrainee")
    public ModelAndView addTrainee(Model model, @ModelAttribute("person") Person person, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        Academy academy = (Academy) session.getAttribute("academy");
        Person personEntity = personRepository.readByEmail(person.getEmail());
        if(personEntity==null || !(personEntity.getPersonId()>0)){
            modelAndView.setViewName("redirect:/admin/displayTrainee?classId="+academy.getClassId()
                    +"&error=true");
            return modelAndView;
        }
        personEntity.setAcademy(academy);
        personRepository.save(personEntity);
        academy.getPersons().add(personEntity);
        academyRepo.save(academy);
        modelAndView.setViewName("redirect:/admin/displayTrainee?classId="+academy.getClassId());
        return modelAndView;
    }

    @GetMapping("/deleteTrainee")
    public ModelAndView deleteTrainee(Model model, @RequestParam int personId, HttpSession session) {
        Academy academy = (Academy) session.getAttribute("academy");
        Optional<Person> person = personRepository.findById(personId);
        person.get().setAcademy(null);
        academy.getPersons().remove(person.get());
        Academy academySaved = academyRepo.save(academy);
        session.setAttribute("academy",academySaved);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayTrainee?classId="+academy.getClassId());
        return modelAndView;
    }

    @GetMapping("/displayCourses")
    public ModelAndView displayCourses(Model model) {
        //List<Courses> courses = coursesRepository.findByOrderByNameDesc();
        List<Courses> courses = coursesRepository.findAll(Sort.by("name").descending());
        ModelAndView modelAndView = new ModelAndView("courses_secure.html");
        modelAndView.addObject("courses",courses);
        modelAndView.addObject("course", new Courses());
        return modelAndView;
    }

    @PostMapping("/addNewCourse")
    public ModelAndView addNewCourse(Model model, @ModelAttribute("course") Courses course) {
        ModelAndView modelAndView = new ModelAndView();
        coursesRepository.save(course);
        modelAndView.setViewName("redirect:/admin/displayCourses");
        return modelAndView;
    }

    @GetMapping("/viewTrainee")
    public ModelAndView viewTrainee(Model model, @RequestParam int id
            , HttpSession session, @RequestParam(required = false) String error) {
        String errorMessage = null;
        ModelAndView modelAndView = new ModelAndView("course_trainee.html");
        Optional<Courses> courses = coursesRepository.findById(id);
        modelAndView.addObject("courses",courses.get());
        modelAndView.addObject("person",new Person());
        session.setAttribute("courses",courses.get());
        if(error != null) {
            errorMessage = "Invalid Email Entered!!";
            modelAndView.addObject("errorMessage", errorMessage);
        }
        return modelAndView;
    }

    @PostMapping("/addTraineeToCourse")
    public ModelAndView addTraineeToCourse(Model model, @ModelAttribute("person") Person person,
                                           HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        Courses courses = (Courses) session.getAttribute("courses");
        Person personEntity = personRepository.readByEmail(person.getEmail());
        if(personEntity==null || !(personEntity.getPersonId()>0)){
            modelAndView.setViewName("redirect:/admin/viewTrainee?id="+courses.getCourseId()
                    +"&error=true");
            return modelAndView;
        }
        personEntity.getCourses().add(courses);
        courses.getPersons().add(personEntity);
        personRepository.save(personEntity);
        session.setAttribute("courses",courses);
        modelAndView.setViewName("redirect:/admin/viewTrainee?id="+courses.getCourseId());
        return modelAndView;
    }

    @GetMapping("/deleteTraineeFromCourse")
    public ModelAndView deleteTraineeFromCourse(Model model, @RequestParam int personId,
                                                HttpSession session) {
        Courses courses = (Courses) session.getAttribute("courses");
        Optional<Person> person = personRepository.findById(personId);
        person.get().getCourses().remove(courses);
        courses.getPersons().remove(person);
        personRepository.save(person.get());
        session.setAttribute("courses",courses);
        ModelAndView modelAndView = new
                ModelAndView("redirect:/admin/viewTrainee?id="+courses.getCourseId());
        return modelAndView;
    }

}