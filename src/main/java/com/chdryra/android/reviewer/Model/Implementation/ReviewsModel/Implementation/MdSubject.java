/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataSubject;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.DataValidator;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Interfaces.MdData;

/**
 * Review Data: subject
 */
public class MdSubject implements MdData, DataSubject {
    private final String mSubject;
    private final MdReviewId mReviewId;

    //Constructors
    public MdSubject(MdReviewId reviewId, String subject) {
        mSubject = subject;
        mReviewId = reviewId;
    }

    //Overridden
    @Override
    public String getSubject() {
        return mSubject;
    }

    @Override
    public String getReviewId() {
        return mReviewId.toString();
    }

    @Override
    public boolean hasData(DataValidator dataValidator) {
        return dataValidator.validateString(mSubject);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MdSubject)) return false;

        MdSubject rdSubject = (MdSubject) o;

        if (mReviewId != null ? !mReviewId.equals(rdSubject.mReviewId) : rdSubject
                .mReviewId != null) {
            return false;
        }
        if (mSubject != null ? !mSubject.equals(rdSubject.mSubject) : rdSubject.mSubject != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = mSubject != null ? mSubject.hashCode() : 0;
        result = 31 * result + (mReviewId != null ? mReviewId.hashCode() : 0);
        return result;
    }
}
