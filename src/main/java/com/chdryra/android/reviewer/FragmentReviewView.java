/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 January, 2015
 */

package com.chdryra.android.reviewer;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
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
public class FragmentReviewView extends Fragment {
    private static final int LAYOUT        = R.layout.fragment_view_review;
    private static final int LINEAR_LAYOUT = R.id.linearlayout;
    private static final int SUBJECT       = R.id.subject_edit_text;
    private static final int RATING        = R.id.rating_bar;
    private static final int BUTTON        = R.id.banner_button;
    private static final int GRID          = R.id.gridview_data;

    private LinearLayout mLinearLayout;
    private TextView     mSubjectView;
    private RatingBar    mRatingBar;
    private Button       mBannerButton;
    private GridView     mGridView;

    private ReviewView mReviewView;

    private int mMaxGridCellWidth;
    private int mMaxGridCellHeight;
    private int mCellWidthDivider  = 1;
    private int mCellHeightDivider = 1;

    private boolean mIsModified = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mReviewView = Administrator.get(getActivity()).unpackView(getActivity().getIntent());
        mReviewView.attachFragment(this);

        ReviewView.ViewReviewParams params = mReviewView.getParams();
        setGridCellDimension(params.cellWidth, params.cellHeight);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(LAYOUT, container, false);

        mLinearLayout = (LinearLayout) v.findViewById(LINEAR_LAYOUT);
        mSubjectView = (ClearableEditText) v.findViewById(SUBJECT);
        mRatingBar = (RatingBar) v.findViewById(RATING);
        mBannerButton = (Button) v.findViewById(BUTTON);
        mGridView = (GridView) v.findViewById(GRID);

        mGridView.setDrawSelectorOnTop(true);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        mMaxGridCellWidth = Math.min(displaymetrics.widthPixels, displaymetrics.heightPixels);
        //noinspection SuspiciousNameCombination
        mMaxGridCellHeight = mMaxGridCellWidth;

        initUi();
        updateUi();

        return mReviewView.modifyIfNeccesary(v, inflater, container, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        new AttachActionListenersTask().execute();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUi();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, android.view.MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        mReviewView.getMenuAction().inflateMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        return mReviewView.getMenuAction().onItemSelected(item) || super
                .onOptionsItemSelected(item);
    }

    public void updateUi() {
        updateSubjectUi();
        updateRatingBarUi();
        updateBannerButtonUi();
        updateGridDataUi();
        updateCover();
    }

    public String getSubject() {
        return mSubjectView.getText().toString().trim();
    }

    public void setSubject(String subject) {
        mSubjectView.setText(subject);
    }

    public float getRating() {
        return mRatingBar.getRating();
    }

    public void setRating(float rating) {
        mRatingBar.setRating(rating);
    }

    public void addView(View v) {
        if (!mIsModified) {
            mGridView.getLayoutParams().height = ActionBar.LayoutParams.WRAP_CONTENT;
            mIsModified = true;
        }

        mLinearLayout.addView(v);
    }

    public void setBannerNotClickable() {
        mBannerButton.setClickable(false);
    }

    public void updateCover() {
        mReviewView.updateCover();
    }

    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setCover(GvImageList.GvImage cover) {
        if (cover != null && cover.isValidForDisplay()) {
            BitmapDrawable bitmap = new BitmapDrawable(getResources(), cover.getBitmap());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mLinearLayout.setBackground(bitmap);
            } else {
                mLinearLayout.setBackgroundDrawable(bitmap);
            }
            ReviewView.ViewReviewParams params = mReviewView.getParams();
            mGridView.getBackground().setAlpha(params.gridAlpha.getAlpha());
        } else {
            removeCover();
        }
    }

    public void removeCover() {
        mLinearLayout.setBackgroundColor(Color.TRANSPARENT);
        mGridView.getBackground().setAlpha(ReviewView.GridViewImageAlpha.OPAQUE.getAlpha());
    }

    void initUi() {
        initSubjectUi();
        initRatingBarUi();
        initBannerButtonUi();
        initDataGridUi();
    }

    void initSubjectUi() {
        ReviewView.ViewReviewParams params = mReviewView.getParams();
        if (!params.subjectIsVisibile) {
            mSubjectView.setVisibility(View.GONE);
            return;
        }

        if (isEditable()) {
            mSubjectView.setFocusable(true);
            ((ClearableEditText) mSubjectView).makeClearable(true);
            mSubjectView.addTextChangedListener(new TextWatcher() {
                ReviewViewAction.SubjectAction action = mReviewView.getSubjectViewAction();

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    action.onTextChanged(s, start, before, count);
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
        ReviewView.ViewReviewParams params = mReviewView.getParams();
        if (!params.ratingIsVisibile) {
            mRatingBar.setVisibility(View.GONE);
            return;
        }

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
        ReviewView.ViewReviewParams params = mReviewView.getParams();
        if (!params.bannerButtonIsVisibile) {
            mBannerButton.setVisibility(View.GONE);
            return;
        }

        final ReviewViewAction.BannerButtonAction action = mReviewView.getBannerButtonAction();
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
        ReviewView.ViewReviewParams params = mReviewView.getParams();
        if (!params.gridIsVisibile) {
            mGridView.setVisibility(View.GONE);
            return;
        }

        mGridView.setAdapter(getGridViewCellAdapter());
        mGridView.setColumnWidth(getGridCellWidth());
        mGridView.setNumColumns(getNumberColumns());

        final ReviewViewAction.GridItemAction action = mReviewView.getGridItemAction();
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                action.onGridItemClick((GvDataList.GvData) parent.getItemAtPosition(position), v);
            }
        });

        mGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {
                action.onGridItemLongClick((GvDataList.GvData) parent.getItemAtPosition(position)
                        , v);
                return true;
            }
        });
    }

    boolean isEditable() {
        return mReviewView.isEditable();
    }

    ViewHolderAdapter getGridViewCellAdapter() {
        return FactoryGridCellAdapter.newAdapter(getActivity(), mReviewView.getGridViewData(),
                getGridCellWidth(), getGridCellHeight());
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

    void updateSubjectUi() {
        mSubjectView.setText(mReviewView.getSubjectViewAction().getSubject());
    }

    void updateRatingBarUi() {
        mRatingBar.setRating(mReviewView.getRatingBarAction().getRating());
    }

    void updateBannerButtonUi() {
    }

    void updateGridDataUi() {
        ((ViewHolderAdapter) mGridView.getAdapter()).notifyDataSetChanged();
        mReviewView.notifyDataSetChanged();
    }

    void updateGridData() {
        ((ViewHolderAdapter) mGridView.getAdapter()).setData(mReviewView.getGridViewData());
    }

    private void setGridCellDimension(ReviewView.CellDimension width,
            ReviewView.CellDimension height) {
        mCellWidthDivider = 1;
        mCellHeightDivider = 1;

        if (width == ReviewView.CellDimension.HALF) {
            mCellWidthDivider = 2;
        } else if (width == ReviewView.CellDimension.QUARTER) {
            mCellWidthDivider = 4;
        }

        if (height == ReviewView.CellDimension.HALF) {
            mCellHeightDivider = 2;
        } else if (height == ReviewView.CellDimension.QUARTER) {
            mCellHeightDivider = 4;
        }
    }

    //Have to do this hacky crap because FragmentManager cannot properly deal with child
    // fragments as executePendingTransactions not properly synchronised pre API 17.
    private class AttachActionListenersTask extends AsyncTask<Void, Void, Void> {
        protected Void doInBackground(Void... params) {
            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            mReviewView.attachActionListeners();
        }
    }
}

