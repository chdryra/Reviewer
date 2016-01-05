package com.chdryra.android.reviewer.DataDefinitions.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 28/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class IdableDataList<T extends HasReviewId> extends IdableDataCollection<T>
        implements IdableList<T>{
    private ReviewId mReviewId;

    public IdableDataList(ReviewId reviewId) {
        mReviewId = reviewId;
    }

    @Override
    public ReviewId getReviewId() {
        return mReviewId;
    }
}
