package com.mixedcs.academy.controller;

import com.mixedcs.academy.model.Holiday;
import com.mixedcs.academy.repository.HolidaysRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Controller
public class HolidaysController {

    @Autowired
    private HolidaysRepo holidaysRepository;

    @GetMapping("/holidays/{display}")
    public String displayHolidays(@PathVariable String display,Model model) {
        if(null != display && display.equals("all")){
            model.addAttribute("weekend",true);
            model.addAttribute("weekend",true);
        }else if(null != display && display.equals("weekend")){
            model.addAttribute("weekend",true);
        }else if(null != display && display.equals("temporary")){
            model.addAttribute("temporary",true);
        }
        Iterable<Holiday> holidays = holidaysRepository.findAll();
        List<Holiday> holidayList = StreamSupport
                .stream(holidays.spliterator(), false)
                .collect(Collectors.toList());
        Holiday.Type[] types = Holiday.Type.values();
        for (Holiday.Type type : types) {
            model.addAttribute(type.toString(),
                    (holidayList.stream().filter(holiday -> holiday.getType().equals(type)).collect(Collectors.toList())));
        }
        return "holidays.html";
    }

}