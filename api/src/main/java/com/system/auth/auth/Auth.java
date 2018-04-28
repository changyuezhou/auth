package com.system.auth.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import javax.servlet.http.HttpServletRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import java.io.IOException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.concurrent.TimeUnit;

@Component
@Configuration
public class Auth {
    private static String platformId;
    private static String secretKey;
    private static String openIdName = "open_id";
    private static String accessTokenName = "access_token";
    private static String userNameAlias = "user_name";
    private static String authHost = "127.0.0.1";
    private static String authPort = "8081";
    private static String signPath = "sign.html";
    private static String webHost = "127.0.0.1";
    private static String webPort = "8081";
    private static String getAccessTokenPath = "/api/auth/access_token";
    private static Integer MAX_RECORDS = 1000;
    private static Integer EXPIRED_TIME = 600;  // seconds

    private static LoadingCache<String, User> cache =
            CacheBuilder.newBuilder()
                    .maximumSize(MAX_RECORDS)
                    .expireAfterAccess(EXPIRED_TIME, TimeUnit.SECONDS)
                    .build(new CacheLoader<String, User>() {
                        @Override
                        public User load(String key) throws Exception {
                            return getUserInfoByToken(key);
                        }
                    });

    public String getOpenIdName() {
        return this.openIdName;
    }

    public String getAccessTokenName() {
        return this.accessTokenName;
    }

    public static String getPlatformId() {
        return platformId;
    }

    public static String getWebHost() {
        return webHost;
    }

    public static String getWebPort() {
        return webPort;
    }

    @Value("${auth.platform_id}")
    public void setPlatformId(String platformIdP) {
        this.platformId = platformIdP;
    }

    @Value("${auth.secret_key}")
    public void setSecretKey(String secretKeyP) {
        this.secretKey = secretKeyP;
    }

    @Value("${auth.auth_host}")
    public void setAuthHost(String authHostP) {
        this.authHost = authHostP;
    }

    @Value("${auth.auth_port}")
    public void setAuthPort(String authPortP) {
        this.authPort = authPortP;
    }

    @Value("${auth.web_host}")
    public void setWebHost(String webHostP) {
        this.webHost = webHostP;
    }

    @Value("${auth.web_port}")
    public void setWebPort(String webPortP) {
        this.webPort = webPortP;
    }

    @Value("${auth.get_access_token_path}")
    public void setGetAccessTokenPath(String getAccessTokenPathP) {
        this.getAccessTokenPath = getAccessTokenPathP;
    }

    @Value("${auth.cache.max_records}")
    public void setMaxRecords(Integer maxRecords) {
        this.MAX_RECORDS = maxRecords;
    }

    @Value("${auth.cache.expired}")
    public void setExpiredTime(Integer expiredTime) {
        this.EXPIRED_TIME = expiredTime;
    }

    @Value("${auth.cookie.open_id_alisa}")
    public void setOpenIdName(String openIdAlisa) {
        this.openIdName = openIdAlisa;
    }

    @Value("${auth.cookie.access_token_alisa}")
    public void setAccessTokenName(String accessTokenName) {
        this.accessTokenName = accessTokenName;
    }

    @Value("${auth.cookie.user_name_alisa}")
    public void setUserNameAlias(String userNameAlias) {
        this.userNameAlias = userNameAlias;
    }
    // #################################################################################################################

    public static void initialAuth(String platformIdParam, String secretKeyParam, String authHostParam, String authPortParam, String webHostParam, String webPortParam, String getAccessTokenPathParam, Integer MAX_RECORDS_PARAM, Integer EXPIRED_TIME_PARAM, String openIdNameParam, String accessTokenNameParam, String userNameAliasParam) {
        platformId = platformIdParam;
        secretKey = secretKeyParam;

        if (0 < authHostParam.length()) {
            authHost = authHostParam;
        }

        if (0 < authPortParam.length()) {
            authPort = authPortParam;
        }

        if (0 < webHostParam.length()) {
            webHost = webHostParam;
        }

        if (0 < webPortParam.length()) {
            webPort = webPortParam;
        }

        if (0 < getAccessTokenPathParam.length()) {
            getAccessTokenPath = getAccessTokenPathParam;
        }

        if (0 < userNameAliasParam.length()) {
            userNameAlias = userNameAliasParam;
        }

        if (0 < MAX_RECORDS_PARAM) {
            MAX_RECORDS = MAX_RECORDS_PARAM;
        }

        if (0 < EXPIRED_TIME_PARAM) {
            EXPIRED_TIME = EXPIRED_TIME_PARAM;
        }

        if (0 < openIdNameParam.length()) {
            openIdName = openIdNameParam;
        }

        if (0 < accessTokenNameParam.length()) {
            accessTokenName = accessTokenNameParam;
        }
    }

