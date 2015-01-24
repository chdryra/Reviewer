/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 24 January, 2015
 */

package com.chdryra.android.reviewer;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.RatingBar;

import com.chdryra.android.mygenerallibrary.ActivityResultCode;

/**
 * Created by: Rizwan Choudrey
 * On: 24/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewView {
    private FragmentReviewView mParent;

    private SubjectViewAction  mSubjectAction;
    private RatingBarAction    mRatingAction;
    private BannerButtonAction mButtonAction;
    private GridItemAction     mGridAction;
    private MenuAction         mMenuAction;

    private GvDataList          mData;
    private GvImageList.GvImage mCover;
    private boolean mIsEditable = false;

    public ReviewView(FragmentReviewView parent, GvDataList mGridData, GvImageList.GvImage cover,
            SubjectViewAction sva, RatingBarAction rba, BannerButtonAction bba,
            GridItemAction gia, MenuAction mia, boolean isEditable) {
        mParent = parent;

        mData = mGridData;
        mCover = cover;
        mIsEditable = isEditable;

        mSubjectAction = sva;
        mRatingAction = rba;
        mButtonAction = bba;
        mGridAction = gia;
        mMenuAction = mia;

        mSubjectAction.setReviewView(this);
        mRatingAction.setReviewView(this);
        mButtonAction.setReviewView(this);
        mGridAction.setReviewView(this);
        mMenuAction.setReviewView(this);
    }

    public FragmentReviewView getParentFragment() {
        return mParent;
    }

    public Activity getActivity() {
        return mParent.getActivity();
    }

    public GvDataList getGridData() {
        return mData;
    }

    public SubjectViewAction getSubjectViewAction() {
        return mSubjectAction;
    }

    public RatingBarAction getRatingBarAction() {
        return mRatingAction;
    }

    public BannerButtonAction getBannerButtonAction() {
        return mButtonAction;
    }

    public GridItemAction getGridItemAction() {
        return mGridAction;
    }

    public MenuAction getMenuItemAction() {
        return mMenuAction;
    }

    public boolean isEditable() {
        return mIsEditable;
    }

    public GvImageList.GvImage getCover() {
        return mCover;
    }

    public void updateUi() {
        mParent.updateUi();
    }

    protected void addFragmentListener(Fragment listener, String tag) {
        FragmentManager manager = getActivity().getFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(listener, tag);
        ft.commit();
    }

    public static class ReviewViewAction {
        private ControllerReview  mController;
        private GvDataList.GvType mDataType;
        private ReviewView        mReviewView;

        private ReviewViewAction(ControllerReview controller, GvDataList.GvType dataType) {
            mController = controller;
            mDataType = dataType;
        }

        public ReviewView getReviewView() {
            return mReviewView;
        }

        public void setReviewView(ReviewView reviewView) {
            mReviewView = reviewView;
        }

        public ControllerReview getController() {
            return mController;
        }

        public GvDataList.GvType getDataType() {
            return mDataType;
        }

        public Activity getActivity() {
            return mReviewView != null ? mReviewView.getActivity() : null;
        }
    }

    public static class SubjectViewAction extends ReviewViewAction {

        public SubjectViewAction(ControllerReview controller, GvDataList.GvType dataType) {
            super(controller, dataType);
        }

        public String getSubject() {
            return getController().getSubject();
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void afterTextChanged(Editable s) {
        }

        public void onClick() {
        }
    }

    public static class RatingBarAction extends ReviewViewAction {
        public RatingBarAction(ControllerReview controller, GvDataList.GvType dataType) {
            super(controller, dataType);
        }

        public float getRating() {
            return getController().getRating();
        }

        public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        }
    }

    public static class BannerButtonAction extends ReviewViewAction {
        public BannerButtonAction(ControllerReview controller, GvDataList.GvType dataType) {
            super(controller, dataType);
        }

        public String getButtonTitle() {
            return null;
        }

        public void onClick(View v) {
        }
    }

    public static class GridItemAction extends ReviewViewAction {
        public GridItemAction(ControllerReview controller, GvDataList.GvType dataType) {
            super(controller, dataType);
        }

        public void onGridItemClick(GvDataList.GvData item) {
        }

        public void onGridItemLongClick(GvDataList.GvData item) {
            onGridItemClick(item);
        }
    }

    public static class MenuAction extends ReviewViewAction {
        public static final int                MENU_UP_ID = android.R.id.home;
        public static final ActivityResultCode RESULT_UP  = ActivityResultCode.UP;

        private final int     mMenuId;
        private final boolean mDisplayHomeAsUp;

        public MenuAction(ControllerReview controller,
                GvDataList.GvType dataType, int menuId) {
            this(controller, dataType, menuId, true);
        }

        public MenuAction(ControllerReview controller,
                GvDataList.GvType dataType, int menuId, boolean displayHomeAsUp) {
            super(controller, dataType);
            mMenuId = menuId;
            mDisplayHomeAsUp = displayHomeAsUp;
        }

        @Override
        public void setReviewView(ReviewView reviewView) {
            super.setReviewView(reviewView);
            if (getActivity().getActionBar() != null) {
                getActivity().getActionBar().setDisplayHomeAsUpEnabled(mDisplayHomeAsUp);
            }
        }

        public void inflateMenu(Menu menu, MenuInflater inflater) {
            inflater.inflate(mMenuId, menu);
        }

        public boolean onItemSelected(android.view.MenuItem item) {
            int itemId = item.getItemId();
            if (itemId == MENU_UP_ID) {
                doUpSelected();
                return true;
            } else {
                return false;
            }
        }

        protected void sendResult(ActivityResultCode result) {
            getActivity().setResult(result.get(), null);
        }

        protected void onUpSelected() {
            if (NavUtils.getParentActivityName(getActivity()) != null) {
                Intent i = NavUtils.getParentActivityIntent(getActivity());
                if (getController() != null) {
                    Administrator.get(getActivity()).pack(getController(), i);
                }
                NavUtils.navigateUpTo(getActivity(), i);
            }
        }

        private void doUpSelected() {
            onUpSelected();
            sendResult(RESULT_UP);
            getActivity().finish();
        }
    }
}
