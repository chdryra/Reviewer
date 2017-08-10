/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData;

import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolder;
import com.chdryra.android.mygenerallibrary.Bucketing.Bucket;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DataValidator;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders.VhBucket;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders.ViewHolderFactory;

/**
 * Created by: Rizwan Choudrey
 * On: 06/12/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class GvBucket<BucketingValue, Data> extends GvDataBasic<GvBucket> {
    public static final GvDataType<GvBucket> TYPE
            = new GvDataType<>(GvBucket.class, "distribution", "distribution");

    private final Bucket<BucketingValue, Data> mBucket;
    private final int mTotalItems;
    private final ViewHolderFactory<VhBucket<BucketingValue, Data>> mVhFactory;

    public GvBucket(Bucket<BucketingValue, Data> bucket,
                    int totalItems,
                    ViewHolderFactory<VhBucket<BucketingValue, Data>> vhFactory) {
        super(TYPE);
        mBucket = bucket;
        mTotalItems = totalItems;
        mVhFactory = vhFactory;
    }

    public Bucket<BucketingValue, Data> getBucket() {
        return mBucket;
    }

    public double getPercentageOfTotal() {
        return mTotalItems != 0 ?
                (double)mBucket.getBucketedItems().size() / (double) mTotalItems : 0;
    }

    @Override
    public ViewHolder getViewHolder() {
        return mVhFactory.newViewHolder();
    }

    @Override
    public boolean isValidForDisplay() {
        return mBucket != null;
    }

    @Override
    public boolean hasData(DataValidator validator) {
        return isValidForDisplay();
    }
}
