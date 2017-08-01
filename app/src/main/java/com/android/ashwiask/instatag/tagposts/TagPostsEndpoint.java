package com.android.ashwiask.instatag.tagposts;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author Ashwini Kumar.
 */

public interface TagPostsEndpoint
{
    @GET("{tag}/media/recent")
    Single<TagPostViewModel> getRecentMediaPostsByTag(@Path("tag") String tag);
}
