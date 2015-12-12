package com.chdryra.android.reviewer.DataDefinitions.Interfaces;

/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface DataSubject extends HasReviewId, Validatable{
    String getSubject();

    @Override
    ReviewId getReviewId();
}
