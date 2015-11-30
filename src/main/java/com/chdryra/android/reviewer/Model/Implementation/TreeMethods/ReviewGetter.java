/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 13 May, 2015
 */

package com.chdryra.android.reviewer.Model.Implementation.TreeMethods;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.IdableDataList;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.IdableList;
import com.chdryra.android.reviewer.Model.Interfaces.Review;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewNode;

/**
 * Created by: Rizwan Choudrey
 * On: 13/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewGetter implements NodeDataGetter<Review> {
    @Override
    public IdableList<Review> getData(ReviewNode node) {
        IdableList<Review> reviews = new IdableDataList<>(node.getReviewId());
        reviews.add(node.getReview());
        return reviews;
    }
}
