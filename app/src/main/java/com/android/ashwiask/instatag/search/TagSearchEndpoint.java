package com.android.ashwiask.instatag.search;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author Ashwini Kumar.
 */

public interface TagSearchEndpoint
{
    @GET("search")
    Single<TagSearchViewModel> getTagsByName(@Query("q") String tagName);
}
