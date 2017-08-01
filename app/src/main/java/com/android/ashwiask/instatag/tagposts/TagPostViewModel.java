package com.android.ashwiask.instatag.tagposts;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Ashwini Kumar.
 */

public class TagPostViewModel
{
    @SerializedName("data")
    private List<TagPost> tagPosts;

    public List<TagPost> getTagPosts()
    {
        return tagPosts;
    }
}
