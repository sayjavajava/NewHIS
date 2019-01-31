package com.sd.his;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestCode {
    public static void main(String[] args){
        LocalDate today = LocalDate.now();
        System.out.println("Current date: " + today);
        boolean checkDate =true ;

        //add 2 week to the current date
        LocalDate next2Week = today.plus(2, ChronoUnit.WEEKS);
        List<LocalDate> finalLocalDates = new ArrayList<>();

        while (checkDate){
            if(next2Week.isBefore(LocalDate.of(2019,03,15))){
                LocalDate addWeekRecurs = next2Week;
                LocalDate addWeek = next2Week.plusDays(7);
                List<LocalDate> dates = Stream.iterate(next2Week, date -> date.plusDays(1))
                        .limit(ChronoUnit.DAYS.between(next2Week, addWeek))
                        .collect(Collectors.toList());
                finalLocalDates.addAll(dates);
                next2Week = addWeekRecurs;
                System.out.println(" before jump date " + next2Week);
                next2Week = next2Week.plus(2,ChronoUnit.WEEKS);
                System.out.println(" after jump date " + next2Week);

            }else
                checkDate =false;
        }

        System.out.println("Next week days: " + next2Week.getDayOfWeek());

        finalLocalDates.stream().forEach(System.out::println);

    }
}
