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
public class ViewReview {
    private FragmentViewReview mParent;
    private ViewReviewAdapter  mAdapter;

    private ViewReviewAction.SubjectViewAction  mSubjectAction;
    private ViewReviewAction.RatingBarAction    mRatingAction;
    private ViewReviewAction.BannerButtonAction mButtonAction;
    private ViewReviewAction.GridItemAction     mGridAction;
    private ViewReviewAction.MenuAction         mMenuAction;

    private ViewReviewParams mParams;

    private GvDataList mData;
    private GvDataList mDataToShow;

    private boolean mRatingIsAverage = false;
    private boolean mIsEditable      = false;

    private ArrayList<DataSetObserver> mGridObservers;
    private HashMap<String, Fragment>  mActionListeners;

    private ViewModifier mModifier;

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
        public View modify(FragmentViewReview parent, View v, LayoutInflater inflater,
                ViewGroup container, Bundle savedInstanceState);
    }

    public ViewReview(FragmentViewReview parent, ViewReviewAdapter adapter, boolean isEditable) {
        mParent = parent;
        mAdapter = adapter;
        mData = adapter.getGridData();
        mDataToShow = mData;
        mIsEditable = isEditable;

        mGridObservers = new ArrayList<>();
        mActionListeners = new HashMap<>();

        setAction(new ViewReviewAction.SubjectViewAction());
        setAction(new ViewReviewAction.RatingBarAction());
        setAction(new ViewReviewAction.BannerButtonAction());
        setAction(new ViewReviewAction.GridItemAction());
        setAction(new ViewReviewAction.MenuAction());

        mParams = new ViewReviewParams();
    }

    public ViewReview(FragmentViewReview parent, ViewReviewAdapter mAdapter, boolean isEditable,
            ViewModifier modifier) {
        this(parent, mAdapter, isEditable);
        mModifier = modifier;
    }

    public ViewReviewAdapter getAdapter() {
        return mAdapter;
    }

    public void setAction(ViewReviewAction.SubjectViewAction action) {
        mSubjectAction = action;
        mSubjectAction.setViewReview(this);
    }

    public void setAction(ViewReviewAction.RatingBarAction action) {
        mRatingAction = action;
        mRatingAction.setViewReview(this);
    }

    public void setAction(ViewReviewAction.BannerButtonAction action) {
        mButtonAction = action;
        mButtonAction.setViewReview(this);
    }

    public void setAction(ViewReviewAction.GridItemAction action) {
        mGridAction = action;
        mGridAction.setViewReview(this);
    }

    public void setAction(ViewReviewAction.MenuAction action) {
        mMenuAction = action;
        mMenuAction.setViewReview(this);
    }

    public ViewReviewParams getParams() {
        return mParams;
    }

    public Activity getActivity() {
        return mParent.getActivity();
    }

    public GvDataList getGridData() {
        return mData;
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

    public ViewReviewAction.SubjectViewAction getSubjectViewAction() {
        return mSubjectAction;
    }

    public ViewReviewAction.RatingBarAction getRatingBarAction() {
        return mRatingAction;
    }

    public ViewReviewAction.BannerButtonAction getBannerButtonAction() {
        return mButtonAction;
    }

    public ViewReviewAction.GridItemAction getGridItemAction() {
        return mGridAction;
    }

    public ViewReviewAction.MenuAction getMenuAction() {
        return mMenuAction;
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
            mParent.setCover(covers.getRandomCover());
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
