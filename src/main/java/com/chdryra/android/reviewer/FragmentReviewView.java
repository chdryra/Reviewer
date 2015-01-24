/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 January, 2015
 */

package com.chdryra.android.reviewer;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.chdryra.android.myandroidwidgets.ClearableEditText;
import com.chdryra.android.mygenerallibrary.ViewHolderAdapter;

/**
 * Created by: Rizwan Choudrey
 * On: 23/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class FragmentReviewView extends Fragment {
    private GvDataList.GvType mDataType;
    private boolean           mIsEdit;

    private ReviewView mReviewView;

    private LinearLayout mLayout;
    private TextView     mSubjectView;
    private RatingBar    mRatingBar;
    private Button       mBannerButton;
    private GridView     mGridView;

    private int mMaxGridCellWidth;
    private int mMaxGridCellHeight;
    private int mCellWidthDivider  = 1;
    private int mCellHeightDivider = 1;

    private GridViewImageAlpha mGridViewImageAlpha = GridViewImageAlpha.MEDIUM;

    /**
     * Settings for GridView transparency with respect to background image.
     */
    public enum GridViewImageAlpha {
        TRANSPARENT(0),
        MEDIUM(200),
        OPAQUE(255);

        private final int mAlpha;

        private GridViewImageAlpha(int alpha) {
            this.mAlpha = alpha;
        }

        public int getAlpha() {
            return mAlpha;
        }
    }

    /**
     * Settings for grid cell dimensions with respect to screen width.
     */
    public enum CellDimension {
        FULL, HALF, QUARTER
    }

    public FragmentReviewView(GvDataList.GvType dataType, boolean isEdit) {
        mDataType = dataType;
        mIsEdit = isEdit;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        updateUi();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mReviewView = FactoryReviewView.newReviewView(this, mDataType, mIsEdit);
        setGridCellDimension(CellDimension.HALF, CellDimension.QUARTER);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_review_grid, container, false);

        mLayout = (LinearLayout) v.findViewById(R.id.review_grid_linearlayout);
        mSubjectView = (ClearableEditText) v.findViewById(R.id.review_subject_edit_text);
        mRatingBar = (RatingBar) v.findViewById(R.id.review_rating_bar);
        mBannerButton = (Button) v.findViewById(R.id.banner_button);
        mGridView = (GridView) v.findViewById(R.id.gridview_data);
        mGridView.setDrawSelectorOnTop(true);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        mMaxGridCellWidth = Math.min(displaymetrics.widthPixels, displaymetrics.heightPixels);
        //noinspection SuspiciousNameCombination
        mMaxGridCellHeight = mMaxGridCellWidth;

        initUi();
        updateUi();

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, android.view.MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        mReviewView.getMenuItemAction().inflateMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        return mReviewView.getMenuItemAction().onItemSelected(item) || super
                .onOptionsItemSelected(item);
    }

    public void updateUi() {
        updateSubjectTextUi();
        updateRatingBarUi();
        updateBannerButtonUi();
        updateGridDataUi();
        updateCover();
    }

    void setGridCellDimension(CellDimension width, CellDimension height) {
        mCellWidthDivider = 1;
        mCellHeightDivider = 1;

        if (width == CellDimension.HALF) {
            mCellWidthDivider = 2;
        } else if (width == CellDimension.QUARTER) {
            mCellWidthDivider = 4;
        }

        if (height == CellDimension.HALF) {
            mCellHeightDivider = 2;
        } else if (height == CellDimension.QUARTER) {
            mCellHeightDivider = 4;
        }
    }

    void initUi() {
        initSubjectUi();
        initRatingBarUi();
        initBannerButtonUi();
        initDataGridUi();
    }

    void initSubjectUi() {
        if (isEditable()) {
            mSubjectView.setFocusable(true);
            ((ClearableEditText) mSubjectView).makeClearable(true);
            mSubjectView.addTextChangedListener(new TextWatcher() {
                ReviewView.SubjectViewAction action = mReviewView.getSubjectViewAction();

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    action.beforeTextChanged(s, start, before, count);
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    action.beforeTextChanged(s, start, count, after);
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.toString().length() > 0) {
                        action.afterTextChanged(s);
                    }
                }
            });
        } else {
            mSubjectView.setFocusable(false);
            ((ClearableEditText) mSubjectView).makeClearable(false);
        }
    }

    void initRatingBarUi() {
        if (isEditable()) {
            mRatingBar.setIsIndicator(false);
            mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    mReviewView.getRatingBarAction().onRatingChanged(ratingBar, rating, fromUser);
                }
            });
        } else {
            mRatingBar.setIsIndicator(true);
        }
    }

    void initBannerButtonUi() {
        final ReviewView.BannerButtonAction action = mReviewView.getBannerButtonAction();
        mBannerButton.setText(action.getButtonTitle());
        mBannerButton.setTextColor(mSubjectView.getTextColors().getDefaultColor());
        mBannerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                action.onClick(v);
            }
        });
    }

    void initDataGridUi() {
        mGridView.setAdapter(getGridViewCellAdapter());
        mGridView.setColumnWidth(getGridCellWidth());
        mGridView.setNumColumns(getNumberColumns());

        final ReviewView.GridItemAction action = mReviewView.getGridItemAction();
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                action.onGridItemClick((GvDataList.GvData) parent.getItemAtPosition(position));
            }
        });

        mGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {
                action.onGridItemLongClick((GvDataList.GvData) parent.getItemAtPosition(position));
                return true;
            }
        });
    }

    boolean isEditable() {
        return mReviewView.isEditable();
    }

    ViewHolderAdapter getGridViewCellAdapter() {
        return new ViewHolderAdapter(getActivity(), mReviewView.getGridData(), getGridCellWidth(),
                getGridCellHeight());
    }

    int getGridCellWidth() {
        return mMaxGridCellWidth / mCellWidthDivider;
    }

    int getGridCellHeight() {
        return mMaxGridCellHeight / mCellHeightDivider;
    }

    int getNumberColumns() {
        return mCellWidthDivider;
    }

    void updateSubjectTextUi() {
        mSubjectView.setText(mReviewView.getSubjectViewAction().getSubject());
    }

    void updateRatingBarUi() {
        mRatingBar.setRating(mReviewView.getRatingBarAction().getRating());
    }

    void updateBannerButtonUi() {
    }

    void updateGridDataUi() {
        ((ViewHolderAdapter) mGridView.getAdapter()).notifyDataSetChanged();
    }

    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    void updateCover() {
        GvImageList.GvImage cover = mReviewView.getCover();
        if (cover.isValidForDisplay()) {
            BitmapDrawable bitmap = new BitmapDrawable(getResources(), cover.getBitmap());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mLayout.setBackground(bitmap);
            } else {
                mLayout.setBackgroundDrawable(bitmap);
            }
            mGridView.getBackground().setAlpha(mGridViewImageAlpha.getAlpha());

        } else {
            mLayout.setBackgroundColor(Color.TRANSPARENT);
            mGridView.getBackground().setAlpha(GridViewImageAlpha.OPAQUE.getAlpha());
        }
    }
}

