/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.chdryra.android.myandroidwidgets.ClearableEditText;
import com.chdryra.android.mygenerallibrary.FragmentDeleteDone;
import com.chdryra.android.mygenerallibrary.GridViewCellAdapter;
import com.chdryra.android.reviewer.GVImageList.GVImage;
import com.chdryra.android.reviewer.GVReviewDataList.GVType;

/**
 * UI Fragment: base class for all the review data interaction UIs. Knows how to present GVData
 * in a GVReviewDataLists as grid cells in a GridView.
 * <p/>
 * <p>
 * Constructed out of 5 UI components that are populated by data from a
 * ControllerReviewTreeEditable:
 * <ul>
 * <li>EditText/TextView: displaying/editing review subject.</li>
 * <li>RatingBar: displaying/editing the review rating.</li>
 * <li>Banner button: button that can be allocated a function.</li>
 * <li>GridView: for displaying some review data.</li>
 * <li>ActionBar: Delete, Done and Up that delete or confirm editing of review data,
 * or cancel back to parent activity.</li>
 * </ul>
 * </p>
 * <p/>
 * <p>
 * A bunch of behaviour can be set or defined by calling the appropriate setters on the
 * overridden <code>onCreate(.)</code> method or by overriding the appropriate protected
 * methods that govern actions to perform on user interaction.
 * </p>
 *
 * @param <T>: type of GVData to display.
 */

//TODO abstract FragmentReviewGrid into data-editing and data-viewing versions.

@SuppressWarnings("EmptyMethod")
public abstract class FragmentReviewGrid<T extends GVReviewDataList> extends FragmentDeleteDone {
    private ControllerReviewTreeEditable mController;
    private LinearLayout                 mLayout;
    private TextView                     mSubjectView;
    private RatingBar                    mTotalRatingBar;
    private Button                       mBannerButton;
    private GridView                     mGridView;
    private int                          mMaxGridCellWidth;
    private int                          mMaxGridCellHeight;
    private int     mCellWidthDivider  = 1;
    private int     mCellHeightDivider = 1;
    private boolean mIsEditable        = false;
    private String mBannerButtonText;
    private T      mGridData;
    private boolean            mReviewInProgress   = false;
    private GridViewImageAlpha mGridViewImageAlpha = GridViewImageAlpha.MEDIUM;

    /**
     * Settings for GridView transpareny with respect to background image.
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
     * Settings for grid cell dimensions with respect to screen width size.
     */
    public enum CellDimension {
        FULL, HALF, QUARTER
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        updateUI();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mController = (ControllerReviewTreeEditable) Administrator.get(getActivity()).unpack
                (getActivity().getIntent().getExtras());

        if (mController == null) {
            setController(Administrator.get(getActivity()).createNewReviewInProgress());
            mReviewInProgress = true;
        }

        setGridCellDimension(CellDimension.HALF, CellDimension.QUARTER);
        setBannerButtonText(getResources().getString(R.string.button_add));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_review_grid, container, false);

        mLayout = (LinearLayout) v.findViewById(R.id.review_grid_linearlayout);
        mSubjectView = (ClearableEditText) v.findViewById(R.id.review_subject_edit_text);
        mTotalRatingBar = (RatingBar) v.findViewById(R.id.review_rating_bar);
        mBannerButton = (Button) v.findViewById(R.id.banner_button);
        mGridView = (GridView) v.findViewById(R.id.gridview_data);
        mGridView.setDrawSelectorOnTop(true);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        mMaxGridCellWidth = Math.min(displaymetrics.widthPixels, displaymetrics.heightPixels);
        //noinspection SuspiciousNameCombination
        mMaxGridCellHeight = mMaxGridCellWidth;

        initUI();
        updateUI();

