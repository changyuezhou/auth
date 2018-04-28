package com.system.auth.bean;

public class SessionBean {
    private static Integer id;

    private static String platformId = "c46e1ffcd01eca4d4f462f05144c0f50";

    private static Integer authTokenCreateTime = new Long(System.currentTimeMillis()).intValue();

    private static Integer authTokenExpire = new Long(System.currentTimeMillis()).intValue() + 84600;

    private static String authToken = "b8694d827c0f13f22ed3bc610c19ec15";

    private static Boolean authTokenUsed = true;

    private static String openId = "5c9597f3c8245907ea71a89d9d39d08e";

    private static String userId = "0bee89b07a248e27c83fc3d5951213c1";

    private static String code = "b773f348b381668a7bcb2003e8ea19fa";

    private static Integer codeCreateTime = new Long(System.currentTimeMillis()).intValue();

    private static Integer codeExpire = new Long(System.currentTimeMillis()).intValue() + 84600;

    private static Boolean codeUsed = true;

    private static String accessToken = "6749735e76f78adc4f16130d698e82df";

    private static Integer accessTokenCreateTime = new Long(System.currentTimeMillis()).intValue();

    private static Integer accessTokenExpire = new Long(System.currentTimeMillis()).intValue() + 84600;

    private static Boolean accessTokenUsed = false;

    private static String refreshToken = "d1665ecf97a7b271c2ecb0d5e9422613";

    private static Integer refreshTokenCreateTime = new Long(System.currentTimeMillis()).intValue();

    private static Integer refreshTokenExpire = new Long(System.currentTimeMillis()).intValue() + 84600;

    private static Boolean refreshTokenUsed = false;

    public static Integer getId() {
        return id;
    }

    public static String getPlatformId() {
        return platformId;
    }

    public static Integer getAuthTokenCreateTime() {
        return authTokenCreateTime;
    }

    public static Integer getAuthTokenExpire() {
        return authTokenExpire;
    }

    public static String getAuthToken() {
        return authToken;
    }

    public static Boolean getAuthTokenUsed() {
        return authTokenUsed;
    }

    public static String getOpenId() {
        return openId;
    }

    public static String getUserId() {
        return userId;
    }

    public static String getCode() {
        return code;
    }

    public static Integer getCodeCreateTime() {
        return codeCreateTime;
    }

    public static Integer getCodeExpire() {
        return codeExpire;
    }

    public static Boolean getCodeUsed() {
        return codeUsed;
    }

    public static String getAccessToken() {
        return accessToken;
    }

    public static Integer getAccessTokenCreateTime() {
        return accessTokenCreateTime;
    }

    public static Integer getAccessTokenExpire() {
        return accessTokenExpire;
    }

    public static Boolean getAccessTokenUsed() {
        return accessTokenUsed;
    }

    public static String getRefreshToken() {
        return refreshToken;
    }

    public static Integer getRefreshTokenCreateTime() {
        return refreshTokenCreateTime;
    }

    public static Integer getRefreshTokenExpire() {
        return refreshTokenExpire;
    }

    public static Boolean getRefreshTokenUsed() {
        return refreshTokenUsed;
    }
}
