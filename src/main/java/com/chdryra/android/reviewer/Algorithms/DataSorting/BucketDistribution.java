/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Algorithms.DataSorting;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by: Rizwan Choudrey
 * On: 05/12/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class BucketDistribution<BucketingValue, ItemType> {
    private final List<Bucket<BucketingValue, ItemType>> mBuckets;
    private List<ItemType> mNotBucketed = new ArrayList<>();

    public BucketDistribution(List<Bucket<BucketingValue, ItemType>> buckets) {
        mBuckets = new ArrayList<>();
        mNotBucketed = new ArrayList<>();
        for(Bucket<BucketingValue, ItemType> bucket : buckets) {
            addBucket(bucket);
        }
    }

    private void addBucket(Bucket<BucketingValue, ItemType> bucket) {
        boolean overlap = false;
        for(Bucket<BucketingValue, ItemType> item : mBuckets) {
            if(item.hasOverlap(bucket)) {
                overlap = true;
                break;
            }
        }

        if(!overlap) mBuckets.add(bucket);
    }

    public List<Bucket<BucketingValue, ItemType>> getBuckets() {
        return mBuckets;
    }

    public int size() {
        int num = mNotBucketed.size();
        for(Bucket<BucketingValue, ItemType> bucket : mBuckets) {
            num += bucket.getBucketedItems().size();
        }

        return num;
    }

    public void bucket(BucketingValue value, ItemType item) {
        boolean bucketed = false;
        for (Bucket<BucketingValue, ItemType> bucket : mBuckets) {
            bucketed = bucket.bucketIfInRange(value, item);
            if (bucketed) break;
        }

        if (!bucketed) dontBucket(item);
    }

    public void dontBucket(ItemType item) {
        mNotBucketed.add(item);
    }
}