        return v;
    }

    @Override
    protected boolean hasDataToDelete() {
        return mGridData.size() > 0;
    }

    @Override
    protected void setDeleteWhatTitle(String deleteWhat) {
        super.setDeleteWhatTitle("all " + deleteWhat);
    }

    @Override
    protected void onDeleteSelected() {
        mGridData.removeAll();
    }

    @Override
    protected void onDoneSelected() {
        getEditableController().setData(mGridData);
//
//        if (mOnDoneActivity != null) {
//            Intent i = new Intent(getActivity(), ActivityReviewBuild.class);
//            Administrator.get(getActivity()).pack(getController(), i);
//            startActivity(i);
//        }
    }

    @Override
    protected void onUpSelected() {
        if (NavUtils.getParentActivityName(getActivity()) != null) {
            Intent i = NavUtils.getParentActivityIntent(getActivity());
            if (!mReviewInProgress && getController() != null) {
                Administrator.get(getActivity()).pack(getController(), i);
            }
            NavUtils.navigateUpTo(getActivity(), i);
        }
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

    void initUI() {
        initSubjectUI();
        initRatingBarUI();
        initBannerButtonUI();
        initDataGridUI();
    }

    void initSubjectUI() {
        if (isEditable()) {
            getSubjectView().setFocusable(true);
            ((ClearableEditText) getSubjectView()).makeClearable(true);
            getSubjectView().addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.toString().length() > 0) {
                        getEditableController().setSubject(s.toString());
                    }
                }
            });
        } else {
            getSubjectView().setFocusable(false);
            ((ClearableEditText) getSubjectView()).makeClearable(false);
        }
    }

    void initRatingBarUI() {
        if (isEditable()) {
            getTotalRatingBar().setIsIndicator(false);
            getTotalRatingBar().setOnRatingBarChangeListener(new RatingBar
                    .OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    getEditableController().setRating(rating);
                }
            });
        } else {
            getTotalRatingBar().setIsIndicator(true);
        }
    }

    void initBannerButtonUI() {
        getBannerButton().setText(getBannerButtonText());
        getBannerButton().setTextColor(getSubjectView().getTextColors().getDefaultColor());
        getBannerButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBannerButtonClick();
            }
        });
    }

    void initDataGridUI() {
        getGridView().setAdapter(getGridViewCellAdapter());
        getGridView().setColumnWidth(getGridCellWidth());
        getGridView().setNumColumns(getNumberColumns());
        getGridView().setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                onGridItemClick(parent, v, position, id);
            }
        });
        getGridView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {
                onGridItemLongClick(parent, v, position, id);
                return true;
            }
        });
    }

    boolean isEditable() {
        return mIsEditable;
    }

    Button getBannerButton() {
        return mBannerButton;
    }

    String getBannerButtonText() {
        return mBannerButtonText;
    }

    void setBannerButtonText(String buttonText) {
        mBannerButtonText = buttonText;
    }

    void onBannerButtonClick() {

    }

    GridViewCellAdapter getGridViewCellAdapter() {
        return new GridViewCellAdapter(getActivity(), mGridData, getGridCellWidth(),
                getGridCellHeight());
    }

    int getGridCellWidth() {
        return mMaxGridCellWidth / mCellWidthDivider;
    }

    int getNumberColumns() {
        return mCellWidthDivider;
    }

    void onGridItemClick(AdapterView<?> parent, View v, int position, long id) {

    }

    void onGridItemLongClick(AdapterView<?> parent, View v, int position, long id) {
        getGridView().performItemClick(v, position, id);
    }

    int getGridCellHeight() {
        return mMaxGridCellHeight / mCellHeightDivider;
    }

    void updateUI() {
        updateSubjectTextUI();
        updateRatingBarUI();
        updateBannerButtonUI();
        updateGridDataUI();
        updateCover();
    }

    void updateSubjectTextUI() {
        if (getController() != null) {
            getSubjectView().setText(getController().getSubject());
        }
    }

    void updateRatingBarUI() {
        if (getController() != null) {
            getTotalRatingBar().setRating(getController().getRating());
        }
    }

    void updateBannerButtonUI() {

    }

    void updateGridDataUI() {
        ((GridViewCellAdapter) getGridView().getAdapter()).notifyDataSetChanged();
    }

    void updateCover() {
        if (getController() != null) {
            updateCover((GVImageList) getController().getData(GVType.IMAGES));
        }
    }

    ControllerReviewEditable getEditableController() {
        return mController;
    }

    ControllerReview getController() {
        return mController;
    }

    void setController(ControllerReviewTreeEditable controller) {
        mController = controller;
    }

    ControllerReviewNode getNodeController() {
        return mController.getReviewNode();
    }

    TextView getSubjectView() {
        return mSubjectView;
    }

    RatingBar getTotalRatingBar() {
        return mTotalRatingBar;
    }

    GridView getGridView() {
        return mGridView;
    }

    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    void updateCover(GVImageList images) {
        if (images.getCovers().size() > 0) {
            GVImage cover = images.getRandomCover();
            BitmapDrawable bitmap = new BitmapDrawable(getResources(), cover.getBitmap());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                getLayout().setBackground(bitmap);
            } else {
                getLayout().setBackgroundDrawable(bitmap);
            }
            getGridView().getBackground().setAlpha(mGridViewImageAlpha.getAlpha());

        } else {
            getLayout().setBackgroundColor(Color.TRANSPARENT);
            getGridView().getBackground().setAlpha(GridViewImageAlpha.OPAQUE.getAlpha());
        }
    }

    LinearLayout getLayout() {
        return mLayout;
    }

    void setIsEditable(boolean isEditable) {
        mIsEditable = isEditable;
    }

    void setGridViewData(T gridData) {
        mGridData = gridData;
    }

    void setTransparentGridCellBackground() {
        mGridViewImageAlpha = GridViewImageAlpha.TRANSPARENT;
    }

    String getSubjectText() {
        return getSubjectView().getText().toString();
    }

    T getGridData() {
        return mGridData;
    }
}
