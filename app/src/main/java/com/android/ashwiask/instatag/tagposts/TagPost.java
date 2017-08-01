package com.android.ashwiask.instatag.tagposts;

import java.util.Map;

/**
 * @author Ashwini Kumar.
 */

public class TagPost
{
    private String type;
    private Map<String, Map<String, Object>> images;

    public String getType()
    {
        return type;
    }

    public Map<String, Map<String, Object>> getImages()
    {
        return images;
    }
}
