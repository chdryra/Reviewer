/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Algorithms.DataSorting;

/**
 * Created by: Rizwan Choudrey
 * On: 05/12/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class BucketRange<BucketingValue> {
    private final BucketingValue mMin;
    private final BucketingValue mMax;
    private boolean mClosed;

    BucketRange(BucketingValue min, BucketingValue max, boolean closed) {
        mMin = min;
        mMax = max;
        mClosed = closed;
    }

    BucketingValue getMin() {
        return mMin;
    }

    BucketingValue getMax() {
        return mMax;
    }

    boolean isClosed() {
        return mClosed;
    }

    public abstract boolean inRange(BucketingValue value);

    boolean hasOverlap(BucketRange<BucketingValue> otherBucket) {
        BucketingValue min = otherBucket.getMin();
        BucketingValue max = otherBucket.getMax();

        return inRange(min)
                || (max.equals(getMin()) && otherBucket.isClosed())
                || (!max.equals(getMin()) && inRange(max));
    }

    @Override
    public String toString() {
        String closed = mClosed ? "" : "-";
        return mMin + "<" + mMax + closed;
    }
}
