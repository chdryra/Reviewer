/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 27 January, 2015
 */

package com.chdryra.android.reviewer.View;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RatingBar;

import com.chdryra.android.mygenerallibrary.ActivityResultCode;
import com.chdryra.android.reviewer.Controller.ReviewViewAdapter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 27/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewViewAction {
    private final HashMap<String, Fragment> mListeners;
    private       ReviewView                mReviewView;

    public ReviewViewAction() {
        mListeners = new HashMap<>();
    }

    public ReviewView getReviewView() {
        return mReviewView;
    }

    public void attachReviewView(ReviewView reviewView) {
        if (mReviewView != null) onUnattachReviewView();
        mReviewView = reviewView;
        onAttachReviewView();
        for (Map.Entry<String, Fragment> entry : mListeners.entrySet()) {
            mReviewView.registerActionListener(entry.getValue(), entry.getKey());
        }
    }

    public void onUnattachReviewView() {

    }

    public void onAttachReviewView() {

    }

    public ReviewViewAdapter getAdapter() {
        return mReviewView.getAdapter();
    }

    public Activity getActivity() {
        return mReviewView != null ? mReviewView.getActivity() : null;
    }

    protected void registerActionListener(Fragment listener, String tag) {
        if (!mListeners.containsKey(tag)) {
            mListeners.put(tag, listener);
        } else {
            unregisterActionListener(tag);
            registerActionListener(listener, tag);
        }
    }

    protected void unregisterActionListener(String tag) {
        if (mListeners.containsKey(tag)) mListeners.remove(tag);
    }

    protected GvDataList getGridData() {
        return getReviewView().getGridData();
    }

    @SuppressWarnings("EmptyMethod")
    public static class SubjectAction extends ReviewViewAction {

        public String getSubject() {
            return getAdapter().getSubject();
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void afterTextChanged(Editable s) {
        }
    }

    public static class RatingBarAction extends ReviewViewAction {
        public float getRating() {
            return getAdapter().getRating();
        }

        public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

        }
    }

    public static class BannerButtonAction extends ReviewViewAction {
        private String mTitle;

        public BannerButtonAction() {
        }

        public BannerButtonAction(String title) {
            mTitle = title;
        }

        public static BannerButtonAction newDisplayButton(final String title) {
            return new BannerButtonAction(title) {
            };
        }

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
        public static final int                MENU_UP_ID = android.R.id.home;
        public static final ActivityResultCode RESULT_UP  = ActivityResultCode.UP;
        private final String                          mTitle;
        private final SparseArray<MenuActionItemInfo> mActionItems;
        private int     mMenuId          = -1;
        private boolean mDisplayHomeAsUp = false;

        public interface MenuActionItem {
            public void doAction(MenuItem item);
        }

        public MenuAction() {
            this(-1, null, false);
        }

        public MenuAction(int menuId, String title, boolean displayHomeAsUp) {
            mMenuId = menuId;
            mTitle = title;
            mDisplayHomeAsUp = displayHomeAsUp;
            mActionItems = new SparseArray<>();
            if (mDisplayHomeAsUp) addMenuActionItem(getUpActionItem(), MENU_UP_ID, true);
        }

        public MenuAction(String title) {
            this(-1, title, true);
        }

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

        public boolean hasOptionsMenu() {
            return mMenuId != -1;
        }

        public void inflateMenu(Menu menu, MenuInflater inflater) {
            if (hasOptionsMenu()) inflater.inflate(mMenuId, menu);
        }

        public void addMenuActionItem(MenuActionItem item, int itemId, boolean finishActivity) {
            mActionItems.put(itemId, new MenuActionItemInfo(item, finishActivity));
        }

        public boolean onItemSelected(android.view.MenuItem item) {
            MenuActionItemInfo actionItem = mActionItems.get(item.getItemId());
            if (actionItem != null) {
                actionItem.mItem.doAction(item);
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

        private MenuActionItem getUpActionItem() {
            return new MenuActionItem() {
                @Override
                public void doAction(MenuItem item) {
                    doUpSelected();
                    sendResult(RESULT_UP);
                }
            };
        }

        private class MenuActionItemInfo {
            private final boolean        mFinishActivity;
            private final MenuActionItem mItem;

            private MenuActionItemInfo(MenuActionItem item, boolean finishActivity) {
                mItem = item;
                mFinishActivity = finishActivity;
            }
        }
    }
}
