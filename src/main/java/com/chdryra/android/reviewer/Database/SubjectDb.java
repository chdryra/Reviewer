package com.chdryra.android.reviewer.Database;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataValidator;
import com.chdryra.android.reviewer.Interfaces.Data.DataSubject;

/**
 * Created by: Rizwan Choudrey
 * On: 13/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class SubjectDb implements DataSubject {
    private String mReviewId;
    private String mSubject;

    public SubjectDb(String reviewId, String subject) {
        mReviewId = reviewId;
        mSubject = subject;
    }

    @Override
    public String getSubject() {
        return mSubject;
    }

    @Override
    public String getReviewId() {
        return mReviewId;
    }

    @Override
    public boolean hasData(DataValidator dataValidator) {
        return dataValidator.validateString(mSubject);
    }
}
