package com.ceiec;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Hello world!
 *
 */
public class App 
{
    private static final Logger jul = Logger.getLogger(App.class.getName());

    public static void main( String[] args )
    {
        jul.log(Level.WARNING,"jul");

        Log4jExample.test();

        Log4j2Example.test();

        CommonsLoggingTest.test();
    }
}
