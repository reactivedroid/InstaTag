package com.android.ashwiask.instatag.tagposts;

import com.android.ashwiask.instatag.network.Network;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Ashwini Kumar.
 */

class TagPostsPresenterImpl implements TagPostsPresenter
{
    private TagPostsView tagPostsView;
    private TagPostsEndpoint tagPostsEndpoint;
    private CompositeDisposable compositeDisposable;
    private String tag;

    TagPostsPresenterImpl(TagPostsView tagPostsView)
    {
        this.tagPostsView = tagPostsView;
        tagPostsEndpoint = Network.getRetrofit().create(TagPostsEndpoint.class);
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onScreenCreated(String tag)
    {
        this.tag = tag;
        tagPostsView.showProgress();
        Disposable postDisposable = tagPostsEndpoint.getRecentMediaPostsByTag(tag)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::onSuccess, this::onError);
        compositeDisposable.add(postDisposable);
    }

    private void onSuccess(TagPostViewModel tagPostViewModel)
    {
        if (isViewAttached())
        {
            tagPostsView.hideProgress();
            List<TagPost> tagPosts = tagPostViewModel.getTagPosts();
            if (!tagPosts.isEmpty())
            {
                tagPostsView.showPosts(tagPosts);
            } else
            {
                tagPostsView.showEmptyError(tag);
            }
        } else
        {
            // do nothing
        }

    }

    private void onError(Throwable throwable)
    {
        if (isViewAttached())
        {
            tagPostsView.hideProgress();
            tagPostsView.showError(throwable);
        } else
        {
            // do nothing
        }
    }


    @Override
    public void onScreenDestroyed()
    {
        tagPostsView = null;
        compositeDisposable.clear();
    }

    private boolean isViewAttached()
    {
        return tagPostsView != null;
    }
}
