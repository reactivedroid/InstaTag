package com.android.ashwiask.instatag.search;

/**
 * @author Ashwini Kumar.
 */

public interface TagSearchPresenter
{
    void onScreenCreated();

    void onQueryTextChanged(String queryTerm);

    void onScreenDestroyed();

    void cancelSearch();
}
