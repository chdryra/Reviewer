package com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataReviewIdable;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.IdableList;

/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MdIdableList<T extends DataReviewIdable> extends MdIdableCollection<T> implements IdableList<T>{
    private MdReviewId mReviewId;

    public MdIdableList(MdReviewId reviewId) {
        mReviewId = reviewId;
    }

    @Override
    public String getReviewId() {
        return mReviewId.toString();
    }
}
