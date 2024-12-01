package com.mjc.linkx.calendar;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CalendarDto {
    private Long calendarNo;
    private String title;
    private String start1;
    private String end;
    private Boolean allDay;
}
