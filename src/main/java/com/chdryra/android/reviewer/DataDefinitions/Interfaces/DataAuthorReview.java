package com.chdryra.android.reviewer.DataDefinitions.Interfaces;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;

/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface DataAuthorReview extends HasReviewId, DataAuthor {
    @Override
    String getName();

    @Override
    UserId getUserId();

    @Override
    boolean hasData(DataValidator dataValidator);

    @Override
    ReviewId getReviewId();
}
