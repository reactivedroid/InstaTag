package com.android.ashwiask.instatag.tagposts;

import java.util.List;

/**
 * @author Ashwini Kumar.
 */

public interface TagPostsView
{
    void showProgress();

    void hideProgress();

    void showError(Throwable throwable);

    void showPosts(List<TagPost> tagPosts);

    void showEmptyError(String tag);
}
