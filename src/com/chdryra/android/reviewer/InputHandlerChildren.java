/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 22 October, 2014
 */

package com.chdryra.android.reviewer;

import android.content.Context;

import com.chdryra.android.reviewer.GVReviewSubjectRatingList.GVReviewSubjectRating;

/**
 * Created by: Rizwan Choudrey
 * On: 22/10/2014
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * Additional constraint over base {@link com.chdryra.android.reviewer.InputHandlerReviewData} to
 * ignore addition if current data already contains the subject.
 */
class InputHandlerChildren extends InputHandlerReviewData<GVReviewSubjectRating> {

    InputHandlerChildren() {
        super(GVReviewDataList.GVType.CHILDREN);
    }

    @Override
    boolean passesAddConstraint(GVReviewSubjectRating datum, Context context) {
        return super.isNewAndValid(datum, context) && !constraint(datum, context);
    }

    private boolean constraint(GVReviewSubjectRating datum, Context context) {
        GVReviewSubjectRatingList data = (GVReviewSubjectRatingList) getData();
        if (data != null && data.contains(datum.getSubject())) {
            makeToastHasItem(context);
            return true;
        }
        return false;
    }
}
