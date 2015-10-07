/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 16 April, 2015
 */

package com.chdryra.android.reviewer.Model.TreeMethods;

import com.chdryra.android.reviewer.Model.ReviewData.IdableList;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;

/**
 * Created by: Rizwan Choudrey
 * On: 16/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewTreeComparer {
    //Static methods
    public static boolean compareNodes(ReviewNode lhs, ReviewNode rhs) {
        if (lhs.isRatingAverageOfChildren() != rhs.isRatingAverageOfChildren()) return false;
        if (!lhs.getId().equals(rhs.getId())) return false;
        if (!lhs.getReview().equals(rhs.getReview())) return false;
        if (!lhs.getChildren().equals(rhs.getChildren())) return false;

        ReviewNode lParent = lhs.getParent();
        ReviewNode rParent = rhs.getParent();
        return lParent == null ? rParent == null : lParent.getId().equals(rParent.getId());
    }

    public static boolean compareTrees(ReviewNode lhs, ReviewNode rhs) {
        ReviewNode lRoot = lhs.getRoot();
        ReviewNode rRoot = rhs.getRoot();

        return compareSubTrees(lRoot, rRoot);
    }

    public static boolean compareSubTrees(ReviewNode lhs, ReviewNode rhs) {
        if (!compareNodes(lhs, rhs)) return false;
        IdableList<ReviewNode> lchildren = lhs.getChildren();
        IdableList<ReviewNode> rchildren = rhs.getChildren();
        if (lchildren.size() != rchildren.size()) return false;
        for (int i = 0; i < lchildren.size(); ++i) {
            if (!compareSubTrees(lchildren.getItem(i), rchildren.getItem(i))) return false;
        }

        return true;
    }
}
