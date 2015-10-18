/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 January, 2015
 */

package com.chdryra.android.reviewer.View.ActivitiesFragments;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.chdryra.android.myandroidwidgets.ClearableEditText;
import com.chdryra.android.mygenerallibrary.ViewHolderAdapter;
import com.chdryra.android.reviewer.ApplicationSingletons.ReviewViewPacker;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;
import com.chdryra.android.reviewer.View.Screens.FactoryGridCellAdapter;
import com.chdryra.android.reviewer.View.Screens.GridDataObservable;
import com.chdryra.android.reviewer.View.Screens.ReviewView;
import com.chdryra.android.reviewer.View.Screens.ReviewViewAction;
import com.chdryra.android.reviewer.View.Screens.ReviewViewParams;

/**
 * Created by: Rizwan Choudrey
 * On: 23/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
@SuppressWarnings("EmptyMethod")
public class FragmentReviewView extends Fragment implements GridDataObservable.GridDataObserver {
    private static final int LAYOUT = R.layout.fragment_view_review;
    private static final int LINEAR_LAYOUT = R.id.linearlayout;
    private static final int SUBJECT = R.id.subject_edit_text;
    private static final int RATING = R.id.review_rating;
    private static final int BUTTON = R.id.banner_button;
    private static final int GRID = R.id.gridview_data;

    private LinearLayout mLinearLayout;
    private TextView mSubjectView;
    private RatingBar mRatingBar;
    private Button mBannerButton;
    private GridView mGridView;

    private ReviewView mReviewView;

    private int mMaxGridCellWidth;
    private int mMaxGridCellHeight;
    private int mCellWidthDivider = 1;
    private int mCellHeightDivider = 1;

    private boolean mIsModified = false;

    //public methods
    public String getSubject() {
        return mSubjectView.getText().toString().trim();
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
            ReviewViewParams params = mReviewView.getParams();
            mGridView.getBackground().setAlpha(params.getGridViewParams().getGridAlpha());
        } else {
            removeCover();
        }
    }

    //private methods
    private boolean isEditable() {
        return mReviewView.isEditable();
    }

    private int getGridCellWidth() {
        return mMaxGridCellWidth / mCellWidthDivider;
    }

    private int getGridCellHeight() {
        return mMaxGridCellHeight / mCellHeightDivider;
    }

    private int getNumberColumns() {
        return mCellWidthDivider;
    }

    private void updateUi() {
        updateSubjectUi();
        updateRatingBarUi();
        updateBannerButtonUi();
        updateGridDataUi();
        updateCover();
    }

    private void updateCover() {
        mReviewView.updateCover();
    }

    private void removeCover() {
        mLinearLayout.setBackgroundColor(Color.TRANSPARENT);
        mGridView.getBackground().setAlpha(ReviewViewParams.GridViewAlpha.OPAQUE.getAlpha());
    }

    private void initUi() {
        initSubjectUi();
        initRatingBarUi();
        initBannerButtonUi();
        initGridDataUi();
    }

    private void initSubjectUi() {
        ReviewViewParams params = mReviewView.getParams();
        if (!params.isSubjectVisible()) {
            mSubjectView.setVisibility(View.GONE);
            return;
        }

        if (isEditable()) {
            mSubjectView.setFocusable(true);
            ((ClearableEditText) mSubjectView).makeClearable(true);
            mSubjectView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                final ReviewViewAction.SubjectAction action
                        = mReviewView.getActions().getSubjectAction();

                //Overridden
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_DONE ||
                            event.getAction() == KeyEvent.ACTION_DOWN &&
                                    event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {

                        action.onEditorDone(v.getText());
                        return true;
                    }
                    return false;
                }
            });
        } else {
            mSubjectView.setFocusable(false);
            ((ClearableEditText) mSubjectView).makeClearable(false);
        }

        updateSubjectUi();
    }

    private void initRatingBarUi() {
        ReviewViewParams params = mReviewView.getParams();
        if (!params.isRatingVisible()) {
            mRatingBar.setVisibility(View.GONE);
            return;
        }

        final ReviewViewAction.RatingBarAction ratingBarAction
                = mReviewView.getActions().getRatingBarAction();
        if (isEditable()) {
            mRatingBar.setIsIndicator(false);
            mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                //Overridden
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    ratingBarAction.onRatingChanged(ratingBar, rating, fromUser);
                }
            });
        } else {
            mRatingBar.setIsIndicator(true);
        }

        mRatingBar.setOnTouchListener(new View.OnTouchListener() {
            //Overridden
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ratingBarAction.onClick(v);
                return false;
            }
        });

        updateRatingBarUi();
    }

    private void initBannerButtonUi() {
        ReviewViewParams params = mReviewView.getParams();
        if (!params.isBannerButtonVisible()) {
            mBannerButton.setVisibility(View.GONE);
            return;
        }

        final ReviewViewAction.BannerButtonAction action
                = mReviewView.getActions().getBannerButtonAction();
        mBannerButton.setText(action.getButtonTitle());
        mBannerButton.setTextColor(mSubjectView.getTextColors().getDefaultColor());
        mBannerButton.setOnClickListener(new View.OnClickListener() {
            //Overridden
            @Override
            public void onClick(View v) {
                action.onClick(v);
            }
        });
        mBannerButton.setOnLongClickListener(new View.OnLongClickListener() {
            //Overridden
            @Override
            public boolean onLongClick(View v) {
                return action.onLongClick(v);
            }
        });

        updateBannerButtonUi();
    }

    private void initGridDataUi() {
        ViewHolderAdapter adapter = FactoryGridCellAdapter.newAdapter(getActivity(),
                mReviewView.getGridViewData(), getGridCellWidth(), getGridCellHeight());
        mGridView.setAdapter(adapter);
        mGridView.setColumnWidth(getGridCellWidth());
        mGridView.setNumColumns(getNumberColumns());

        final ReviewViewAction.GridItemAction action = mReviewView.getActions().getGridItemAction();
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //Overridden
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                GvData item = (GvData) parent.getItemAtPosition(position);
                action.onGridItemClick(item, position, v);
            }
        });

        mGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            //Overridden
            public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {
                GvData item = (GvData) parent.getItemAtPosition(position);
                action.onGridItemLongClick(item, position, v);
                return true;
            }
        });
    }

    private void updateSubjectUi() {
        mSubjectView.setText(mReviewView.getSubject());
    }

    private void updateRatingBarUi() {
        mRatingBar.setRating(mReviewView.getRating());
    }

    private void updateBannerButtonUi() {
    }

    private void updateGridDataUi() {
        ((ViewHolderAdapter) mGridView.getAdapter()).setData(mReviewView.getGridViewData());
    }

    private void setGridCellDimension(ReviewViewParams.CellDimension width,
                                      ReviewViewParams.CellDimension height) {
        mCellWidthDivider = 1;
        mCellHeightDivider = 1;

        if (width == ReviewViewParams.CellDimension.HALF) {
            mCellWidthDivider = 2;
        } else if (width == ReviewViewParams.CellDimension.QUARTER) {
            mCellWidthDivider = 4;
        }

        if (height == ReviewViewParams.CellDimension.HALF) {
            mCellHeightDivider = 2;
        } else if (height == ReviewViewParams.CellDimension.QUARTER) {
            mCellHeightDivider = 4;
        }
    }

    //Overridden
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mReviewView = ReviewViewPacker.unpackView(getActivity(), getActivity().getIntent());
        mReviewView.attachFragment(this);

        ReviewViewParams params = mReviewView.getParams();
        setGridCellDimension(params.getGridViewParams().getCellWidth(),
                params.getGridViewParams().getCellHeight());
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
        mReviewView.getActions().getMenuAction().inflateMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        return mReviewView.getActions().getMenuAction().onItemSelected(item)
                || super.onOptionsItemSelected(item);
    }

    @Override
    public void onGridDataChanged() {
        updateUi();
    }

    //Have to do this hacky crap because FragmentManager cannot properly deal with child
    // fragments as executePendingTransactions not properly synchronised pre API 17.
    private class AttachActionListenersTask extends AsyncTask<Void, Void, Void> {
        //Overridden
        protected Void doInBackground(Void... params) {
            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            mReviewView.attachActionListeners();
        }
    }
}

