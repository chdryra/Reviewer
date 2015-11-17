package com.chdryra.android.reviewer.TreeMethods.Factories;

import com.chdryra.android.reviewer.TreeMethods.Implementation.VisitorRatingAverageOfChildren;
import com.chdryra.android.reviewer.TreeMethods.Implementation.VisitorReviewsGetter;
import com.chdryra.android.reviewer.TreeMethods.Interfaces.VisitorRatingCalculator;
import com.chdryra.android.reviewer.TreeMethods.Interfaces.VisitorReviewNode;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryVisitorReviewNode {
    public VisitorReviewNode newReviewsGetter() {
        return new VisitorReviewsGetter();
    }

    public VisitorRatingCalculator newRatingsAverager() {
        return new VisitorRatingAverageOfChildren();
    }
}
