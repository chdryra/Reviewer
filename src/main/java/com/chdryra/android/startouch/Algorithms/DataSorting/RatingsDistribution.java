/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Algorithms.DataSorting;

import com.chdryra.android.corelibrary.Bucketing.Bucket;
import com.chdryra.android.corelibrary.Bucketing.BucketDistribution;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.RatingDefinition;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataRating;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by: Rizwan Choudrey
 * On: 05/12/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class RatingsDistribution extends BucketDistribution<Float, DataRating> {
    private static final int NUM_BUCKETS = 5;
    private static final float MIN = RatingDefinition.MIN_RATING;
    private static final float MAX = RatingDefinition.MAX_RATING;

    public RatingsDistribution(int numBuckets) {
        super(getBuckets(numBuckets));
    }

    public RatingsDistribution() {
        this(NUM_BUCKETS);
    }

    private static List<Bucket<Float, DataRating>> getBuckets(int numBuckets) {
        List<Bucket<Float, DataRating>> buckets = new ArrayList<>();
        float bucketSize = (MAX - MIN) / numBuckets;
        float min = MIN;
        float max = MIN + bucketSize;

        for(int i = 0; i < numBuckets - 1; ++i) {
            buckets.add(new Bucket<Float, DataRating>(new RatingRange(min, max, false)));
            min = max;
            max += bucketSize;
        }

        buckets.add(new Bucket<Float, DataRating>(new RatingRange(min, max, true)));

        return buckets;
    }
}
