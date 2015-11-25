package com.chdryra.android.reviewer.Database.Implementation;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.DataValidator;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.Model.Interfaces.Review;

/**
 * Created by: Rizwan Choudrey
 * On: 13/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CriterionDb implements DataCriterion{
    private String mParentId;
    private Review mReview;

    public CriterionDb(String parentId, Review review) {
        mParentId = parentId;
        mReview = review;
    }

    @Override
    public String getSubject() {
        return mReview.getSubject().getSubject();
    }

    @Override
    public float getRating() {
        return mReview.getRating().getRating();
    }

    @Override
    public Review getReview() {
        return mReview;
    }

    @Override
    public String getReviewId() {
        return mParentId;
    }

    @Override
    public boolean hasData(DataValidator validator) {
        return false;
    }
}
