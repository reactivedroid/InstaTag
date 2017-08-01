package com.android.ashwiask.instatag.search;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Ashwini Kumar.
 */

public class TagSearchViewModel
{
    @SerializedName("data")
    private List<Tag> tags;

    public List<Tag> getTags()
    {
        return tags;
    }
}
