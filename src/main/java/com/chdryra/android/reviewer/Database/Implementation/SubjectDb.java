package com.chdryra.android.reviewer.Database.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;

/**
 * Created by: Rizwan Choudrey
 * On: 13/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class SubjectDb implements DataSubject {
    private ReviewId mReviewId;
    private String mSubject;

    public SubjectDb(ReviewId reviewId, String subject) {
        mReviewId = reviewId;
        mSubject = subject;
    }

    @Override
    public String getSubject() {
        return mSubject;
    }

    @Override
    public ReviewId getReviewId() {
        return mReviewId;
    }

    @Override
    public boolean hasData(DataValidator dataValidator) {
        return dataValidator.validateString(mSubject);
    }
}
