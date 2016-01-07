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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DatumAuthorReview)) return false;

        DatumAuthorReview that = (DatumAuthorReview) o;

        if (!mReviewId.equals(that.mReviewId)) return false;
        if (!mName.equals(that.mName)) return false;
        return mUserId.equals(that.mUserId);

    }

    @Override
    public int hashCode() {
        int result = mReviewId.hashCode();
        result = 31 * result + mName.hashCode();
        result = 31 * result + mUserId.hashCode();
        return result;
    }
}
