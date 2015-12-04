package com.chdryra.android.reviewer.DataDefinitions.DataConverters.Implementation.MdConverters;


import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterionReview;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdCriterion;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdDataList;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdReviewId;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;

/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MdConverterCriteria extends MdConverterDataReview<DataCriterionReview,
        MdCriterion> {

    @Override
    public MdCriterion convert(DataCriterionReview datum) {
        MdReviewId id = new MdReviewId(datum.getReviewId());
        return new MdCriterion(id, datum.getReview());
    }

    public MdDataList<MdCriterion> convertReviews(Iterable<? extends Review> reviews, String parentId) {
        MdReviewId id = new MdReviewId(parentId);
        MdDataList<MdCriterion> list = new MdDataList<>(id);
        for(Review review : reviews) {
            list.add(new MdCriterion(id, review));
        }
        return list;
    }


}
