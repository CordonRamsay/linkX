package com.mjc.linkx.calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalendarServiceImpl implements CalendarService {

    @Autowired
    private ICalendarMapper ICalendarMapper;

    @Override
    public List<CalendarDto> calendarList() throws Exception{
        return ICalendarMapper.calendarList();
    }

    @Override
    public void calendarSave(CalendarDto cal) throws Exception {
        ICalendarMapper.calendarSave(cal);
    }

    @Override
    public void calendarDelete(String no) throws Exception {
        ICalendarMapper.calendarDelete(no);
    }

    @Override
    public void eventUpdate(CalendarDto cal) throws Exception {
        ICalendarMapper.eventUpdate(cal);
    }
}
