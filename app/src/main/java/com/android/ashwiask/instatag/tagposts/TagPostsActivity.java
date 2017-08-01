package com.android.ashwiask.instatag.tagposts;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.ashwiask.instatag.R;
import com.android.ashwiask.instatag.common.Constants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TagPostsActivity extends AppCompatActivity implements TagPostsView
{
    private static final String EXTRA_TAG = "extra_tag";
    private static final int NO_OF_COLUMNS = 3;

    @BindView(R.id.post_list)
    RecyclerView tagPostList;
    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.empty_post)
    TextView emptyPost;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private TagPostsPresenter tagPostsPresenter;

    public static void start(Context context, String tag)
    {
        Intent starter = new Intent(context, TagPostsActivity.class);
        starter.putExtra(EXTRA_TAG, tag);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_posts);
        ButterKnife.bind(this);
        String tag = getIntent().getStringExtra(EXTRA_TAG);
        tagPostsPresenter = new TagPostsPresenterImpl(this);
        tagPostsPresenter.onScreenCreated(tag);
        setToolbar(tag);
    }

    private void setToolbar(String tag)
    {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        setTitle(new StringBuilder(2).append(Constants.HASHTAG).append(tag).toString());
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
    public void showError(Throwable throwable)
    {
        Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showPosts(List<TagPost> tagPosts)
    {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, NO_OF_COLUMNS);
        gridLayoutManager.setAutoMeasureEnabled(true);
        TagPostsAdapter tagPostsAdapter = new TagPostsAdapter(tagPosts);
        tagPostList.setLayoutManager(gridLayoutManager);
        tagPostList.setAdapter(tagPostsAdapter);
        int spacing = getResources().getDimensionPixelSize(R.dimen.dimen_4dp);
        tagPostList.addItemDecoration(new GridItemDecoration(spacing, NO_OF_COLUMNS));
        tagPostList.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEmptyError(String tag)
    {
        emptyPost.setText(String.format(getResources().getString(R.string.post_empty_text), tag));
        emptyPost.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        tagPostsPresenter.onScreenDestroyed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == android.R.id.home)
        {
            onBackPressed();
            return true;
        } else
        {
            return super.onOptionsItemSelected(item);
        }
    }
}
