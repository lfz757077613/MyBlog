package com.qunar.lfz.assist;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Author: fuzhi.lai
 * Date: 2018/5/5 下午1:25
 * Create by Intellij idea
 */
/*
                       _oo0oo_
                      o8888888o
                      88" . "88
                      (| -_- |)
                      0\  =  /0
                    ___/`---'\___
                  .' \\|     |// '.
                 / \\|||  :  |||// \
                / _||||| -:- |||||- \
               |   | \\\  -  /// |   |
               | \_|  ''\---/''  |_/ |
               \  .-\__  '-'  ___/-. /
             ___'. .'  /--.--\  `. .'___
          ."" '<  `.___\_<|>_/___.' >' "".
         | | :  `- \`.;`\ _ /`;.`/ - ` : | |
         \  \ `_.   \_ __\ /__ _/   .-` /  /
     =====`-.____`.___ \_____/___.-`___.-'=====
                       `=---='
     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

               佛祖保佑         永无BUG
*/
@Slf4j
public final class DateTimeUtil {

    private static final DateTimeFormatter yyyyMMdd = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final DateTimeFormatter yyyy_MM_dd = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Deprecated
    public static String formatFullDate(@NonNull Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).format(yyyy_MM_dd);
    }

    @Deprecated
    public static String formatDate(@NonNull Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).format(yyyyMMdd);
    }

    @Deprecated
    public static Date parseFullDate(String date) {
        if (StringUtils.isBlank(date)) {
            throw new RuntimeException("date is blank");
        }
        LocalDateTime localDateTime = LocalDateTime.parse(date, yyyy_MM_dd);
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    @Deprecated
    public static Date parseDate(String date) {
        if (StringUtils.isBlank(date)) {
            throw new RuntimeException("date is blank");
        }
        LocalDate localDate = LocalDate.parse(date, yyyyMMdd);
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }
}
