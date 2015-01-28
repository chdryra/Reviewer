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

/**
 * Created by: Rizwan Choudrey
 * On: 24/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ViewReview {
    private FragmentViewReview mParent;

    private ViewReviewAction.SubjectViewAction  mSubjectAction;
    private ViewReviewAction.RatingBarAction    mRatingAction;
    private ViewReviewAction.BannerButtonAction mButtonAction;
    private ViewReviewAction.GridItemAction     mGridAction;
    private ViewReviewAction.MenuAction         mMenuAction;

    private GvDataList mData;
    private GvDataList mDataToShow;

    private GvImageList.GvImage mCover;
    private boolean mIsEditable = false;

    private ArrayList<DataSetObserver> mGridObservers;

    private CellDimension mCellWidth  = CellDimension.HALF;
    private CellDimension mCellHeight = CellDimension.QUARTER;

    private GridViewImageAlpha mGridViewAlpha = GridViewImageAlpha.MEDIUM;

    private ViewModifier mModifier;

    public enum CellDimension {FULL, HALF, QUARTER}

    /**
     * Settings for GridView transparency with respect to background image.
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

    public interface ViewModifier {
        public View modify(FragmentViewReview parent, View v, LayoutInflater inflater,
                ViewGroup container, Bundle savedInstanceState);
    }

    public ViewReview(FragmentViewReview parent, GvDataList mGridData, GvImageList.GvImage cover,
            ViewReviewAction.SubjectViewAction sva, ViewReviewAction.RatingBarAction rba,
            ViewReviewAction.BannerButtonAction bba,
            ViewReviewAction.GridItemAction gia, ViewReviewAction.MenuAction mia,
            boolean isEditable) {
        mParent = parent;

        mData = mGridData;
        mDataToShow = mData;

        mCover = cover;
        mIsEditable = isEditable;

        mSubjectAction = sva;
        mRatingAction = rba;
        mButtonAction = bba;
        mGridAction = gia;
        mMenuAction = mia;

        mGridObservers = new ArrayList<>();

        mSubjectAction.setViewReview(this);
        mRatingAction.setViewReview(this);
        mButtonAction.setViewReview(this);
        mGridAction.setViewReview(this);
        mMenuAction.setViewReview(this);
    }

    public void setViewModifier(ViewModifier modifier) {
        mModifier = modifier;
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
        mDataToShow = mData;
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

    public GvImageList.GvImage getCover() {
        return mCover;
    }

    public void setCover(GvImageList.GvImage image) {
        mCover = image;
        mParent.updateCover();
    }

    public void updateUi() {
        mParent.updateUi();
    }

    public void addListener(Fragment listener, String tag) {
        FragmentManager manager = getActivity().getFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(listener, tag);
        ft.commit();
        manager.executePendingTransactions();
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
        if (!mGridObservers.contains(observer)) mGridObservers.add(observer);
    }

    public void notifyDataSetChanged() {
        for (DataSetObserver observer : mGridObservers) {
            observer.onChanged();
        }
    }

    public void setGridCellDimension(CellDimension width, CellDimension height) {
        mCellWidth = width;
        mCellHeight = height;
    }

    public CellDimension getGridCellWidth() {
        return mCellWidth;
    }

    public CellDimension getGridCellHeight() {
        return mCellHeight;
    }

    public GridViewImageAlpha getGridViewAlpha() {
        return mGridViewAlpha;
    }

    public void setTransparentGridCellBackground() {
        mGridViewAlpha = GridViewImageAlpha.TRANSPARENT;
    }

    public View modifyIfNeccesary(View v, LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        if (mModifier != null) {
            return mModifier.modify(mParent, v, inflater, container, savedInstanceState);
        } else {
            return v;
        }
    }
}
