/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvAuthor;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by: Rizwan Choudrey
 * On: 13/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
class AuthorDataWrapper extends GridDataWrapperBasic<GvAuthor> {
    private static final GvDataType<GvAuthor> TYPE = GvAuthor.TYPE;
    private AuthorId mUserId;
    private GvDataList<GvAuthor> mData;
    private FactoryReviewViewAdapter mFactory;

    public AuthorDataWrapper(AuthorId userId,
                             GvDataList<GvAuthor> data,
                             FactoryReviewViewAdapter factory) {
        mUserId = userId;
        mData = data;
        mFactory = factory;
    }

    public void setData(GvDataList<GvAuthor> data) {
        mData = data;
        notifyDataObservers();
    }

    @Override
    public GvDataType<? extends GvData> getGvDataType() {
        return TYPE;
    }

    @Override
    public GvDataList<GvAuthor> getGridData() {
        return mData;
    }

    @Override
    public boolean isExpandable(GvAuthor datum) {
        return mData.contains(datum);
    }

    @Nullable
    @Override
    public ReviewViewAdapter<?> expandGridCell(GvAuthor datum) {
        return isExpandable(datum) ? mFactory.newReviewsListAdapter(datum.getAuthorId()) : null;
    }

    @Nullable
    @Override
    public ReviewViewAdapter<?> expandGridData() {
        Set<AuthorId> authors = new HashSet<>();
        for(AuthorId author : mData) {
            authors.add(author);
        }
        return mFactory.newTreeSummaryAdapter(mUserId, authors);
    }
}
