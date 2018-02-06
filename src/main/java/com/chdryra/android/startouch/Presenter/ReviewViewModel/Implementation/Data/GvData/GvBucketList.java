/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData;

import com.chdryra.android.startouch.Application.Implementation.AppInstanceAndroid;

/**
 * Used for Review summaries in published feed
 *
 * @see AppInstanceAndroid
 */
public class GvBucketList<BucketingValue, Data> extends GvDataListImpl<GvBucket> {
    public GvBucketList() {
        super(GvBucket.TYPE, null);
    }

    public GvBucketList(GvReviewId parentId) {
        super(GvBucket.TYPE, parentId);
    }

    public GvBucketList(GvBucketList<BucketingValue, Data> data) {
        super(data);
    }
}
