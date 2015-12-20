package com.chdryra.android.reviewer.Model.Interfaces.TreeMethods;

import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.ReviewNode;

import java.util.Iterator;

/**
 * Created by: Rizwan Choudrey
 * On: 01/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewNodeIterator extends Iterator<ReviewNode> {
    @Override
    boolean hasNext();

    @Override
    ReviewNode next();

    @Override
    void remove();
}
