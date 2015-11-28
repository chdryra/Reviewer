package com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.DataValidator;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataDateReview;

/**
 * Created by: Rizwan Choudrey
 * On: 27/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DatumDateReview implements DataDateReview {
    private String mReviewId;
    private long mTime;

    public DatumDateReview(String reviewId, long time) {
        mReviewId = reviewId;
        mTime = time;
    }

    @Override
    public long getTime() {
        return mTime;
    }

    @Override
    public String getReviewId() {
        return mReviewId;
    }

    @Override
    public boolean hasData(DataValidator validator) {
        return validator.validate(this);
    }
}
