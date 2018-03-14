package com.system.auth.util;

import com.system.auth.AuthApplication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerInitialFactory {
    private static Logger logger;

    static {
        logger = LogManager.getLogger(AuthApplication.class.getName());
    }

    public static Logger getLogger() {
        return logger;
    }
}
