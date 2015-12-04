package com.chdryra.android.reviewer.DataDefinitions.Interfaces;

/**
 * Created by: Rizwan Choudrey
 * On: 27/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface DataTag extends DataReviewIdable, Validatable{
    String getTag();

    @Override
    String getReviewId();
}
