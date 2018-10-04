package com.qunar.lfz.crawler;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import okhttp3.ConnectionPool;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.Dispatcher;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static okhttp3.internal.Util.assertionError;
/**
 * Author: fuzhi.lai
 * Date: 2018/8/10 上午12:23
 * Create by Intellij idea
 */
/*
                       _oo0oo_
                      o8888888o
                      88" . "88
                      (| -_- |)
                      0\  =  /0
                    ___/`---'\___
                  .' \\|     |// '.
                 / \\|||  :  |||// \
                / _||||| -:- |||||- \
               |   | \\\  -  /// |   |
               | \_|  ''\---/''  |_/ |
               \  .-\__  '-'  ___/-. /
             ___'. .'  /--.--\  `. .'___
          ."" '<  `.___\_<|>_/___.' >' "".
         | | :  `- \`.;`\ _ /`;.`/ - ` : | |
         \  \ `_.   \_ __\ /__ _/   .-` /  /
     =====`-.____`.___ \_____/___.-`___.-'=====
                       `=---='
     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

               佛祖保佑         永无BUG
*/
@Slf4j
public abstract class AbstractCrawler {

    private static X509TrustManager trustManager = createX509TrustManager();

    private static MediaType JSON_TYPE = MediaType.parse("application/json; charset=utf-8");

    private static final Dispatcher dispatcher = new Dispatcher();
    static {
        dispatcher.setMaxRequests(200);
        dispatcher.setMaxRequestsPerHost(200);
    }

    private final static OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(2, TimeUnit.SECONDS)
            .readTimeout(2, TimeUnit.SECONDS)
            .writeTimeout(2, TimeUnit.SECONDS)
            .dispatcher(dispatcher)
            .connectionPool(new ConnectionPool(200, 30, TimeUnit.SECONDS))
            .followRedirects(false)
            .followSslRedirects(false)
            .addInterceptor(new HttpLoggingInterceptor(HttpLoggingInterceptor.Level.BODY))
            .sslSocketFactory(defaultSslSocketFactory(trustManager), trustManager)
            .hostnameVerifier((s, sslSession) -> true)
            .build();


    protected final static LoadingCache<String, ConcurrentHashMap<String, Cookie>> cookieCache = CacheBuilder.newBuilder()
            .concurrencyLevel(10)
            .refreshAfterWrite(10, TimeUnit.MINUTES)
            .expireAfterAccess(30, TimeUnit.MINUTES)
            .maximumSize(100)
            .recordStats()
            .build(new CacheLoader<String, ConcurrentHashMap<String, Cookie>>() {
                @Override
                public ConcurrentHashMap<String, Cookie> load(String key) {
                    return new ConcurrentHashMap<>();
                }
            });

    protected OkHttpClient getClient() {
        return client;
    }

    protected OkHttpClient getClient(final String username) {
        return client.newBuilder().cookieJar(new CookieJar() {
            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                try {
                    ConcurrentHashMap<String, Cookie> cookieMap = cookieCache.get(username);
                    for (Cookie cookie : cookies) {
                        cookieMap.put(cookie.name(), cookie);
                    }
                } catch (ExecutionException e) {
                    log.error("saveFromResponse error", e);
                }
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {
                try {
                    ArrayList<Cookie> cookies = Lists.newArrayList();
                    ConcurrentHashMap<String, Cookie> cookieMap = cookieCache.get(username);
                    for (Cookie cookie : cookieMap.values()) {
                        if (cookie.matches(url) && cookie.expiresAt() != Long.MIN_VALUE) {
                            cookies.add(cookie);
                        }
                    }
                    return cookies;
                } catch (ExecutionException e) {
                    log.error("loadForRequest error", e);
                    return Collections.emptyList();
                }
            }
        }).build();
    }

    private static SSLSocketFactory defaultSslSocketFactory(X509TrustManager trustManager) {
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{trustManager}, null);
            return sslContext.getSocketFactory();
        } catch (GeneralSecurityException e) {
            throw assertionError("No System TLS", e);
        }
    }

    private static X509TrustManager createX509TrustManager() {
        return new X509TrustManager() {

            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[]{};
            }
        };
    }

}
