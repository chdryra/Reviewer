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
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 24/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewView implements ReviewViewAdapter.GridDataObserver {
    private FragmentReviewView mParent;
    private ReviewViewAdapter  mAdapter;

    private Map<Action, ReviewViewAction> mActions;

    private ViewReviewParams mParams;

    private GvDataList mDataToShow;

    private boolean mRatingIsAverage = false;
    private boolean mIsEditable      = false;

    private ArrayList<DataSetObserver> mGridObservers;
    private HashMap<String, Fragment>  mActionListeners;

    private ViewModifier mModifier;

    private enum Action {SUBJECTVIEW, RATINGBAR, BANNERBUTTON, GRIDITEM, MENU}

    public enum CellDimension {FULL, HALF, QUARTER}

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

    public interface ViewModifier {
        public View modify(FragmentReviewView parent, View v, LayoutInflater inflater,
                ViewGroup container, Bundle savedInstanceState);
    }

    public ReviewView(ReviewViewAdapter adapter, boolean isEditable) {
        mAdapter = adapter;
        mAdapter.registerGridDataObserver(this);

        mDataToShow = adapter.getGridData();
        mIsEditable = isEditable;

        mGridObservers = new ArrayList<>();
        mActionListeners = new HashMap<>();

        mActions = new HashMap<>();

        setAction(new ReviewViewAction.SubjectAction());
        setAction(new ReviewViewAction.RatingBarAction());
        setAction(new ReviewViewAction.BannerButtonAction());
        setAction(new ReviewViewAction.GridItemAction());
        setAction(new ReviewViewAction.MenuAction());

        mParams = new ViewReviewParams();
    }

    public ReviewView(ReviewViewAdapter mAdapter, boolean isEditable, ViewModifier modifier) {
        this(mAdapter, isEditable);
        mModifier = modifier;
    }

    public ReviewViewAdapter getAdapter() {
        return mAdapter;
    }

    public void attachFragment(FragmentReviewView parent) {
        if (mParent != null) throw new RuntimeException("Fragment already attached");
        mParent = parent;
        for (ReviewViewAction action : mActions.values()) {
            action.attachReviewView(this);
        }
    }

    public void setAction(ReviewViewAction.SubjectAction action) {
        setAction(Action.SUBJECTVIEW, action);
    }

    public void setAction(ReviewViewAction.RatingBarAction action) {
        setAction(Action.RATINGBAR, action);
    }

    public void setAction(ReviewViewAction.BannerButtonAction action) {
        setAction(Action.BANNERBUTTON, action);
    }

    public void setAction(ReviewViewAction.GridItemAction action) {
        setAction(Action.GRIDITEM, action);
    }

    public void setAction(ReviewViewAction.MenuAction action) {
        setAction(Action.MENU, action);
    }

    public ViewReviewParams getParams() {
        return mParams;
    }

    public Activity getActivity() {
        return mParent.getActivity();
    }

    public GvDataList getGridData() {
        return mAdapter.getGridData();
    }

    public GvDataList getGridViewData() {
        return mDataToShow;
    }

    public void setGridViewData(GvDataList dataToShow) {
        mDataToShow = dataToShow;
        mParent.updateGridData();
    }

    public void resetGridViewData() {
        mDataToShow = getGridData();
        mParent.updateGridData();
    }

    public ReviewViewAction.SubjectAction getSubjectViewAction() {
        return (ReviewViewAction.SubjectAction) mActions.get(Action.SUBJECTVIEW);
    }

    public ReviewViewAction.RatingBarAction getRatingBarAction() {
        return (ReviewViewAction.RatingBarAction) mActions.get(Action.RATINGBAR);
    }

    public ReviewViewAction.BannerButtonAction getBannerButtonAction() {
        return (ReviewViewAction.BannerButtonAction) mActions.get(Action.BANNERBUTTON);
    }

    public ReviewViewAction.GridItemAction getGridItemAction() {
        return (ReviewViewAction.GridItemAction) mActions.get(Action.GRIDITEM);
    }

    public ReviewViewAction.MenuAction getMenuAction() {
        return (ReviewViewAction.MenuAction) mActions.get(Action.MENU);
    }

    public boolean isEditable() {
        return mIsEditable;
    }

    public void proposeCover(GvImageList.GvImage image) {
        if (mParams.coverManager) {
            GvImageList images = mAdapter.getImages();
            GvImageList covers = images.getCovers();
            if (covers.size() == 1 && images.contains(image)) {
                covers.getItem(0).setIsCover(false);
                image.setIsCover(true);
            }
        }
    }

    public void updateCover() {
        if (mParams.coverManager) {
            GvImageList images = mAdapter.getImages();
            GvImageList covers = images.getCovers();
            GvImageList.GvImage cover = null;
            if (covers.size() > 0) {
                cover = covers.getRandomCover();
            } else if (images.size() > 0) {
                cover = images.getItem(0);
                cover.setIsCover(true);
            }

            mParent.setCover(cover);
        }
    }

    public void updateUi() {
        mParent.updateUi();
    }

    public void attachActionListeners() {
        FragmentManager manager = mParent.getFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        for (Map.Entry<String, Fragment> entry : mActionListeners.entrySet()) {
            ft.add(entry.getValue(), entry.getKey());
        }
        ft.commit();
        manager.executePendingTransactions();
    }

    public void registerActionListener(Fragment listener, String tag) {
        if (!mActionListeners.containsKey(tag)) mActionListeners.put(tag, listener);
    }

    public String getSubject() {
        return mParent.getSubject();
    }

    public void setSubject(String subject) {
        mParent.setSubject(subject);
    }

    public float getRating() {
        return mRatingIsAverage ? mAdapter.getAverageRating() : mParent.getRating();
    }

    public void setRating(float rating) {
        mParent.setRating(rating);
    }

    public boolean isRatingAverage() {
        return mRatingIsAverage;
    }

    public void setRatingAverage(boolean isAverage) {
        mRatingIsAverage = isAverage;
    }

    public void registerGridDataObserver(DataSetObserver observer) {
        if (!mGridObservers.contains(observer)) mGridObservers.add(observer);
    }

    public void unregisterGridDataObserver(DataSetObserver observer) {
        if (mGridObservers.contains(observer)) mGridObservers.remove(observer);
    }

    public void notifyDataSetChanged() {
        for (DataSetObserver observer : mGridObservers) {
            observer.onChanged();
        }
    }

    public View modifyIfNeccesary(View v, LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        if (mModifier != null) {
            return mModifier.modify(mParent, v, inflater, container, savedInstanceState);
        } else {
            return v;
        }
    }

    @Override
    public void onGridDataChanged() {
        notifyDataSetChanged();
    }

    private void setAction(Action type, ReviewViewAction action) {
        mActions.put(type, action);
        if (mParent != null) action.attachReviewView(this);
    }

    public static class ViewReviewParams {
        public GridViewImageAlpha gridAlpha              = GridViewImageAlpha.MEDIUM;
        public CellDimension      cellWidth              = CellDimension.HALF;
        public CellDimension      cellHeight             = CellDimension.QUARTER;
        public boolean            subjectIsVisibile      = true;
        public boolean            ratingIsVisibile       = true;
        public boolean            bannerButtonIsVisibile = true;
        public boolean            gridIsVisibile         = true;
        public boolean            coverManager           = true;
    }
}
