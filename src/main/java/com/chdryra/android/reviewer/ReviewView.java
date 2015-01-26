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
import android.database.DataSetObserver;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

    public MenuAction getMenuAction() {
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

    public void addListener(Fragment listener, String tag) {
        FragmentManager manager = getActivity().getFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(listener, tag);
        ft.commit();
    }

    public Fragment getListener(String tag) {
        FragmentManager manager = getActivity().getFragmentManager();
        Fragment f = manager.findFragmentByTag(tag);
        return f;
    }

    public String getSubject() {
        return mParent.getSubject();
    }

    public void setSubject(String subject) {
        mParent.setSubject(subject);
    }

    public float getRating() {
        return mParent.getRating();
    }

    public void setRating(float rating) {
        mParent.setRating(rating);
    }

    public void registerGridDataObserver(DataSetObserver observer) {
        mParent.registerGridDataObserver(observer);
    }

    public void updateGridDataUi(GvDataList data) {
        mParent.updateGridDataUi(data);
    }

    public abstract static class ReviewViewAction {
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
            onSetReviewView();
        }

        public void onSetReviewView() {

        }

        ;

        public ControllerReview getController() {
            return mController;
        }

        public GvDataList.GvType getDataType() {
            return mDataType;
        }

        public Activity getActivity() {
            return mReviewView != null ? mReviewView.getActivity() : null;
        }

        protected Fragment getNewListener() {
            return null;
        }

        protected Fragment getListener(String tag) {
            final ReviewView view = getReviewView();

            Fragment listener = view.getListener(tag);
            if (listener == null) listener = getNewListener();
            if (listener != null) view.addListener(listener, tag);

            return listener;
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

        private SparseArray<MenuActionItemInfo> mActionItems;

        public MenuAction(ControllerReview controller,
                GvDataList.GvType dataType, int menuId) {
            this(controller, dataType, menuId, true);
        }

        public MenuAction(ControllerReview controller,
                GvDataList.GvType dataType, int menuId, boolean displayHomeAsUp) {
            super(controller, dataType);
            mMenuId = menuId;
            mDisplayHomeAsUp = displayHomeAsUp;
            mActionItems = new SparseArray<>();
            if (mDisplayHomeAsUp) addMenuActionItem(getUpActionItem(), MENU_UP_ID, true);
        }

        @Override
        public void onSetReviewView() {
            if (getActivity().getActionBar() != null) {
                getActivity().getActionBar().setDisplayHomeAsUpEnabled(mDisplayHomeAsUp);
            }
        }

        public void inflateMenu(Menu menu, MenuInflater inflater) {
            addMenuItems();
            inflater.inflate(mMenuId, menu);
        }

        public void addMenuActionItem(MenuActionItem item, int itemId,
                boolean finishActivityOnAction) {
            mActionItems.put(itemId, new MenuActionItemInfo(item, finishActivityOnAction));
        }

        public boolean onItemSelected(android.view.MenuItem item) {
            MenuActionItemInfo actionItem = mActionItems.get(item.getItemId());
            if (actionItem != null) {
                actionItem.mItem.doAction(item);
                if (actionItem.mFinishActivityOnAction) getActivity().finish();
                return true;
            }

            return false;
        }

        protected void addMenuItems() {
        }

        protected void sendResult(ActivityResultCode result) {
            if (result != null) getActivity().setResult(result.get(), null);
        }

        private void onUpSelected() {
            if (NavUtils.getParentActivityName(getActivity()) != null) {
                Intent i = NavUtils.getParentActivityIntent(getActivity());
                if (getController() != null) {
                    Administrator.get(getActivity()).pack(getController(), i);
                }
                NavUtils.navigateUpTo(getActivity(), i);
            }
        }

        private MenuActionItem getUpActionItem() {
            return new MenuActionItem() {
                @Override
                public void doAction(MenuItem item) {
                    onUpSelected();
                    sendResult(RESULT_UP);
                }
            };
        }

        public abstract class MenuActionItem {
            public abstract void doAction(MenuItem item);
        }

        private class MenuActionItemInfo {
            private boolean        mFinishActivityOnAction;
            private MenuActionItem mItem;

            private MenuActionItemInfo(MenuActionItem item, boolean finishActivityOnAction) {
                mItem = item;
                mFinishActivityOnAction = finishActivityOnAction;
            }
        }
    }
}
