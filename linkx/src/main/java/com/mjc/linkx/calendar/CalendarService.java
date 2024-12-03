package com.mjc.linkx.calendar;

import java.util.List;

public interface CalendarService {

    //캘린더 일정 조회
    List<CalendarDto> calendarList() throws Exception;

    //캘린더 일정 저장
    void calendarSave(CalendarDto cal) throws Exception;

    //캘린더 일정 삭제
    void calendarDelete(String no) throws Exception;

    //캘린더 일정 수정
    void eventUpdate(CalendarDto cal) throws Exception;


}