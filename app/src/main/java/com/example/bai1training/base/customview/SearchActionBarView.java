package com.example.bai1training.base.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;

import com.example.bai1training.R;

/**
 * Created by cuonglv on 7/30/2021
 */
public class SearchActionBarView extends RelativeLayout implements TextWatcher, TextView.OnEditorActionListener {

    private ViewGroup mViewGroup;
    private RelativeLayout rlContainer;

    /**
     * edittext for write key
     */
    private EditText edtSearch;
    /**
     * clear search text.
     */
    private ImageView imgClearSearch;
    private ImageView imgSearchIcon;

    private int searchBarBackground = R.drawable.vtecom_bg_search_box;
    private int searchBarSearchIcon = R.drawable.vtecom_ic_search;
    private int searchBarClearIcon = R.drawable.vtecom_ic_close_black;
    private int searchBarIconColor = R.color.white;
    private int searchBarTextColor = R.color.white;
    private int searchBarHintTextColor = R.color.white;

    /**
     * to show clear button.
     */
    private boolean isShowClearButton = true;
    /**
     * callback
     */
    private SearchViewCallback mSearchViewCallBack;

    public void setSearchViewCallBack(SearchViewCallback mSearchViewCallBack) {
        this.mSearchViewCallBack = mSearchViewCallBack;
    }

    public SearchActionBarView(Context context) {
        super(context);
        handleTypedArray(context, null);
        init();
    }

    public SearchActionBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        handleTypedArray(context, attrs);
        init();
    }

    public SearchActionBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        handleTypedArray(context, attrs);
        init();
    }

    private void init() {
        mViewGroup = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.vtecom_search_bar_view, this);
        rlContainer = mViewGroup.findViewById(R.id.rl_container);
        edtSearch = mViewGroup.findViewById(R.id.edt_search_view);
        imgClearSearch = mViewGroup.findViewById(R.id.img_clear_search);
        imgSearchIcon = mViewGroup.findViewById(R.id.img_search_icon);
        edtSearch.addTextChangedListener(this);
        edtSearch.setOnEditorActionListener(this);
        imgClearSearch.setOnClickListener(v -> edtSearch.setText(""));
        imgClearSearch.setVisibility(GONE);

        rlContainer.setBackgroundResource(searchBarBackground);
        imgSearchIcon.setImageResource(searchBarSearchIcon);
        imgClearSearch.setImageResource(searchBarClearIcon);
        setColorSearchIcon(getColorFromResId(searchBarIconColor));
        setColorClearButton(getColorFromResId(searchBarIconColor));
        setTextColor(getColorFromResId(searchBarTextColor));
        setHintTextColor(getColorFromResId(searchBarHintTextColor));
    }

    private void handleTypedArray(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return;
        }

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.VtecomSearchActionBarView);

        searchBarBackground = typedArray.getResourceId(R.styleable.VtecomSearchActionBarView_vtecom_sb_background,
                R.drawable.vtecom_bg_search_box);
        searchBarSearchIcon =
                typedArray.getResourceId(R.styleable.VtecomSearchActionBarView_vtecom_sb_search_icon,
                        R.drawable.vtecom_ic_search);
        searchBarClearIcon =
                typedArray.getResourceId(R.styleable.VtecomSearchActionBarView_vtecom_sb_clear_icon,
                        R.drawable.vtecom_ic_close_black);
        searchBarSearchIcon =
                typedArray.getResourceId(R.styleable.VtecomSearchActionBarView_vtecom_sb_search_icon,
                        R.drawable.vtecom_ic_search);
        searchBarIconColor =
                typedArray.getResourceId(R.styleable.VtecomSearchActionBarView_vtecom_sb_icon_color,
                        R.color.white);
        searchBarTextColor =
                typedArray.getResourceId(R.styleable.VtecomSearchActionBarView_vtecom_sb_text_color,
                        R.color.white);
        searchBarHintTextColor =
                typedArray.getResourceId(R.styleable.VtecomSearchActionBarView_vtecom_sb_hint_text_color,
                        R.color.white);

        typedArray.recycle();
    }

    /**
     * set hint
     *
     * @param searchHint
     */
    public void setSearchHint(String searchHint) {
        edtSearch.setHint(searchHint);
    }

    /**
     * text for edittext.
     *
     * @param textSearch
     */
    public void setTextSearch(String textSearch) {
        edtSearch.setText(textSearch);
    }

    /**
     * set hint color
     *
     * @param searchHintColor
     */
    public void setSearchHintColor(int searchHintColor) {
        edtSearch.setHintTextColor(searchHintColor);
    }

    public void setEnableClickEdittext(boolean enableEditText) {
        edtSearch.setClickable(enableEditText);
    }

    /**
     * get text
     *
     * @return
     */

    public String getText() {
        return edtSearch.getText().toString();
    }

    /**
     * set text size
     *
     * @param textsize
     */
    public void setTextSize(int textsize) {
        edtSearch.setTextSize(textsize);
    }

    /**
     * set text color
     *
     * @param textColor
     */
    public void setTextColor(int textColor) {
        edtSearch.setTextColor(textColor);
    }

    /**
     * set hint text color
     *
     * @param textColor
     */
    public void setHintTextColor(int textColor) {
        edtSearch.setHintTextColor(textColor);
    }

    /**
     * show clear button
     *
     * @param isShow false is gone
     *               true is visible
     */
    public void setShowClearButton(boolean isShow) {
        this.isShowClearButton = isShow;
    }

    /**
     * set color clear button.
     *
     * @param color
     */
    public void setColorClearButton(int color) {
        imgClearSearch.setColorFilter(color, PorterDuff.Mode.SRC_IN);
    }

    public void setColorSearchIcon(int color) {
        imgSearchIcon.setColorFilter(color, PorterDuff.Mode.SRC_IN);
    }

    private int getColorFromResId(@ColorRes int color) {
        return ContextCompat.getColor(getContext(), color);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        if (mSearchViewCallBack != null) mSearchViewCallBack.beforeTextChanged(s.toString());
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (isShowClearButton) {
            if (s != null && s.toString().length() > 0) {
                if (imgClearSearch.getVisibility() == GONE)
                    imgClearSearch.setVisibility(VISIBLE);
            } else imgClearSearch.setVisibility(GONE);
        }
        if (mSearchViewCallBack != null) mSearchViewCallBack.onTextChange(s.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (mSearchViewCallBack != null) mSearchViewCallBack.onAfterTextChanged(s.toString());
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            if (mSearchViewCallBack != null)
                mSearchViewCallBack.onSubmitSearch(edtSearch.getText().toString());
        }
        return false;
    }

    public interface SearchViewCallback {
        default void onTextChange(String s) {
        }

        default void onAfterTextChanged(String s) {
        }

        default void beforeTextChanged(String s) {
        }

        void onSubmitSearch(String s);
    }
}
