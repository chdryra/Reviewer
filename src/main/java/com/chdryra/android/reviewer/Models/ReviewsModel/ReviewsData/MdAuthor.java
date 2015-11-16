package com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataAuthorReview;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.DataValidator;

/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MdAuthor implements MdData, DataAuthorReview {
    private MdReviewId mReviewId;
    private String mName;
    private String mUserId;

    public MdAuthor(MdReviewId reviewId, String name, String userId) {
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
        return dataValidator.validateString(mName) && dataValidator.validateString(mUserId);
    }

    @Override
    public String getReviewId() {
        return mReviewId.toString();
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
