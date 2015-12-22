package com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MdDataList<T extends HasReviewId> extends MdDataCollection<T> implements IdableList<T>{
    private ReviewId mReviewId;

    public MdDataList(ReviewId reviewId) {
        mReviewId = reviewId;
    }

    @Override
    public ReviewId getReviewId() {
        return mReviewId;
    }
}
