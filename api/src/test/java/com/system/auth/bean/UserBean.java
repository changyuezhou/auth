package com.system.auth.bean;

public class UserBean {
    private static String userId = "0bee89b07a248e27c83fc3d5951213c1";

    private static String userName = "user_test";

    private static String password = "f447b20a7fcbf53a5d5be013ea0b15af";

    private static Integer status = 0;

    private static String mobileNumber = "13800000000";

    private static String contactName = "测试员";

    private static String description = "";

    private static String createUserId = "0bee89b07a248e27c83fc3d5951213c1";

    private static Long updateTime = System.currentTimeMillis();

    private static Long createTime = System.currentTimeMillis();

    public static String getUserId() {
        return userId;
    }

    public static String getUserName() {
        return userName;
    }

    public static String getPassword() {
        return password;
    }

    public static Integer getStatus() {
        return status;
    }

    public static String getMobileNumber() {
        return mobileNumber;
    }

    public static String getContactName() {
        return contactName;
    }

    public static String getDescription() {
        return description;
    }

    public static String getCreateUserId() {
        return createUserId;
    }

    public static Long getUpdateTime() {
        return updateTime;
    }

    public static Long getCreateTime() {
        return createTime;
    }
}
