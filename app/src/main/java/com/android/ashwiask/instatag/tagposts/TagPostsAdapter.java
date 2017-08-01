package com.android.ashwiask.instatag.tagposts;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.ashwiask.instatag.R;
import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Ashwini Kumar.
 */

public class TagPostsAdapter extends RecyclerView.Adapter<TagPostsAdapter.TagPostHolder>
{
    private static final String TYPE_IMAGE = "image";
    private static final String THUMBNAIL = "thumbnail";
    private static final String IMAGE_URL = "url";

    private List<TagPost> tagPosts;
    private Context context;

    public TagPostsAdapter(List<TagPost> tagPosts)
    {
        this.tagPosts = tagPosts;
    }

    @Override
    public TagPostHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.tag_post_list_item, parent, false);
        return new TagPostHolder(view);
    }

    @Override
    public void onBindViewHolder(TagPostHolder holder, int position)
    {
        TagPost tagPost = tagPosts.get(position);
        if (tagPost.getType().equalsIgnoreCase(TYPE_IMAGE))
        {
            Map<String, Object> imageMap = tagPost.getImages().get(THUMBNAIL);
            String thumbnailUrl = (String) imageMap.get(IMAGE_URL);
            Glide.with(context).load(thumbnailUrl).placeholder(R.color.gray).into(holder.tagPost);
        } else
        {
            // no image for video type
        }
    }

    @Override
    public int getItemCount()
    {
        return tagPosts.size();
    }

    static class TagPostHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.post)
        ImageView tagPost;

        TagPostHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
