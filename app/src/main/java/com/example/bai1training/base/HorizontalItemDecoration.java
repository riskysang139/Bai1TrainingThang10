package com.example.bai1training.base;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HorizontalItemDecoration extends RecyclerView.ItemDecoration {
    /**
     * Evenly spaced between item.
     */
    private int spacing;

    public HorizontalItemDecoration(int spacing) {
        this.spacing = spacing;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        float frameWidth = ((parent.getWidth() - (float) spacing));
        int padding = (int) (parent.getWidth() / frameWidth);
        int itemPosition = parent.getChildAdapterPosition(view);
        outRect.top = spacing / 2;
        if (itemPosition == 0) {
            outRect.left = spacing;
        } else outRect.left = spacing / 2;
        outRect.right = padding;
        if (parent.getAdapter() != null) {//spacing last item.
            if (itemPosition == parent.getAdapter().getItemCount() - 1)
                outRect.right = spacing;
        }
    }
}
