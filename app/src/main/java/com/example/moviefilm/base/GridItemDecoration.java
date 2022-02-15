package com.example.moviefilm.base;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by cuonglv on 8/16/2021
 */
public class GridItemDecoration extends RecyclerView.ItemDecoration {
    /**
     * Evenly spaced between item.
     */
    private int spacing;
    /**
     * column of recyclerview.
     */
    private int column;


    public GridItemDecoration(int spacing, int column) {
        this.spacing = spacing;
        this.column = column;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        GridLayoutManager.LayoutParams params = (GridLayoutManager.LayoutParams) view.getLayoutParams();
        int spanIndex = params.getSpanIndex();
        int spanSize = params.getSpanSize();
        // If it is in column 0 you apply the full offset on the start side, else only half
        if (spanIndex == 0) {
            outRect.left = spacing;
        } else {
            outRect.left = spacing / 2;
        }

        // If spanIndex + spanSize equals spanCount (it occupies the last column) you apply the full offset on the end, else only half.
        if (spanIndex + spanSize == column) {
            outRect.right = spacing;
        } else {
            outRect.right = spacing / 2;
        }


        // just add some vertical padding as well
        outRect.top = spacing / 2;
        outRect.bottom = spacing / 2;
        /**
         * nếu type 1 item nằm ngang thì bỏ hết khoảng cách
         */
        if (spanSize == 2) {
            outRect.left = 0;
            outRect.right = 0;
            outRect.top=0;
        }
        if(isLayoutRTL(parent)) {
            int tmp = outRect.left;
            outRect.left = outRect.right;
            outRect.right = tmp;
        }
    }

    @SuppressLint({"NewApi", "WrongConstant"})
    private static boolean isLayoutRTL(RecyclerView parent) {
        return parent.getLayoutDirection() == ViewCompat.LAYOUT_DIRECTION_RTL;
    }
}
