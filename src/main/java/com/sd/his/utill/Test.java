package com.sd.his.utill;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
 * @author    : irfan nasim
 * @Date      : 16-Apr-18
 * @version   : ver. 1.0.0
 * 
 * ________________________________________________________________________________________________
 *
 *  Developer				Date		     Version		Operation		Description
 * ________________________________________________________________________________________________ 
 *	
 * 
 * ________________________________________________________________________________________________
 *
 * @Project   : HIS
 * @Package   : com.sd.his.utill
 * @FileName  : Test
 *
 * Copyright © 
 * SolutionDots, 
 * All rights reserved.
 * 
 */
public class Test {

    public static void main(String ...args) {

        LocalDate start = LocalDate.now();
        LocalDate end = LocalDate.now().plusMonths(1).with(TemporalAdjusters.lastDayOfMonth());
        LocalDate testEnd = LocalDate.of(2018,12,07);
        List<LocalDate> dates = Stream.iterate(start, date -> date.plusDays(1))
                .limit(ChronoUnit.DAYS.between(start, testEnd))
                .collect(Collectors.toList());
        System.out.println(dates.size());
        System.out.println(dates);
    }
}






