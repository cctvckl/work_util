package com.ceiec;



/**
 * desc:
 *
 * @author : caokunliang
 * creat_date: 2018/10/31 0031
 * creat_time: 17:10
 **/
public class Log4j2Example {
    private static final org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger();

    public static void test() {
        logger.info("info ");
        logger.error("error");
        logger.warn("warn");
    }
}
