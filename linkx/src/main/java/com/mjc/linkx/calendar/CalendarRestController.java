package com.mjc.linkx.calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CalendarRestController {

    @Autowired
    private CalendarService calendarService;

    @GetMapping("/api/calendarList")
    public List<CalendarDto> getCalendarList() throws Exception {
        return calendarService.calendarList();
    }




}
