package com.chdryra.android.reviewer.Adapter.DataAdapterModel;

/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface DataAuthorReview extends DataReview, DataAuthor {
    @Override
    String getName();

    @Override
    String getUserId();

    @Override
    boolean hasData(DataValidator dataValidator);

    @Override
    String getReviewId();
}
