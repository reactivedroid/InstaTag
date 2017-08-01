package com.android.ashwiask.instatag;

import android.app.Application;
import android.content.Context;

/**
 * @author Ashwini Kumar.
 */

public class BaseApplication extends Application
{
    private static Context context;

    public static Context getContext()
    {
        return context;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        context = this;
    }
}
