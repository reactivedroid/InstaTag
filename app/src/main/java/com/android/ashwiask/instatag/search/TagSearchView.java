package com.android.ashwiask.instatag.search;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author Ashwini Kumar.
 */

public interface TagSearchView
{
    void showProgress();

    void hideProgress();

    Observable<CharSequence> getQueryTextChangeObserver();

    void showSearchTags(List<Tag> tags);

    void initView(List<Tag> tags);

    void showError(Throwable throwable);
}
