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

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 24/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewView {
    private FragmentReviewView mParent;

    private ReviewViewAction.SubjectViewAction  mSubjectAction;
    private ReviewViewAction.RatingBarAction    mRatingAction;
    private ReviewViewAction.BannerButtonAction mButtonAction;
    private ReviewViewAction.GridItemAction     mGridAction;
    private ReviewViewAction.MenuAction         mMenuAction;

    private GvDataList mData;
    private GvDataList mDataToShow;

    private GvImageList.GvImage mCover;
    private boolean mIsEditable = false;

    private ArrayList<DataSetObserver> mGridObservers;

    private CellDimension mCellWidth  = CellDimension.HALF;
    private CellDimension mCellHeight = CellDimension.QUARTER;

    public enum CellDimension {
        FULL(FragmentReviewView.CellDimension.FULL),
        HALF(FragmentReviewView.CellDimension.HALF),
        QUARTER(FragmentReviewView.CellDimension.QUARTER);

        private FragmentReviewView.CellDimension mDim;

        private CellDimension(FragmentReviewView.CellDimension dim) {
            mDim = dim;
        }
    }

    public ReviewView(FragmentReviewView parent, GvDataList mGridData, GvImageList.GvImage cover,
            ReviewViewAction.SubjectViewAction sva, ReviewViewAction.RatingBarAction rba,
            ReviewViewAction.BannerButtonAction bba,
            ReviewViewAction.GridItemAction gia, ReviewViewAction.MenuAction mia,
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

    public ReviewViewAction.SubjectViewAction getSubjectViewAction() {
        return mSubjectAction;
    }

    public ReviewViewAction.RatingBarAction getRatingBarAction() {
        return mRatingAction;
    }

    public ReviewViewAction.BannerButtonAction getBannerButtonAction() {
        return mButtonAction;
    }

    public ReviewViewAction.GridItemAction getGridItemAction() {
        return mGridAction;
    }

    public ReviewViewAction.MenuAction getMenuAction() {
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

    public FragmentReviewView.CellDimension getGridCellWidth() {
        return mCellWidth.mDim;
    }

    public FragmentReviewView.CellDimension getGridCellHeight() {
        return mCellHeight.mDim;
    }
}
