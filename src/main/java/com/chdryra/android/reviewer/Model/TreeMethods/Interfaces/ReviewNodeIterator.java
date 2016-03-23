/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.TreeMethods.Interfaces;

import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;

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
