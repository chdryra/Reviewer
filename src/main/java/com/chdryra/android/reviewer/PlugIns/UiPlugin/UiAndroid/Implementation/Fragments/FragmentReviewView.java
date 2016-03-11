/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Fragments;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
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

import com.chdryra.android.mygenerallibrary.ClearableEditText;
import com.chdryra.android.mygenerallibrary.ViewHolderAdapter;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Activities
        .ActivityReviewView;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.GridDataObservable;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryGridCellAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .ReviewViewActions;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewParams;
import com.chdryra.android.reviewer.R;

/**
 * Created by: Rizwan Choudrey
 * On: 23/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
@SuppressWarnings("EmptyMethod")
public class FragmentReviewView extends Fragment implements GridDataObservable.GridDataObserver {
    private static final String TAG = "FragmentReviewView";

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

    private ReviewView<?> mReviewView;
    private ReviewViewActions mActions;
    private ReviewViewParams mParams;

    private int mMaxGridCellWidth;
    private int mMaxGridCellHeight;
    private int mCellWidthDivider = 1;
    private int mCellHeightDivider = 1;

    private boolean mIsModified = false;
    private boolean mIsAttached = false;

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

    public ReviewView<?> getReviewView() {
        return mReviewView;
    }

    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setCover(@Nullable DataImage cover) {
        if (cover != null && cover.getBitmap() != null) {
            BitmapDrawable bitmap = new BitmapDrawable(getResources(), cover.getBitmap());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mLinearLayout.setBackground(bitmap);
            } else {
                mLinearLayout.setBackgroundDrawable(bitmap);
            }
            mGridView.getBackground().setAlpha(mParams.getGridViewParams().getGridAlpha());
        } else {
            removeCover();
        }
    }

    //public methods
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        ActivityReviewView activity;
        try {
            activity = (ActivityReviewView) getActivity();
        } catch (ClassCastException e) {
            Log.e(TAG, "Activity must be an ActivityReviewView");
            throw new RuntimeException("Activity must be an ActivityReviewView", e);
        }

        if (mReviewView == null) mReviewView = activity.getReviewView();
        if (mReviewView == null) {
            throw new IllegalStateException("ReviewView cannot be null!");
        }

        mActions = mReviewView.getActions();
        mParams = mReviewView.getParams();

        setGridCellDimension(mParams.getGridViewParams().getCellWidth(),
                mParams.getGridViewParams().getCellHeight());

        View v = inflater.inflate(LAYOUT, container, false);

        mLinearLayout = (LinearLayout) v.findViewById(LINEAR_LAYOUT);
        mSubjectView = (ClearableEditText) v.findViewById(SUBJECT);
        mRatingBar = (RatingBar) v.findViewById(RATING);
        mBannerButton = (Button) v.findViewById(BUTTON);
        mGridView = (GridView) v.findViewById(GRID);
        mGridView.setDrawSelectorOnTop(true);

        initGridCellDimensions();
        initUi();

        attachToReviewViewIfNecessary();

        return mReviewView.modifyIfNeccessary(v, inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        attachToReviewViewIfNecessary();
        updateUi();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, android.view.MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        mActions.getMenuAction().inflateMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        return mActions.getMenuAction().onItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    public void onGridDataChanged() {
        updateUi();
    }

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

    private void attachToReviewViewIfNecessary() {
        if(!mIsAttached) mReviewView.attachFragment(this);
        mIsAttached = true;
    }

    private void detachFromReviewViewIfNecessary() {
        if(mIsAttached) mReviewView.detachFragment(this);
        mIsAttached = false;
    }

    private void initGridCellDimensions() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        mMaxGridCellWidth = Math.min(displaymetrics.widthPixels, displaymetrics.heightPixels);
        //noinspection SuspiciousNameCombination
        mMaxGridCellHeight = mMaxGridCellWidth;
    }

    private void initUi() {
        initSubjectUi();
        initRatingBarUi();
        initBannerButtonUi();
        initGridDataUi();
    }

    private void initSubjectUi() {
        if (!mReviewView.getParams().isSubjectVisible()) {
            mSubjectView.setVisibility(View.GONE);
            return;
        }

        mSubjectView.setFocusable(isEditable());
        ((ClearableEditText) mSubjectView).makeClearable(isEditable());
        if (isEditable()) {
            mSubjectView.setOnEditorActionListener(newSubjectActionListener());
        }

        updateSubjectUi();
    }

    @NonNull
    private TextView.OnEditorActionListener newSubjectActionListener() {
        return new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE ||
                        event.getAction() == KeyEvent.ACTION_DOWN &&
                                event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    mActions.getSubjectAction().onKeyboardDone(v.getText());
                    return true;
                }
                return false;
            }
        };
    }

    private void initRatingBarUi() {
        if (!mParams.isRatingVisible()) {
            mRatingBar.setVisibility(View.GONE);
            return;
        }

        mRatingBar.setIsIndicator(!isEditable());
        mRatingBar.setOnTouchListener(newRatingBarTouchListener());
        if (isEditable()) {
            mRatingBar.setOnRatingBarChangeListener(newRatingBarChangeListener());
        }

        updateRatingBarUi();
    }

    @NonNull
    private View.OnTouchListener newRatingBarTouchListener() {
        return new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mActions.getRatingBarAction().onClick(v);
                return false;
            }
        };
    }

    @NonNull
    private RatingBar.OnRatingBarChangeListener newRatingBarChangeListener() {
        return new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                mActions.getRatingBarAction().onRatingChanged(ratingBar, rating, fromUser);
            }
        };
    }

    private void initBannerButtonUi() {
        if (!mParams.isBannerButtonVisible()) {
            mBannerButton.setVisibility(View.GONE);
            return;
        }

        mBannerButton.setText(mActions.getBannerButtonAction().getButtonTitle());
        mBannerButton.setTextColor(mSubjectView.getTextColors().getDefaultColor());
        mBannerButton.setOnClickListener(newBannerButtonClickListener());
        mBannerButton.setOnLongClickListener(newBannerButtonLongClickListener());

        updateBannerButtonUi();
    }

    @NonNull
    private View.OnLongClickListener newBannerButtonLongClickListener() {
        return new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return mActions.getBannerButtonAction().onLongClick(v);
            }
        };
    }

    @NonNull
    private View.OnClickListener newBannerButtonClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActions.getBannerButtonAction().onClick(v);
            }
        };
    }

    private void initGridDataUi() {
        ViewHolderAdapter adapter = FactoryGridCellAdapter.newAdapter(getActivity(),
                mReviewView.getGridViewData(), getGridCellWidth(), getGridCellHeight());
        mGridView.setAdapter(adapter);
        mGridView.setColumnWidth(getGridCellWidth());
        mGridView.setNumColumns(getNumberColumns());
        mGridView.setOnItemClickListener(newGridItemClickListener());
        mGridView.setOnItemLongClickListener(newGridItemLongClickListener());
    }

    @NonNull
    private AdapterView.OnItemLongClickListener newGridItemLongClickListener() {
        return new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {
                GvData item = (GvData) parent.getItemAtPosition(position);
                //TODO make type safe
                mActions.getGridItemAction().onGridItemLongClick(item, position, v);
                return true;
            }
        };
    }

    @NonNull
    private AdapterView.OnItemClickListener newGridItemClickListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                GvData item = (GvData) parent.getItemAtPosition(position);
                //TODO make type safe
                mActions.getGridItemAction().onGridItemClick(item, position, v);
            }
        };
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
        mCellWidthDivider = width.getDivider();
        mCellHeightDivider = height.getDivider();
    }
}

