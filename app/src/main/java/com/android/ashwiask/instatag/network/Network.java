package com.android.ashwiask.instatag.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.android.ashwiask.instatag.BaseApplication;
import com.android.ashwiask.instatag.BuildConfig;
import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import java.io.File;
import java.util.concurrent.TimeUnit;

import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Network
{
    private static final String INSTAGRAM_TAGS_BASE_URL = "https://api.instagram.com/v1/tags/";
    private static final String DEBUG = "debug";
    private static final int CONNECT_TIMEOUT_IN_MS = 30000;
    private static final int READ_TIMEOUT_IN_MS = 30000;
    private static final Object LOCK = new Object();
    private static okhttp3.OkHttpClient okHttpClient;

    private static OkHttpClient getOkHTTPClient()
    {
        synchronized (LOCK)
        {
            if (okHttpClient == null)
            {
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                ClearableCookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(BaseApplication.getContext()));

                if (BuildConfig.BUILD_TYPE.equals(DEBUG))
                {
                    logging.setLevel(HttpLoggingInterceptor.Level.BODY);

                    okHttpClient = new okhttp3.OkHttpClient.Builder()
                            .connectTimeout(CONNECT_TIMEOUT_IN_MS, TimeUnit.MILLISECONDS)
                            .readTimeout(READ_TIMEOUT_IN_MS, TimeUnit.MILLISECONDS)
                            .cookieJar(cookieJar)
                            .cache(addCacheDirectory())
                            .addInterceptor(logging)
                            .addInterceptor(new ApiInterceptor())
                            .build();
                } else
                {
                    logging.setLevel(HttpLoggingInterceptor.Level.NONE);
                    okHttpClient = new okhttp3.OkHttpClient.Builder()
                            .connectTimeout(CONNECT_TIMEOUT_IN_MS, TimeUnit.MILLISECONDS)
                            .readTimeout(READ_TIMEOUT_IN_MS, TimeUnit.MILLISECONDS)
                            .cookieJar(cookieJar)
                            .cache(addCacheDirectory())
                            .addInterceptor(new ApiInterceptor())
                            .build();
                }
            }
        }
        return okHttpClient;
    }

    private static Cache addCacheDirectory()
    {
        final int cacheSize = 5 * 1024 * 1024; // 5 MB
        File cacheDir = BaseApplication.getContext().getCacheDir();
        return new Cache(cacheDir, cacheSize);
    }

    public static synchronized Retrofit getRetrofit()
    {
        return new Retrofit.Builder()
                .baseUrl(INSTAGRAM_TAGS_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .client(getOkHTTPClient())
                .build();
    }

    public static boolean isNetworkAvailable(Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}

