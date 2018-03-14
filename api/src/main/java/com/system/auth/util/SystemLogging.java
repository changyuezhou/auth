package com.system.auth.util;

import com.system.auth.AuthApplication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class SystemLogging {
    private static int DEBUG = 1;
    private static int INFO = 2;
    private static int WARN = 3;
    private static int ERROR = 4;
    private static int FATAL = 5;

    private static int OPERATION_START = 1;
    private static int SUCCESS = 2;
    private static int FAILED = 3;

    // private static String json_format = "{\"path\":\"%s\",\"method\": \"%s\", \"post_data\":\"%s\", \"response\":\"%s\", \"result\":\"%s\"}";

    private static Logger logger = LogManager.getLogger(AuthApplication.class.getName());

    public static void Logging(int level, String postObj, HttpServletRequest request, String response, int operationResult) {
        String operation_result = "SUCCESS";
        if (FAILED == operationResult) {
            operation_result = "FAILED";
        } else if (OPERATION_START == operationResult) {
            operation_result = "START";
        }

        if (DEBUG == level) {
            logger.debug("{\"url\":\"" + request.getRequestURL() + "\",\"method\": \" " + request.getMethod() + "\", \"post_data\":\" " + postObj + " \", \"response\":\" " + response + " \", \"result\":\" " + operation_result + " \"}");
        } else if (INFO == level) {
            logger.info("{\"url\":\"" + request.getRequestURL() + "\",\"method\": \" " + request.getMethod() + "\", \"post_data\":\" " + postObj + " \", \"response\":\" " + response + " \", \"result\":\" " + operation_result + " \"}");
        } else if (WARN == level) {
            logger.warn("{\"url\":\"" + request.getRequestURL() + "\",\"method\": \" " + request.getMethod() + "\", \"post_data\":\" " + postObj + " \", \"response\":\" " + response + " \", \"result\":\" " + operation_result + " \"}");
        } else if (ERROR == level) {
            logger.error("{\"url\":\"" + request.getRequestURL() + "\",\"method\": \" " + request.getMethod() + "\", \"post_data\":\" " + postObj + " \", \"response\":\" " + response + " \", \"result\":\" " + operation_result + " \"}");
        } else if (FATAL == level) {
            logger.fatal("{\"url\":\"" + request.getRequestURL() + "\",\"method\": \" " + request.getMethod() + "\", \"post_data\":\" " + postObj + " \", \"response\":\" " + response + " \", \"result\":\" " + operation_result + " \"}");
        }
    }

    public  static void LoggingException(int code, String msg) {
        logger.error("{\"error_code\":\"" + Integer.toString(code) + "\", \"error_msg\": \"" + msg + "\"}");
    }

    public static int getDEBUG() { return DEBUG; }
    public static int getERROR() { return ERROR; }
    public static int getINFO() { return INFO; }
    public static int getWARN() { return WARN; }
    public static int getFATAL() { return FATAL; }

    public static int getOperationStart() { return OPERATION_START; }
    public static int getSUCCESS() { return SUCCESS; }
    public static int getFAILED() { return FAILED; }
}
