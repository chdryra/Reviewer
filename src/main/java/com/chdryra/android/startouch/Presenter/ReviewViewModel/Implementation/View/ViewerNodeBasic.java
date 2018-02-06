/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.View;

import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvDataCache;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 12/05/2015
 * Email: rizwan.choudrey@gmail.com
 */

public abstract class ViewerNodeBasic<T extends GvData> extends GridDataWrapperBasic<T>
        implements ReviewNode.NodeObserver {
    private final ReviewNode mNode;
    private final GvDataType<?> mType;

    private GvDataCache<T> mCache;
    private Comparator<? super T> mComparator;

    protected abstract GvDataList<T> makeGridData();

    ViewerNodeBasic(ReviewNode node, GvDataType<?> type) {
        mNode = node;
        mType = type;
    }

    protected void nullifyCacheAndNotify() {
        nullifyCache();
        notifyDataObservers();
    }

    protected void onNullifyCache() {

    }

    @Override
    protected void onAttach() {
        super.onAttach();
        mCache = null;
        mNode.registerObserver(this);
    }

    @Override
    protected void onDetach() {
        mNode.unregisterObserver(this);
        nullifyCache();
        super.onDetach();
    }

    @Override
    public GvDataType<?> getGvDataType() {
        return mType;
    }

    @Override
    public void onChildAdded(ReviewNode child) {
        nullifyCacheAndNotify();
    }

    @Override
    public void onChildRemoved(ReviewNode child) {
        nullifyCacheAndNotify();
    }

    @Override
    public void onNodeChanged() {
        nullifyCacheAndNotify();
    }

    @Override
    public void onTreeChanged() {
        nullifyCacheAndNotify();
    }

    @Override
    public GvDataList<T> getGridData() {
        if (mCache == null) {
            mCache = new GvDataCache<>(makeGridData());
            sortAndNotify(null);
        }

        return mCache.getData();
    }

    @Override
    public void sort(Comparator<? super T> comparator, OnSortedCallback callback) {
        mComparator = comparator;
        sortAndNotify(callback);
    }

    ReviewNode getReviewNode() {
        return mNode;
    }

    @Nullable
    GvDataList<T> getCache() {
        return mCache.getData();
    }

    protected void setCache(GvDataList<T> cache) {
        mCache = new GvDataCache<>(cache);
        sortAndNotify(null);
    }

    private void sortAndNotify(@Nullable final OnSortedCallback callback) {
        sortCache(new OnSortedCallback() {
            @Override
            public void onSorted(CallbackMessage message) {
                if(callback != null) callback.onSorted(message);
                if(message.isOk()) notifyDataObservers();
            }
        });
    }

    private void nullifyCache() {
        onNullifyCache();
        mCache = null;
    }

    private void sortCache(OnSortedCallback callback) {
        if (mComparator != null && mCache != null) {
            mCache.sort(mComparator, callback);
        } else {
            callback.onSorted(CallbackMessage.error(
                    mComparator == null ? "No comparator" : "No cache"));
        }
    }
}
