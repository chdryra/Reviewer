/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 25 March, 2015
 */

package com.chdryra.android.reviewer.View;

import android.os.Parcel;

/**
 * Created by: Rizwan Choudrey
 * On: 25/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class GvDataBasic implements GvData {
    private GvReviewId mReviewId;

    protected GvDataBasic() {
    }

    protected GvDataBasic(GvReviewId reviewId) {
        mReviewId = reviewId;
    }

    public GvDataBasic(Parcel in) {
        mReviewId = in.readParcelable(GvReviewId.class.getClassLoader());
    }

    @Override
    public boolean hasHoldingReview() {
        return mReviewId != null;
    }

    @Override
    public GvReviewId getHoldingReviewId() {
        return mReviewId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(mReviewId, i);
    }
}
