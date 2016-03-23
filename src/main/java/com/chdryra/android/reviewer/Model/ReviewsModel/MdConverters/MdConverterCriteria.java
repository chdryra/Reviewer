/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.MdConverters;


import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterionReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.MdCriterion;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.MdDataList;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.MdReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;

/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MdConverterCriteria extends MdConverterDataReview<DataCriterionReview, MdCriterion> {

    @Override
    public MdCriterion convert(DataCriterionReview datum, ReviewId reviewId) {
        return new MdCriterion(newMdReviewId(reviewId), datum.getReview());
    }

    public MdDataList<MdCriterion> convertReviews(Iterable<? extends Review> reviews, ReviewId parentId) {
        MdReviewId id = newMdReviewId(parentId);
        MdDataList<MdCriterion> list = new MdDataList<>(id);
        for(Review review : reviews) {
            list.add(new MdCriterion(id, review));
        }
        return list;
    }


}
