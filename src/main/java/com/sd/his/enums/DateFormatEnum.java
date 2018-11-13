package com.sd.his.enums;



public enum DateFormatEnum {

        dd_MM_yyyy {
            @Override
            public String toString() {
                return "dd-MM-yyyy";
            }
        },
        yyyy_MM_dd {
            @Override
            public String toString() {
                return "yyyy-MM-dd";
            }
        },

        yyyyMMdd {
        @Override
        public String toString() {
            return "yyyy/MM/dd";
        }
        },
        ddMMYYYY {
        @Override
        public String toString() {
            return "dd/MM/YYYY";
        }
        },
        MMdd {
        @Override
        public String toString() {
            return "MM/dd";
        }
        },
        MM_dd {
        @Override
        public String toString() {
            return "MM-dd";
        }
    },

        /*yyyyMMddHHmmss{
        @Override
        public String toString() {
            return "yyyy-MM-dd-HH:mm:ss";
        }
        },
        yyyyMMddHHmmssSSS{
        @Override
        public String toString() {
            return "yyyy-MM-dd-HH:mm:ss:SSS";
        }
        },
        yyyyMMddHHmm{
        @Override
        public String toString() {
            return "yyyy-MM-dd-HH:mm";
        }
        },
        yyMMddHHmmss{
        @Override
        public String toString() {
            return "yy-MM-dd-HH:mm:ss";
        }
        },*/
       /* HHmmss{
        @Override
        public String toString() {
            return "HH:mm:ss";
        }
        },
        HHmm{
        @Override
        public String toString() {
            return "HH:mm:ss";
        }
        },*/
        yyyyMMdd0000{
        @Override
        public String toString() {
            return "yyyy-MM-dd-00:00";
        }
     },

   }



