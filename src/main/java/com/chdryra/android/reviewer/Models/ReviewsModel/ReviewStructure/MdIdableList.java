package com.chdryra.android.reviewer.Models.ReviewsModel.ReviewStructure;

import com.chdryra.android.reviewer.Interfaces.Data.DataReview;
import com.chdryra.android.reviewer.Interfaces.Data.IdableList;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdIdableCollection;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MdIdableList<T extends DataReview> extends MdIdableCollection<T> implements IdableList<T>{
    private MdReviewId mReviewId;

    public MdIdableList(MdReviewId reviewId) {
        mReviewId = reviewId;
    }

    @Override
    public String getReviewId() {
        return mReviewId.toString();
    }
}
