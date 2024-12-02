package com.mjc.linkx.calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Controller
public class CalendarController {
    @Autowired
    private CalendarService calendarService;



    @RequestMapping("/calendarList")
    public String calendarList(Model model) throws Exception {
        List<CalendarDto> cal = calendarService.calendarList();
        model.addAttribute("calendarList", cal);
        return "/calendar/calendar";  // "calendar.html"로 이동
    }


    //캘린더 일정 추가
    @PostMapping("/calendarSave")
    @ResponseBody
    public CalendarDto calendarSave(@RequestBody Map<String, Object> map) throws Exception{
        CalendarDto cal = new CalendarDto();
        cal.setTitle((String) map.get("title"));

        //UTC시간을 LocalDateTime으로 변화
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        ZonedDateTime startUTC = ZonedDateTime.parse((String) map.get("start"), formatter).withZoneSameInstant(ZoneId.of("Asia/Seoul"));
        ZonedDateTime endUTC = map.get("end") != null ? ZonedDateTime.parse((String) map.get("end"), formatter).withZoneSameInstant(ZoneId.of("Asia/Seoul")) : null;

        //한국 시간으로 변환하여 저장
        cal.setStart1(startUTC.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        cal.setEnd(endUTC != null ? endUTC.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null);
        cal.setAllDay((Boolean) map.get("allDay"));

        // 저장한 일정의 key 값을 포함한 데이터를 다시 반환
        calendarService.calendarSave(cal);
        return cal;
    }

    //캘린더 일정 삭제
    @DeleteMapping("/calendarDelete")
    @ResponseBody
    public String calendarDelete(@RequestParam String no) throws Exception{
        try{
            calendarService.calendarDelete(no);
            return "success";
        }catch(Exception e){
            e.printStackTrace();
            return "fail";
        }
    }

    //캘린더 일정 수정
    @PutMapping("/eventUpdate/{no}")
    public String eventUpdate(@PathVariable String no, @RequestBody Map<String, Object> map){
        CalendarDto cal = new CalendarDto();
        cal.setCalendarNo(Long.valueOf(no));
        cal.setTitle((String) map.get("title"));

        // UTC 날짜 시간 형식을 MySQL이 이해할 수 있는 형식으로 변환
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        ZonedDateTime startUTC = ZonedDateTime.parse(map.get("start1").toString(), formatter).withZoneSameInstant(ZoneId.of("Asia/Seoul"));
        ZonedDateTime endUTC = map.get("end") != null ? ZonedDateTime.parse(map.get("end").toString(), formatter).withZoneSameInstant(ZoneId.of("Asia/Seoul")) :null;


        // MySQL 형식으로 변환
        cal.setStart1(startUTC.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        if (endUTC != null) {
            cal.setEnd(endUTC.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        } else {
            cal.setEnd(null);
        }


        cal.setAllDay((Boolean) map.get("allDay"));

        try{
            calendarService.eventUpdate(cal);
            return "success";
        }catch(Exception e){
            e.printStackTrace();
            return "fail";
        }
    }
}


