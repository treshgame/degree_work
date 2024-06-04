package dev.university.degree.util;

import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AppointmentTimeHelper {
    public static List<String> getTimesForDay(LocalDate date){
        LocalTime currentTime = LocalTime.of(9, 0, 0);
        if(date.equals(LocalDate.now())){

            LocalTime nowTime = LocalTime.now();
            int hour = nowTime.getHour();
            int minute = 0;
            if(nowTime.getMinute() >= 30){
                hour += 1;
            }else{
                minute = 30;
            }
            currentTime = LocalTime.of(hour, minute, 0);
        }

        List<String> timeList = new ArrayList<>();
        LocalTime endTime = LocalTime.of(20, 0, 0);
        while (currentTime.isBefore(endTime)){
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(currentTime.getHour());
            stringBuilder.append(":");
            stringBuilder.append(currentTime.getMinute());
            if(currentTime.getMinute() == 0){
                stringBuilder.append(0);
            }
            timeList.add(stringBuilder.toString());
            currentTime = currentTime.plusMinutes(30);
        }
        return timeList;
    }
}
