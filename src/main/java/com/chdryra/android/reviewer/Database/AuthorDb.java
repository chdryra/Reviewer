package com.chdryra.android.reviewer.Database;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataValidator;
import com.chdryra.android.reviewer.Interfaces.Data.DataAuthorReview;

/**
 * Created by: Rizwan Choudrey
 * On: 13/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class AuthorDb implements DataAuthorReview {
    private String mReviewId;
    private String mName;
    private String mUserId;

    public AuthorDb(String reviewId, String name, String userId) {
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
