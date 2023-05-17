package com.prettyant.utils;

import org.apache.log4j.Logger;

public class LogUtils {
    public static String TAG = "prettyant";

    public static void f(Class clazz, String msg) {
        Logger logger = Logger.getLogger(clazz);
        logger.fatal(TAG + ":" + msg);

    }

    public static void e(Class clazz,String msg) {
        Logger logger = Logger.getLogger(clazz);
        logger.error(TAG + ":" + msg);

    }

    public static void w(Class clazz, String msg) {
        Logger logger = Logger.getLogger(clazz);
        logger.warn(TAG + ":" + msg);

    }

    public static void i(Class clazz, String msg) {
        Logger logger = Logger.getLogger(clazz);
        logger.info(TAG + ":" + msg);
    }

    public static void d(Class clazz,String msg) {
        Logger logger = Logger.getLogger(clazz);
        logger.debug(TAG + ":" + msg);
    }

    public static void t(Class clazz,String msg) {
        Logger logger = Logger.getLogger(clazz);
        logger.trace(TAG + ":" + msg);
    }
}
