package com.ceiec;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;

/**
 * desc:
 *
 * @author : caokunliang
 * creat_date: 2018/10/31 0031
 * creat_time: 17:10
 **/
public class Log4jExample {
    private static Logger logger = Logger.getLogger(Log4jExample.class);

    public static void test() {
        logger.info("info ");
        logger.error("error");
        logger.warn("warn");
    }
}
