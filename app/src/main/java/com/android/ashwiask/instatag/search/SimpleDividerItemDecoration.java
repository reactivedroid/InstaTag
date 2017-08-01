package com.android.ashwiask.instatag.search;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.ashwiask.instatag.R;

/**
 * @author Ashwini Kumar
 */
public class SimpleDividerItemDecoration extends RecyclerView.ItemDecoration
{
    private Drawable divider;
    private int margin;

    public SimpleDividerItemDecoration(Context context)
    {
        divider = ContextCompat.getDrawable(context, R.drawable.line_divider);
    }

    public SimpleDividerItemDecoration(Drawable divider)
    {
        this.divider = divider;
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state)
    {
        super.onDrawOver(c, parent, state);
        int left = parent.getPaddingLeft() + margin;
        int right = parent.getWidth() - parent.getPaddingRight() - margin;


        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++)
        {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + divider.getIntrinsicHeight();

            divider.setBounds(left, top, right, bottom);
            divider.draw(c);
        }
    }
}
