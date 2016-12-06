/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData;

import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolder;
import com.chdryra.android.reviewer.Algorithms.DataSorting.Bucket;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DataValidator;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders.VhBucket;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders.ViewHolderFactory;

/**
 * Created by: Rizwan Choudrey
 * On: 06/12/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class GvBucket<BucketingValue, Data> extends GvDataBasic<GvBucket> {
    public static final GvDataType<GvBucket> TYPE = new GvDataType<>(GvBucket.class, "Bucket");

    private final Bucket<BucketingValue, Data> mBucket;
    private final ViewHolderFactory<VhBucket<BucketingValue, Data>> mVhFactory;
    private VhBucket<BucketingValue, Data> mViewHolder;

    public GvBucket(Bucket<BucketingValue, Data> bucket,
                    ViewHolderFactory<VhBucket<BucketingValue, Data>> vhFactory) {
        super(TYPE);
        mBucket = bucket;
        mVhFactory = vhFactory;
    }

    public GvBucket(GvReviewId reviewId,
                    Bucket<BucketingValue, Data> bucket,
                    ViewHolderFactory<VhBucket<BucketingValue, Data>> vhFactory) {
        super(TYPE, reviewId);
        mBucket = bucket;
        mVhFactory = vhFactory;
    }

    public Bucket<BucketingValue, Data> getBucket() {
        return mBucket;
    }

    @Override
    public ViewHolder getViewHolder() {
        return mVhFactory.newViewHolder();
    }

    @Override
    public boolean isValidForDisplay() {
        return true;
    }

    @Override
    public boolean hasData(DataValidator validator) {
        return true;
    }
}
