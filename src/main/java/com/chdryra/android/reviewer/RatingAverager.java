/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 13 February, 2015
 */

package com.chdryra.android.reviewer;

/**
 * Created by: Rizwan Choudrey
 * On: 13/02/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RatingAverager {
    public static float average(ReviewNode node) {
        if (node.getChildren().size() > 0) {
            VisitorRatingCalculator visitor = new VisitorRatingAverageOfChildren();
            node.acceptVisitor(visitor);
            return visitor.getRating();
        } else {
            return 0f;
        }
    }
}
