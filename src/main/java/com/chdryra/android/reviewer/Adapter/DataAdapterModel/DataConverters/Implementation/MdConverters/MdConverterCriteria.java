package com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Implementation.MdConverters;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.Models.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Models.ReviewsModel.Implementation.MdCriterionList;
import com.chdryra.android.reviewer.Models.ReviewsModel.Implementation.MdReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MdConverterCriteria extends MdConverterDataReview<DataCriterion,
        MdCriterionList.MdCriterion, MdCriterionList> {

    public MdConverterCriteria() {
        super(MdCriterionList.class);
    }

    @Override
    public MdCriterionList.MdCriterion convert(DataCriterion datum) {
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
