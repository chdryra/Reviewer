/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Algorithms.DataSorting;

import com.chdryra.android.mygenerallibrary.Bucketing.BucketDistribution;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewNode;

/**
 * Created by: Rizwan Choudrey
 * On: 05/12/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface DataBucketer<BucketingValue, Data extends HasReviewId> {
    interface DataBucketerCallback<BucketingValue, Data> {
        void onDataBucketed(BucketDistribution<BucketingValue, Data> distribution);
    }

    void bucketData(ReviewNode root, DataBucketerCallback<BucketingValue, Data> callback);
}
