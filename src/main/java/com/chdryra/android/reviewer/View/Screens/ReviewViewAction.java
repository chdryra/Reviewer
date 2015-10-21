/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 27 January, 2015
 */

package com.chdryra.android.reviewer.View.Screens;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RatingBar;

import com.chdryra.android.mygenerallibrary.ActivityResultCode;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewAdapter;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;

import java.util.HashMap;

/**
 * Created by: Rizwan Choudrey
 * On: 27/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewViewAction {
    private final HashMap<String, Fragment> mListeners;
    private ReviewView mReviewView;

    //Constructors
    public ReviewViewAction() {
        mListeners = new HashMap<>();
    }

    //public methods
    public ReviewView getReviewView() {
        return mReviewView;
    }

    public ReviewViewAdapter getAdapter() {
        return mReviewView.getAdapter();
    }

    public Activity getActivity() {
        return mReviewView != null ? mReviewView.getActivity() : null;
    }

    public void attachReviewView(ReviewView reviewView) {
        if (mReviewView != null) onUnattachReviewView();
        mReviewView = reviewView;
        onAttachReviewView();
    }

    public void onUnattachReviewView() {

    }

    public void onAttachReviewView() {

    }

    //protected methods
    protected GvDataList getGridData() {
        return getReviewView().getGridData();
    }

    //Classes
    @SuppressWarnings("EmptyMethod")
    public static class SubjectAction extends ReviewViewAction {
        //public methods
        public String getSubject() {
            return getAdapter().getSubject();
        }

        public void onEditorDone(CharSequence s) {

        }
    }

    public static class RatingBarAction extends ReviewViewAction {
        //public methods
        public float getRating() {
            return getAdapter().getRating();
        }

        public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

        }

        public void onClick(View v) {

        }
    }

    public static class BannerButtonAction extends ReviewViewAction {
        private String mTitle;

        //Constructors
        public BannerButtonAction() {
        }

        public BannerButtonAction(String title) {
            mTitle = title;
        }

        //Static methods
        public static BannerButtonAction newDisplayButton(final String title) {
            return new BannerButtonAction(title) {
            };
        }

        //public methods
        public String getButtonTitle() {
            return mTitle;
        }

        public void onClick(View v) {
        }

        public boolean onLongClick(View v) {
            onClick(v);
            return true;
        }
    }

    public static class GridItemAction extends ReviewViewAction {

        public void onGridItemClick(GvData item, int position, View v) {
        }

        public void onGridItemLongClick(GvData item, int position, View v) {
            onGridItemClick(item, position, v);
        }
    }

    public static class MenuAction extends ReviewViewAction {
        public static final int MENU_UP_ID = android.R.id.home;
        public static final ActivityResultCode RESULT_UP = ActivityResultCode.UP;
        private final String mTitle;
        private final SparseArray<MenuActionItemInfo> mActionItems;
        private int mMenuId = -1;
        private boolean mDisplayHomeAsUp = true;

        public interface MenuActionItem {
            //abstract methods
            void doAction(Context context, MenuItem item);
        }

        //Constructors
        public MenuAction() {
            this(-1, null, true);
        }

        public MenuAction(int menuId, String title, boolean displayHomeAsUp) {
            mMenuId = menuId;
            mTitle = title;
            mDisplayHomeAsUp = displayHomeAsUp;
            mActionItems = new SparseArray<>();
            if (mDisplayHomeAsUp) bindMenuActionItem(getUpActionItem(), MENU_UP_ID, true);
        }

        public MenuAction(String title) {
            this(-1, title, true);
        }

        public boolean hasOptionsMenu() {
            return mMenuId != -1;
        }

        public void inflateMenu(Menu menu, MenuInflater inflater) {
            if (hasOptionsMenu()) inflater.inflate(mMenuId, menu);
        }

        public void bindMenuActionItem(MenuActionItem item, int itemId, boolean finishActivity) {
            mActionItems.put(itemId, new MenuActionItemInfo(item, finishActivity));
        }

        public boolean onItemSelected(android.view.MenuItem item) {
            int itemId = item.getItemId();
            MenuActionItemInfo actionItem = mActionItems.get(itemId);
            if (actionItem != null) {
                actionItem.mItem.doAction(getActivity(), item);
                if (actionItem.mFinishActivity) getActivity().finish();
                return true;
            }

            return false;
        }

        protected void addMenuItems() {
        }

        protected void sendResult(ActivityResultCode result) {
            if (result != null) getActivity().setResult(result.get(), null);
        }

        protected void doUpSelected() {
            if (NavUtils.getParentActivityName(getActivity()) != null) {
                Intent i = NavUtils.getParentActivityIntent(getActivity());
                NavUtils.navigateUpTo(getActivity(), i);
            }
        }

        //private methods
        private MenuActionItem getUpActionItem() {
            return new MenuActionItem() {
                //Overridden
                @Override
                public void doAction(Context context, MenuItem item) {
                    doUpSelected();
                    sendResult(RESULT_UP);
                }
            };
        }

        //Overridden
        @Override
        public void onAttachReviewView() {
            ActionBar actionBar = getActivity().getActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(mDisplayHomeAsUp);
                actionBar.setDisplayShowHomeEnabled(false);
                if (mTitle != null) actionBar.setTitle(mTitle);
                addMenuItems();
            }
        }

        private class MenuActionItemInfo {
            private final boolean mFinishActivity;
            private final MenuActionItem mItem;

            private MenuActionItemInfo(MenuActionItem item, boolean finishActivity) {
                mItem = item;
                mFinishActivity = finishActivity;
            }
        }
    }
}
