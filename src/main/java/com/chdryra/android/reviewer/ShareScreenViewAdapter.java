/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 19 February, 2015
 */

package com.chdryra.android.reviewer;

import android.content.Context;

import java.util.Date;

/**
 * Created by: Rizwan Choudrey
 * On: 19/02/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ShareScreenViewAdapter implements ReviewViewAdapter {
    private Context           mContext;
    private ReviewViewAdapter mAdapter;

    public ShareScreenViewAdapter(Context context, ReviewViewAdapter adapter) {
        mContext = context;
        mAdapter = adapter;
    }

    @Override
    public String getSubject() {
        return mAdapter.getSubject();
    }

    @Override
    public float getRating() {
        return mAdapter.getRating();
    }

    @Override
    public float getAverageRating() {
        return mAdapter.getAverageRating();
    }

    @Override
    public GvDataList getGridData() {
        return Administrator.get(mContext).getSocialPlatformList();
    }

    @Override
    public Author getAuthor() {
        return mAdapter.getAuthor();
    }

    @Override
    public Date getPublishDate() {
        return mAdapter.getPublishDate();
    }

    @Override
    public GvImageList getImages() {
        return mAdapter.getImages();
    }
}
