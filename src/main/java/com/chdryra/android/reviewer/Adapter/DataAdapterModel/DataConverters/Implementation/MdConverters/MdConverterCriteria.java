package com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Implementation.MdConverters;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataCriterionReview;
import com.chdryra.android.reviewer.Model.Interfaces.Review;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdCriterionList;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MdConverterCriteria extends MdConverterDataReview<DataCriterionReview,
        MdCriterionList.MdCriterion, MdCriterionList> {

    public MdConverterCriteria() {
        super(MdCriterionList.class);
    }

    @Override
    public MdCriterionList.MdCriterion convert(DataCriterionReview datum) {
        MdReviewId id = new MdReviewId(datum.getReviewId());
        return new MdCriterionList.MdCriterion(id, datum.getReview());
    }

    public MdCriterionList convertReviews(Iterable<? extends Review> reviews, String parentId) {
        MdReviewId id = new MdReviewId(parentId);
        MdCriterionList list = new MdCriterionList(id);
        for(Review review : reviews) {
            list.add(new MdCriterionList.MdCriterion(id, review));
        }
        return list;
    }


}
