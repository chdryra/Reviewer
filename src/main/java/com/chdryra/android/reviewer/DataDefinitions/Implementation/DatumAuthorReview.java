package com.chdryra.android.reviewer.DataDefinitions.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthorReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.UserId;

/**
 * Created by: Rizwan Choudrey
 * On: 27/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DatumAuthorReview implements DataAuthorReview {
    private ReviewId mReviewId;
    private String mName;
    private UserId mUserId;

    public DatumAuthorReview(ReviewId reviewId, String name, UserId userId) {
        mReviewId = reviewId;
        mName = name;
        mUserId = userId;
    }

    @Override
    public String getName() {
        return mName;
    }

    @Override
    public UserId getUserId() {
        return mUserId;
    }

    @Override
    public boolean hasData(DataValidator dataValidator) {
        return dataValidator.validate(this);
    }

    @Override
    public ReviewId getReviewId() {
        return mReviewId;
    }
}
