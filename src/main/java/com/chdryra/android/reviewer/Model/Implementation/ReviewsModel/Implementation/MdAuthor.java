package com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthorReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.UserId;

/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MdAuthor implements DataAuthorReview {
    private ReviewId mReviewId;
    private String mName;
    private UserId mUserId;

    public MdAuthor(ReviewId reviewId, String name, UserId userId) {
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
        return dataValidator.validate(mReviewId) &&
                dataValidator.validateString(mName) && dataValidator.validate(mUserId);
    }

    @Override
    public ReviewId getReviewId() {
        return mReviewId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MdAuthor)) return false;

        MdAuthor mdAuthor = (MdAuthor) o;

        if (mReviewId != null ? !mReviewId.equals(mdAuthor.mReviewId) : mdAuthor.mReviewId != null)
            return false;
        if (mName != null ? !mName.equals(mdAuthor.mName) : mdAuthor.mName != null) return false;
        return !(mUserId != null ? !mUserId.equals(mdAuthor.mUserId) : mdAuthor.mUserId != null);

    }

    @Override
    public int hashCode() {
        int result = mReviewId != null ? mReviewId.hashCode() : 0;
        result = 31 * result + (mName != null ? mName.hashCode() : 0);
        result = 31 * result + (mUserId != null ? mUserId.hashCode() : 0);
        return result;
    }
}
