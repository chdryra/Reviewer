package com.chdryra.android.reviewer.DataDefinitions.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 12/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DatumReviewId implements ReviewId {
    private String mId;

    public DatumReviewId(String id) {
        mId = id;
    }

    @Override
    public String toString() {
        return mId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReviewId)) return false;

        ReviewId that = (ReviewId) o;

        return !(mId != null ? !mId.equals(that.toString()) : that.toString() != null);

    }

    @Override
    public int hashCode() {
        return mId != null ? mId.hashCode() : 0;
    }
}
