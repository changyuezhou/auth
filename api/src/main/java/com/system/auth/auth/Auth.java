package com.system.auth.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import javax.servlet.http.Cookie;
import java.io.IOException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.concurrent.TimeUnit;

public class Auth {
    private static String platformId = "";
    private static String secretKey = "";
    private static String openIdName = "open_id";
    private static String accessTokenName = "access_token";
    private static String authHost = "127.0.0.1";
    private static String authPort = "8081";
    private static String signURL = "http://192.168.56.101:8081/sign";
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

    public void setOpenIdName(String openIdName) {
        this.openIdName = openIdName;
    }

    public void setAccessTokenName(String accessTokenName) {
        this.accessTokenName = accessTokenName;
    }

    public void initialAuth(String platformId, String secretKey, String authHost, String authPort, Integer MAX_RECORDS, Integer EXPIRED_TIME, String openIdName, String accessTokenName) {
        this.platformId = platformId;
        this.secretKey = secretKey;

        if (0 < authHost.length()) {
            this.authHost = authPort;
        }

        if (0 < authPort.length()) {
            this.authPort = authPort;
        }

        if (0 < MAX_RECORDS) {
            this.MAX_RECORDS = MAX_RECORDS;
        }

        if (0 < EXPIRED_TIME) {
            this.EXPIRED_TIME = EXPIRED_TIME;
        }

        if (0 < openIdName.length()) {
            this.openIdName = openIdName;
        }

        if (0 < accessTokenName.length()) {
            this.accessTokenName = accessTokenName;
        }
    }

    public static UserInfo getUserInfo(HttpServletRequest request) {
        UserInfo userInfo = new UserInfo();

        String redirectBack = "";

        try {
            redirectBack = URLEncoder.encode(request.getLocalName() + ":" + Integer.toString(request.getLocalPort()), "UTF-8");
            
            String openId = getCookieValue(openIdName, request.getCookies());
            String accessToken = getCookieValue(accessTokenName, request.getCookies());

            User user = cache.get(openId + "_" + accessToken);
            userInfo.setCode(0);
            userInfo.setMsg("");
            userInfo.setData(user);
            return userInfo;
        } catch (Exception e) {
            String authToken = getAuthToken();
            String redirectURL = signURL + "?auth_token=" + authToken + "&redirect_back=" + redirectBack;
            userInfo.setCode(302);
            userInfo.setMsg(redirectURL);
        }

        return userInfo;
    }

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

        throw new CustomizeException();
    }

    private static User getUserInfoByToken(String key) {
        String[] keys = key.split("_");
        if (2 != keys.length) {
            return null;
        }

        String openId = keys[0];
        String accessToken = keys[1];


        return getUserInfoByOpenIdAccessToken(openId, accessToken);
    }

    private static User getUserInfoByOpenIdAccessToken(String openId, String accessToken) {
        String response = PostRequest("/api/auth/user_info", "{\"platformId\" : \"" + platformId + "\",\"openId\":\"" + openId + "\", \"accessToken\":\"" + accessToken + "\"}");
        if (0 > response.length()) {
            return null;
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            UserInfo userInfoResponse = mapper.readValue(response, UserInfo.class);

            return userInfoResponse.getData();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String PostRequest(String path, String data) {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(authHost + ":" + authPort + "/" + path);
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
