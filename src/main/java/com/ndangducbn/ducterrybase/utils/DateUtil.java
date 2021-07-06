package com.ndangducbn.ducterrybase.utils;


import com.ndangducbn.ducterrybase.enums.ETypeDateFormat;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtil {
    private static final String STR_FORMAT_FULL = "ddMMyyyyHHmmssSSS";
    private static final String STR_FORMAT_SHORT = "ddMMyyyy";
    public static final ZoneId ZONEID = ZoneId.of("Asia/Ho_Chi_Minh");

    public static SimpleDateFormat simpleDateFormat(ETypeDateFormat type) {
        SimpleDateFormat format;
        switch (type) {
            case DATE_FORMAT_FULL:
                format = new SimpleDateFormat(STR_FORMAT_FULL);
                break;
            case DATE_FORMAT_SHORT:
                format = new SimpleDateFormat(STR_FORMAT_SHORT);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
        return format;
    }

    public static DateTimeFormatter dateTimeFormatter(ETypeDateFormat type) {
        DateTimeFormatter format;
        switch (type) {
            case DATE_FORMAT_FULL:
                format = DateTimeFormatter.ofPattern(STR_FORMAT_FULL);
                break;
            case DATE_FORMAT_SHORT:
                format = DateTimeFormatter.ofPattern(STR_FORMAT_SHORT);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
        return format;
    }

    public static String dateToString(ETypeDateFormat type, Date date) {
        return simpleDateFormat(type).format(date);
    }

    public static String ZDT2String(ETypeDateFormat type, ZonedDateTime zdt) {
        return zdt.format(dateTimeFormatter(type));
    }

   public static ZonedDateTime string2ZDT(String strDate){
        return ZonedDateTime.parse(strDate);
   }
   
    public static ZonedDateTime now() {
        ZoneId zoneId = ZoneOffset.UTC;
        return ZonedDateTime.now(zoneId);
    }


    public static Long getDuration(ZonedDateTime d1, ZonedDateTime d2) {
        return d1.toInstant().toEpochMilli() - d2.toInstant().toEpochMilli();
    }

    public static ZonedDateTime long2ZDT(Long  timeLong) {
        Instant instant = Instant.ofEpochMilli(timeLong);
        return ZonedDateTime.ofInstant(instant, ZoneOffset.UTC);
    }
    

    public static ZonedDateTime getStartOfDay(ZonedDateTime zdt) {
        return zdt.toLocalDate()
                .atStartOfDay()
                .atZone(zdt.getZone())
                .withLaterOffsetAtOverlap();
    }

    public static ZonedDateTime getEndOfDay(ZonedDateTime zdt) {
        return zdt.toLocalDate()
                .atStartOfDay()
                .atZone(zdt.getZone())
                .withLaterOffsetAtOverlap().plusDays(1);
    }


    public static Long zdt2Long() {
        ZoneId zoneId = ZoneOffset.UTC;
        return ZonedDateTime
                .now(zoneId)
                .toInstant().toEpochMilli();
    }

    public static Long localZDT2Long(ZonedDateTime zdt) {
        return zdt.toInstant().atZone(ZONEID)
                .toInstant()
                .toEpochMilli();
    }

}
