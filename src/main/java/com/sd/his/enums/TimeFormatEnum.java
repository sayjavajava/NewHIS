package com.sd.his.enums;

public enum TimeFormatEnum {


    HHmmss{
        @Override
        public String toString() {
            return "HH:mm:ss";
        }
    },
    HHmm{
        @Override
        public String toString() {
            return "HH:mm";
        }
    },
   /* mmss{
        @Override
        public String toString() {
            return "mm:ss";
        }
    },*/
}
