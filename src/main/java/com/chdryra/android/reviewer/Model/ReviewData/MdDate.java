package com.chdryra.android.reviewer.Model.ReviewData;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataDateReview;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataValidator;

/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MdDate implements MdData, DataDateReview {
    private long mTime;
    private MdReviewId mReviewId;

    public MdDate(MdReviewId reviewId, long time) {
        mTime = time;
        mReviewId = reviewId;
    }

    @Override
    public long getTime() {
        return mTime;
    }

    @Override
    public String getReviewId() {
        return mReviewId.toString();
    }

    @Override
    public boolean hasData(DataValidator dataValidator) {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MdDate)) return false;

        MdDate mdDate = (MdDate) o;

        if (mTime != mdDate.mTime) return false;
        return !(mReviewId != null ? !mReviewId.equals(mdDate.mReviewId) : mdDate.mReviewId !=
                null);

    }

    @Override
    public int hashCode() {
        int result = (int) (mTime ^ (mTime >>> 32));
        result = 31 * result + (mReviewId != null ? mReviewId.hashCode() : 0);
        return result;
    }
}
