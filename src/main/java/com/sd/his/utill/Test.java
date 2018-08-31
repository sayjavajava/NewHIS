package com.sd.his.utill;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;

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
public class Test extends bike implements car {

    public static void main(String ...args){

     System.out.println(new BCryptPasswordEncoder().encode("Password*1"));

        LocalDateTime date = LocalDateTime.now();

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm a");
            String dateText = date.format(formatter);
            System.out.println(dateText);
        } catch (DateTimeException exc) {
            System.out.printf("%s can't be formatted!%n", date);
            throw exc;
        }
    }

}

interface car{
//    default String test(){
//        System.out.println("car");
//        return null;
//    }
    String test();
}
abstract class bike{
    public String test() {
   return null;
    }
}
