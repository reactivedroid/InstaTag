package com.android.ashwiask.instatag.network;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author Ashwini Kumar.
 */

public class ApiInterceptor implements Interceptor
{
    private static final String ACCESS_TOKEN = "access_token";

    @Override
    public Response intercept(Chain chain) throws IOException
    {
        Request original = chain.request();
        HttpUrl originalHttpUrl = original.url();

        HttpUrl url = originalHttpUrl.newBuilder()
                .addQueryParameter(ACCESS_TOKEN, "2004274594.6f9d3c4.03a455afa19a4b4fa18e3713ff76b69a")
                .build();

        Request.Builder requestBuilder = original.newBuilder()
                .url(url);

        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}

