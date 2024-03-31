package com.example.Controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.*;
import java.time.format.DateTimeFormatter;


@RestController
public class calculatecontroller{
    @GetMapping("/calculate")
    public String calculateVacationPay(@RequestParam Double averageSalary, @RequestParam int vacationDays, @RequestParam(required = false) String vacationStartDate) {
        double vacationPay = averageSalary / 29.3 * vacationDays;
       
        if (vacationStartDate != null && !vacationStartDate.isEmpty()) 
        {
            LocalDate startDate = LocalDate.parse(vacationStartDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            LocalDate endDate = startDate.plusDays(vacationDays);
            int workDays = 0, holidays = 0;
            int currentYear = startDate.getYear();
            LocalDate[] holiDates = {
                LocalDate.of(currentYear, 1, 1),
                LocalDate.of(currentYear, 2, 23),    
                LocalDate.of(currentYear, 3, 8),   
                LocalDate.of(currentYear, 5, 1),   
                LocalDate.of(currentYear, 5, 9),
                LocalDate.of(currentYear, 6, 12),
                LocalDate.of(currentYear, 9, 5)              
            }; 

            for (LocalDate date = startDate; endDate.isAfter(date); date = date.plusDays(1)) 
            {
                if (date.getDayOfWeek() != DayOfWeek.SATURDAY && date.getDayOfWeek() != DayOfWeek.SUNDAY) 
                {
                    workDays++;
                }
                for (int i = 0; i < holiDates.length; i++) 
                {
                    if(date.getMonth()==holiDates[i].getMonth() && date.getDayOfMonth()==holiDates[i].getDayOfMonth()){
                        holidays++;
                    }
                }
            }
            vacationPay = averageSalary / 29.3 * (workDays - holidays);
        }

        return "Your vacation pay: " + vacationPay;
    }
}

