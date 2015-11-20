package com.chdryra.android.reviewer.Models.TreeMethods.Factories;

import com.chdryra.android.reviewer.Models.TreeMethods.Implementation.VisitorRatingAverageOfChildren;
import com.chdryra.android.reviewer.Models.TreeMethods.Implementation.VisitorReviewsGetter;
import com.chdryra.android.reviewer.Models.TreeMethods.Interfaces.VisitorRatingCalculator;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryVisitorReviewNode {
    public VisitorReviewsGetter newReviewsGetter() {
        return new VisitorReviewsGetter();
    }

    public VisitorRatingCalculator newRatingsAverager() {
        return new VisitorRatingAverageOfChildren();
    }
}
