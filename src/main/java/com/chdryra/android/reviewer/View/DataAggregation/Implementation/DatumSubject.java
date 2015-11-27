package com.chdryra.android.reviewer.View.DataAggregation.Implementation;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.DataValidator;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataSubject;

/**
 * Created by: Rizwan Choudrey
 * On: 27/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DatumSubject implements DataSubject {
    private final String mReviewId;
    private final String mSubject;

    public DatumSubject(String reviewId, String subject) {
        mReviewId = reviewId;
        mSubject = subject;
    }

    @Override
    public String getSubject() {
        return mSubject;
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
