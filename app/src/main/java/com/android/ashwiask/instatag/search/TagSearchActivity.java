package com.android.ashwiask.instatag.search;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.ashwiask.instatag.R;
import com.android.ashwiask.instatag.network.Network;
import com.android.ashwiask.instatag.tagposts.TagPostsActivity;
import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

public class TagSearchActivity extends AppCompatActivity implements SearchView.OnCloseListener, SearchView.OnQueryTextListener, TagSearchView, TagsAdapter.Callback
{
    @BindView(R.id.search_tag)
    SearchView searchView;
    @BindView(R.id.search_list)
    RecyclerView searchTags;
    @BindView(R.id.progress)
    ProgressBar progress;

    private TagSearchPresenter tagSearchPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_search);
        ButterKnife.bind(this);
        setSearchView();
        tagSearchPresenter = new TagSearchPresenterImpl(this);
        tagSearchPresenter.onScreenCreated();
    }

    private void setSearchView()
    {
        searchView.setIconified(false);
        searchView.setQueryHint(getString(R.string.search_hint));
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);
    }

    @Override
    public boolean onClose()
    {
        tagSearchPresenter.cancelSearch();
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query)
    {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText)
    {
        if (Network.isNetworkAvailable(this))
        {
            tagSearchPresenter.onQueryTextChanged(newText);
            return true;
        } else
        {
            Toast.makeText(this, getString(R.string.no_network_error), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    @Override
    public void showProgress()
    {
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress()
    {
        progress.setVisibility(View.GONE);
    }

    @Override
    public Observable<CharSequence> getQueryTextChangeObserver()
    {
        return RxSearchView.queryTextChanges(searchView);
    }

    @Override
    public void showSearchTags(List<Tag> tags)
    {
        searchTags.scrollToPosition(0);
        searchTags.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void initView(List<Tag> tags)
    {
        TagsAdapter tagsAdapter = new TagsAdapter(tags, this);
        searchTags.setLayoutManager(new LinearLayoutManager(this));
        searchTags.setHasFixedSize(true);
        searchTags.setAdapter(tagsAdapter);
        searchTags.addItemDecoration(new SimpleDividerItemDecoration(this));
    }

    @Override
    public void showError(Throwable throwable)
    {
        Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTagClicked(String tagName)
    {
        if (Network.isNetworkAvailable(this))
        {
            TagPostsActivity.start(this, tagName);
        } else
        {
            Toast.makeText(this, getString(R.string.no_network_error), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        tagSearchPresenter.onScreenDestroyed();
    }
}
