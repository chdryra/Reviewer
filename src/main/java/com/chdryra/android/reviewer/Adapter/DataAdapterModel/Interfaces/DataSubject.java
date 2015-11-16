package com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces;

/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface DataSubject extends DataReview, Validatable{
    String getSubject();

    @Override
    String getReviewId();
}
