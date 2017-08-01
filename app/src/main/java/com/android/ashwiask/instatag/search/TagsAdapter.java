package com.android.ashwiask.instatag.search;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.ashwiask.instatag.R;
import com.android.ashwiask.instatag.common.Constants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Ashwini Kumar.
 */

public class TagsAdapter extends RecyclerView.Adapter<TagsAdapter.TagHolder>
{
    private List<Tag> tags;
    private Context context;
    private Callback callback;

    public TagsAdapter(List<Tag> tags, Callback callback)
    {
        this.tags = tags;
        this.callback = callback;
    }

    @Override
    public TagHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.search_list_item, parent, false);
        TagHolder tagHolder = new TagHolder(view);
        tagHolder.itemView.setOnClickListener(v ->
                onItemClick(tagHolder.getAdapterPosition()));
        return tagHolder;
    }

    private void onItemClick(int position)
    {
        if (position != RecyclerView.NO_POSITION)
        {
            callback.onTagClicked(tags.get(position).getName());
        } else
        {
            // do nothing
        }
    }

    @Override
    public void onBindViewHolder(TagHolder holder, int position)
    {
        Tag tag = tags.get(position);
        holder.tag.setText(new StringBuilder(2).append(Constants.HASHTAG).append(tag.getName()).toString());
        holder.posts.setText(String.format(context.getResources().getString(R.string.public_posts), String.valueOf(tag.getMediaCount())));
    }

    @Override
    public int getItemCount()
    {
        return tags.size();
    }

    static class TagHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.tag)
        TextView tag;
        @BindView(R.id.post_count)
        TextView posts;

        TagHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface Callback
    {
        void onTagClicked(String tagName);
    }
}
