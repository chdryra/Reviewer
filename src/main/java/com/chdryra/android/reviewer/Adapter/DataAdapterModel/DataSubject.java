package com.chdryra.android.reviewer.Adapter.DataAdapterModel;

/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface DataSubject extends DataReview{
    String getSubject();

    @Override
    String getReviewId();
}
