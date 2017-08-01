package com.android.ashwiask.instatag.search;

import com.google.gson.annotations.SerializedName;

/**
 * @author Ashwini Kumar.
 */

public class Tag
{
    @SerializedName("media_count")
    private long mediaCount;
    private String name;

    public long getMediaCount()
    {
        return mediaCount;
    }

    public String getName()
    {
        return name;
    }
}
