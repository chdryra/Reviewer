/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 27 January, 2015
 */

package com.chdryra.android.reviewer;

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

import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 27/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ViewReviewAction {
    private GvAdapter                 mAdapter;
    private ViewReview                mViewReview;
    private HashMap<String, Fragment> mListeners;

    private ViewReviewAction() {
        mListeners = new HashMap<>();
    }

    private ViewReviewAction(GvAdapter adapter) {
        mListeners = new HashMap<>();
        mAdapter = adapter;
    }

    public ViewReview getViewReview() {
        return mViewReview;
    }

    public void setViewReview(ViewReview viewReview) {
        if (mViewReview != null) onUnsetViewReview();
        mViewReview = viewReview;
        onSetViewReview();
        for (Map.Entry<String, Fragment> entry : mListeners.entrySet()) {
            getViewReview().registerActionListener(entry.getValue(), entry.getKey());
        }
    }

    public void onUnsetViewReview() {

    }

    public void onSetViewReview() {

    }

    public GvAdapter getAdapter() {
        return mAdapter;
    }


    public Activity getActivity() {
        return mViewReview != null ? mViewReview.getActivity() : null;
    }

    protected void registerActionListener(Fragment listener, String tag) {
        if (!mListeners.containsKey(tag)) mListeners.put(tag, listener);
    }

    protected GvDataList getData() {
        return getViewReview().getGridData();
    }

    public static class TypedViewAction extends ViewReviewAction {
        private GvDataList.GvType mDataType;

        protected TypedViewAction() {

        }

        public TypedViewAction(GvAdapter adapter, GvDataList.GvType dataType) {
            super(adapter);
            mDataType = dataType;
        }

        public GvDataList.GvType getDataType() {
            return mDataType;
        }
    }

    public static class SubjectViewAction extends ViewReviewAction {
        public SubjectViewAction() {

        }

        public SubjectViewAction(GvAdapter adapter) {
            super(adapter);
        }

        public String getSubject() {
            GvAdapter adapter = getAdapter();
            return adapter != null ? getAdapter().getSubject() : "";
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void afterTextChanged(Editable s) {
        }
    }

    public static class RatingBarAction extends ViewReviewAction {
        public RatingBarAction() {

        }

        public RatingBarAction(GvAdapter adapter) {
            super(adapter);
        }

        public float getRating() {
            GvAdapter adapter = getAdapter();
            return adapter != null ? getAdapter().getRating() : 0f;
        }

        public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        }
    }

    public static class BannerButtonAction extends TypedViewAction {
        public BannerButtonAction() {

        }

        public BannerButtonAction(GvAdapter adapter, GvDataList.GvType dataType) {
            super(adapter, dataType);
        }

        public static BannerButtonAction newDisplayButton(GvAdapter adapter,
                GvDataList.GvType dataType, final String title) {
            return new BannerButtonAction(adapter, dataType) {
                @Override
                public String getButtonTitle() {
                    return title;
                }
            };
        }

        public String getButtonTitle() {
            return null;
        }

        public void onClick(View v) {
        }
    }

    public static class GridItemAction extends TypedViewAction {
        public GridItemAction() {

        }

        public GridItemAction(GvAdapter adapter, GvDataList.GvType dataType) {
            super(adapter, dataType);
        }

        public void onGridItemClick(GvDataList.GvData item, View v) {
        }

        public void onGridItemLongClick(GvDataList.GvData item, View v) {
            onGridItemClick(item, v);
        }
    }

    public static class MenuAction extends TypedViewAction {
        public static final int                MENU_UP_ID = android.R.id.home;
        public static final ActivityResultCode RESULT_UP  = ActivityResultCode.UP;

        private int     mMenuId          = -1;
        private boolean mDisplayHomeAsUp = false;

        private SparseArray<MenuActionItemInfo> mActionItems;

        public MenuAction() {
            this(-1, false);
        }

        public MenuAction(int menuId, boolean displayHomeAsUp) {
            mMenuId = menuId;
            mDisplayHomeAsUp = displayHomeAsUp;
            mActionItems = new SparseArray<>();
            if (mDisplayHomeAsUp) addMenuActionItem(getUpActionItem(), MENU_UP_ID, true);
        }

        public MenuAction(GvAdapter adapter, GvDataList.GvType dataType, int menuId) {
            this(adapter, dataType, menuId, true);
        }

        public MenuAction(GvAdapter adapter, GvDataList.GvType dataType,
                boolean displayHomeAsUp) {
            super(adapter, dataType);
            mDisplayHomeAsUp = displayHomeAsUp;
            mActionItems = new SparseArray<>();
            if (mDisplayHomeAsUp) addMenuActionItem(getUpActionItem(), MENU_UP_ID, true);
        }

        public MenuAction(GvAdapter adapter,
                GvDataList.GvType dataType, int menuId, boolean displayHomeAsUp) {
            super(adapter, dataType);
            mMenuId = menuId;
            mDisplayHomeAsUp = displayHomeAsUp;
            mActionItems = new SparseArray<>();
            if (mDisplayHomeAsUp) addMenuActionItem(getUpActionItem(), MENU_UP_ID, true);
        }

        @Override
        public void onSetViewReview() {
            ActionBar actionBar = getActivity().getActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(mDisplayHomeAsUp);
                actionBar.setDisplayShowHomeEnabled(false);
                if (getDataType() != null) actionBar.setTitle(getDataType().getDataString());
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
                if (getAdapter() != null) {
                    Administrator.get(getActivity()).pack(getAdapter(), i);
                }
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

        public static abstract class MenuActionItem {
            public abstract void doAction(MenuItem item);
        }

        private class MenuActionItemInfo {
            private boolean        mFinishActivity;
            private MenuActionItem mItem;

            private MenuActionItemInfo(MenuActionItem item, boolean finishActivity) {
                mItem = item;
                mFinishActivity = finishActivity;
            }
        }
    }
}
