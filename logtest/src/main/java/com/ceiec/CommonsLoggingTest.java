package com.ceiec;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * desc:
 *
 * @author : caokunliang
 * creat_date: 2018/10/31 0031
 * creat_time: 17:46
 **/

public class CommonsLoggingTest {
    private final static Log logger = LogFactory.getLog(CommonsLoggingTest.class);

    public static void test() {
        logger.debug("DEBUG ...");
        logger.info("INFO ...");
        logger.error("ERROR ...");
    }
}