package com.android.ashwiask.instatag.search;

import android.text.TextUtils;

import com.android.ashwiask.instatag.network.Network;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Ashwini Kumar.
 */

class TagSearchPresenterImpl implements TagSearchPresenter
{
    private static final long QUERY_DEBOUNCE_DURATION_IN_MILLIS = 800L;
    private TagSearchView tagSearchView;
    private TagSearchEndpoint tagSearchEndpoint;
    private List<Tag> tags;
    private CompositeDisposable compositeDisposable;

    TagSearchPresenterImpl(TagSearchView tagSearchView)
    {
        this.tagSearchView = tagSearchView;
        tags = new ArrayList<>(10);
        compositeDisposable = new CompositeDisposable();
        tagSearchEndpoint = Network.getRetrofit().create(TagSearchEndpoint.class);
    }

    @Override
    public void onScreenCreated()
    {
        tagSearchView.initView(tags);
    }

    @Override
    public void onQueryTextChanged(String queryTerm)
    {
        Disposable queryDisposable = tagSearchView.getQueryTextChangeObserver()
                .debounce(QUERY_DEBOUNCE_DURATION_IN_MILLIS, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(charSequence -> searchTag(charSequence.toString()), this::onError);
        compositeDisposable.add(queryDisposable);
    }

    private void searchTag(String tag)
    {
        if (!TextUtils.isEmpty(tag))
        {
            tagSearchView.showProgress();
            Disposable searchDisposable = tagSearchEndpoint.getTagsByName(tag)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::onSuccess, this::onError);
            compositeDisposable.add(searchDisposable);
        } else
        {
            tags.clear();
            tagSearchView.showSearchTags(tags);
        }
    }

    private void onSuccess(TagSearchViewModel tagSearchViewModel)
    {
        if (isViewAttached())
        {
            tags.clear();
            tags.addAll(tagSearchViewModel.getTags());
            tagSearchView.hideProgress();
            tagSearchView.showSearchTags(tags);
        } else
        {
            // do nothing
        }
    }

    private void onError(Throwable throwable)
    {
        if (isViewAttached())
        {
            tagSearchView.hideProgress();
            tagSearchView.showError(throwable);
        } else
        {
            // do nothing
        }
    }

    @Override
    public void onScreenDestroyed()
    {
        tagSearchView = null;
        cancelSearch();
    }

    @Override
    public void cancelSearch()
    {
        compositeDisposable.clear();
    }

    private boolean isViewAttached()
    {
        return tagSearchView != null;
    }
}