    public static UserInfo getUserInfo(HttpServletRequest request) {
        UserInfo userInfo = new UserInfo();

        String redirectBack = "http://" + webHost + ":" + webPort + "/" + getAccessTokenPath + "?openIdName=" + openIdName + "&accessTokenName=" + accessTokenName + "&userNameAlias=" + userNameAlias;
        String oriURL = request.getHeader("referer");

        try {
            redirectBack += "&oriURL=" + URLEncoder.encode(oriURL, "UTF-8");
            redirectBack = URLEncoder.encode(redirectBack, "UTF-8");

            String openId = getCookieValue(openIdName, request.getCookies());
            String accessToken = getCookieValue(accessTokenName, request.getCookies());

            String key = openId + "_" + accessToken;
            User user = cache.get(key);
            cache.put(key, user);
            userInfo.setCode(0);
            userInfo.setMsg("");
            userInfo.setData(user);
            return userInfo;
        } catch (Exception e) {
            // e.printStackTrace();

            String authToken = getAuthToken();
            String redirectURL = "http://" + authHost + ":" + authPort + "/" + signPath + "?isShow=true&authToken=" + authToken + "&platformId=" + platformId + "&redirectBack=" + redirectBack;
            userInfo.setCode(302);
            userInfo.setMsg(redirectURL);
        }

        return userInfo;
    }

    // #################################################################################################################

    private static String getAuthToken() {
        String random = Integer.toString(new Long(System.currentTimeMillis()).intValue());
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");

            String password = byteArrayToHexString(md5.digest((secretKey + random).getBytes("utf-8")));

            String response = PostRequest("/api/auth/apply_auth_token", "{\"platformId\" : \"" + platformId + "\",\"random\":\"" + random + "\", \"password\":\"" + password + "\"}");
            if (0 > response.length()) {
                return "";
            }

            ObjectMapper mapper = new ObjectMapper();
            ApplyAuthTokenInfo authTokenResponse = mapper.readValue(response, ApplyAuthTokenInfo.class);

            return authTokenResponse.getData().getAuthToken();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    private static String getCookieValue(String itemName, Cookie[] cookies) throws Exception {
        for (int i = 0; i < cookies.length; ++i) {
            Cookie item = cookies[i];

            if (item.getName().equalsIgnoreCase(itemName)) {
                return item.getValue();
            }
        }

        System.out.print(" ########################## cookie:" + itemName + " not found .......");

        throw new CustomizeException();
    }

    private static User getUserInfoByToken(String key) throws Exception {
        String[] keys = key.split("_");
        if (2 != keys.length) {
            return null;
        }

        String openId = keys[0];
        String accessToken = keys[1];


        return getUserInfoByOpenIdAccessToken(openId, accessToken);
    }

    private static User getUserInfoByOpenIdAccessToken(String openId, String accessToken) throws Exception {
        String response = PostRequest("/api/auth/user_info", "{\"platformId\" : \"" + platformId + "\",\"openId\":\"" + openId + "\", \"accessToken\":\"" + accessToken + "\"}");
        if (0 > response.length()) {
            throw new CustomizeException();
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            UserInfo userInfoResponse = mapper.readValue(response, UserInfo.class);

            return userInfoResponse.getData();
        } catch (Exception e) {
            throw e;
        }
    }

    private static String PostRequest(String path, String data) {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost("http://" + authHost + ":" + authPort + "/" + path);
        post.addHeader("content-type", "application/json");

        try {
            post.setEntity(new StringEntity(data,
                    ContentType.APPLICATION_JSON));
            HttpResponse response = client.execute(post);

            return EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    private static String byteArrayToHexString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        final String[] hexDigits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

        int n = b;
        if (n < 0) n = 256 + n;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }
}
