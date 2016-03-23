/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 9 June, 2015
 */

package com.chdryra.android.reviewer.test.Model.TreeMethods;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation
        .MdIdableCollection;
import com.chdryra.android.reviewer.Model.TreeMethods.Implementation.ReviewGetter;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.Interfaces.VisitorReviewsGetter;
import com.chdryra.android.reviewer.test.TestUtils.ReviewMocker;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 09/06/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class VisitorReviewsGetterTest extends TestCase {
    @SmallTest
    public void testVisit() {
        ReviewNode node = ReviewMocker.newReviewNode(false);
        VisitorReviewsGetter visitor = new ReviewGetter();
        node.acceptVisitor(visitor);

        IdableList<Review> nodes = visitor.getReviews();
        MdIdableCollection<Review> flattened = flatten(node);
        assertEquals(flattened.size(), nodes.size());
        for (Review item : flattened) {
            assertTrue(nodes.containsId(item.getMdReviewId()));
        }
    }

    private MdIdableCollection<Review> flatten(ReviewNode node) {
        MdIdableCollection<Review> reviews = new MdIdableCollection<>();
        reviews.add(node.getReview());

        for (ReviewNode child : node.getChildren()) {
            reviews.addCollection(flatten(child));
        }

        return reviews;
    }
}
