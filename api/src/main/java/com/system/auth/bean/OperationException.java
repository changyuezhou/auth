package com.system.auth.bean;

public class OperationException extends RuntimeException {
    private static final int SERVICE_EXCEPTION = -100000;
    private static final int USER_INPUT_EXCEPTION = -100001;
    private static final int SQL_OP_EXCEPTION = -100002;

    private static final int RECORD_IS_NOT_EXISTS = -100999;

    public static int getServiceException() { return SERVICE_EXCEPTION; }
    public static int getUserInputException() { return USER_INPUT_EXCEPTION; }
    public static int getSqlOpException() { return SQL_OP_EXCEPTION; }
    public static int getRecordIsNotExists() { return RECORD_IS_NOT_EXISTS; }

    public int code;
    public String msg;

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public OperationException() {
        this.code = 0;
        this.msg = "";
    }

    public OperationException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }
}
