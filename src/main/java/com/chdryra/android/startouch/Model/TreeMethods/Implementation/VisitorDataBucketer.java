/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Model.TreeMethods.Implementation;

import android.support.annotation.NonNull;

import com.chdryra.android.corelibrary.Bucketing.BucketDistribution;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.startouch.Model.TreeMethods.Interfaces.NodeDataGetter;
import com.chdryra.android.startouch.Model.TreeMethods.Interfaces.NodeValueGetter;
import com.chdryra.android.startouch.Model.TreeMethods.Interfaces.VisitorReviewNode;

/**
 * Created by: Rizwan Choudrey
 * On: 05/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class VisitorDataBucketer<BucketingValue, Data extends HasReviewId> implements
        VisitorReviewNode {
    private final BucketDistribution<BucketingValue, Data> mDistribution;
    private final NodeValueGetter<BucketingValue> mBucketValueGetter;
    private final NodeDataGetter<Data> mDataGetter;

    public VisitorDataBucketer(BucketDistribution<BucketingValue, Data> distribution,
                               NodeValueGetter<BucketingValue> bucketValueGetter,
                               NodeDataGetter<Data> dataGetter) {
        mDistribution = distribution;
        mBucketValueGetter = bucketValueGetter;
        mDataGetter = dataGetter;
    }

    public BucketDistribution<BucketingValue, Data> getDistribution() {
        return mDistribution;
    }

    @Override
    public void visit(@NonNull ReviewNode node) {
        BucketingValue value = mBucketValueGetter.getData(node);
        Data datum = mDataGetter.getData(node);
        if (value != null && datum != null) {
            mDistribution.bucket(value, datum);
        } else if (value == null && datum != null) {
            mDistribution.dontBucket(datum);
        }
    }
}
