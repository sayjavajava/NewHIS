package com.sd.his.utill;


import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

        /*
         * @author    : irfan
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
         * @Package   : com.sd.ap.util
         * @FileName  : HISCoreUtil
         *
         * Copyright ©
         * SolutionDots,
         * All rights reserved.
         *
         */

public class HISCoreUtil {

    public static boolean isNull(String checkString) {
        if (null == checkString || checkString.trim().length() == 0 || checkString.trim().equalsIgnoreCase("null")) {
            return true;
        }
        return false;
    }

    public static boolean isValidObject(Object object) {
        if (null != object) {
            return true;
        }
        return false;
    }

    public static boolean isListEmpty(List<?> dataList) {
        if (null == dataList || dataList.isEmpty()) {
            return true;
        }
        return false;
    }

    /***
     * Valid mean , not null and not empty the list
     *
     *
     */
    public static boolean isListValid(List<?> dataList) {
        if (null == dataList || dataList.isEmpty()) {
            return false;
        }
        return true;
    }

    public static boolean isMapEmpty(Map<?, ?> dataMap) {
        if (null == dataMap || dataMap.isEmpty()) {
            return true;
        }
        return false;
    }

    public static boolean isSetEmpty(Set<?> dataSet) {
        if (null == dataSet || dataSet.isEmpty()) {
            return true;
        }
        return false;
    }

    public static long convertDateToMilliSeconds(String myDate) {
        //= "2014/10/29 18:10:45";
        if (myDate != null) {
            Instant instant = Instant.parse(myDate);

            return instant.toEpochMilli();
        } else {
            return 0;
        }
    }

    public static Date convertToTime(String str) {
        Date date = null;
        if (str != null) {
            SimpleDateFormat formatter = new SimpleDateFormat(HISConstants.TIME_FORMAT_PATTERN);
            try {
                date = formatter.parse(str);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        return date;
    }

    public static String convertTimeToString(Date time) {
         String formatedDate = null;
        if(time != null){
        SimpleDateFormat form = new SimpleDateFormat("hh:mm:ss");
        formatedDate= form.format(time);
      }
      return formatedDate;
    }

    public static String convertDateToString(Date date) {
        String formatedDate = null;
        if(date != null){
            SimpleDateFormat form = new SimpleDateFormat("dd MMMM yyyy");
            formatedDate= form.format(date);
        }
        return formatedDate;
    }

   public static Date convertToDate(String str) {
       Date date = null;
        if (str != null) {
           SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
           try {
               date = formatter.parse(str);
           } catch (ParseException e) {
               e.printStackTrace();
           }

       }
       return date;
   }

    public static Date convertToDateWithOutSecond(String str) {
        Date date = null;
        if (str != null) {
            SimpleDateFormat formatter = new SimpleDateFormat(HISConstants.DATE_FORMATE_TWO);
            try {
                date = formatter.parse(str);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        return date;
    }


    public static Date addTimetoDate(Date str , long duration) {
        Date date = null;
        if (str != null) {
            date = Date.from(str.toInstant().plusSeconds(duration*60));
            }
        return date;
    }

    public static String convertDateToStringWithZone(Date date) {
        String formatedDate = null;
        if (date != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
            formatedDate = formatter.format(date);
            //date = formatter.parse(str);
        }
        return formatedDate;
    }
    public static String convertDateAndTimeToString(Date date) {
        String formatedDate = null;
        if(date != null){
            SimpleDateFormat form = new SimpleDateFormat("dd MMMM yyyy : hh:mm:ss");
            formatedDate= form.format(date);
        }
        return formatedDate;
    }
}