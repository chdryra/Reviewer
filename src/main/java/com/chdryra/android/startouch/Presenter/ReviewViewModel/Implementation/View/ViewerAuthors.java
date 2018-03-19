/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.View;

import android.support.annotation.Nullable;

import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.startouch.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvAuthorName;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvDataType;

/**
 * Created by: Rizwan Choudrey
 * On: 13/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ViewerAuthors extends GridDataWrapperBasic<GvAuthorName> {
    private static final GvDataType<GvAuthorName> TYPE = GvAuthorName.TYPE;
    private GvDataList<GvAuthorName> mData;

    public ViewerAuthors(GvDataList<GvAuthorName> data) {
        mData = data;
    }

    public void setData(GvDataList<GvAuthorName> data) {
        mData = data;
        notifyDataObservers();
    }

    @Override
    public GvDataType<? extends GvData> getGvDataType() {
        return TYPE;
    }

    @Override
    public GvDataList<GvAuthorName> getGridData() {
        return mData;
    }

    @Override
    public boolean isExpandable(GvAuthorName datum) {
        return mData.contains(datum);
    }

    @Nullable
    @Override
    public ReviewViewAdapter<?> expandGridCell(GvAuthorName datum) {
        return null;
    }

    @Nullable
    @Override
    public ReviewViewAdapter<?> expandGridData() {
        return null;
//        Set<AuthorId> authors = new HashSet<>();
//        for(AuthorId author : mData) {
//            authors.add(author);
//        }
//        return mFactory.newTreeSummaryAdapter(mSessionUser, authors);
    }
}
