package com.chdryra.android.reviewer.View.DataAggregation;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.DataValidator;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataAuthorReview;

/**
 * Created by: Rizwan Choudrey
 * On: 27/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DatumAuthorReview implements DataAuthorReview {
    private String mReviewId;
    private String mName;
    private String mUserId;

    public DatumAuthorReview(String reviewId, String name, String userId) {
        mReviewId = reviewId;
        mName = name;
        mUserId = userId;
    }

    @Override
    public String getName() {
        return mName;
    }

    @Override
    public String getUserId() {
        return mUserId;
    }

    @Override
    public boolean hasData(DataValidator dataValidator) {
        return dataValidator.validate(this);
    }

    @Override
    public String getReviewId() {
        return mReviewId;
    }
}
