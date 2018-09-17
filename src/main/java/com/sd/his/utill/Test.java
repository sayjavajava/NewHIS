package com.sd.his.utill;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

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

    public static void main(String ...args){
     User user =new User("waqas","12312312");
     System.out.println(new BCryptPasswordEncoder().encode("Password*1"));

     String val =Optional.ofNullable(user)
             .flatMap(User::getAddress).flatMap(Address::getCountry)
                      .map(Country::getIsCode).
                       orElse("no");
     System.out.println("value our:"+ val);

        List<Integer> list = Arrays.asList(1,2,3);
        Iterator it = list.iterator();

        while(it.hasNext()) {
            it.remove();
            list.remove("waqas");
        }
    }
}
    class User{

    User(String name, String roll){

    }
     private Address address;

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

    }
    class Address{
        private Country country;

        public Optional<Country> getCountry() {
            return Optional.ofNullable(country);
        }

    }
    class Country{
      private String isCode;

        public String getIsCode() {
            return isCode;
        }

    }





