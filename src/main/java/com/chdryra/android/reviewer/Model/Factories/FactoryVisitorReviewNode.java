package com.chdryra.android.reviewer.Model.Factories;

import com.chdryra.android.reviewer.Model.Implementation.TreeMethods.VisitorRatingAverageOfChildren;
import com.chdryra.android.reviewer.Model.Interfaces.VisitorReviewsGetter;
import com.chdryra.android.reviewer.Model.Implementation.TreeMethods.VisitorReviewsGetterImpl;
import com.chdryra.android.reviewer.Model.Interfaces.VisitorRatingCalculator;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryVisitorReviewNode {
    public VisitorReviewsGetter newReviewsGetter() {
        return new VisitorReviewsGetterImpl();
    }

    public VisitorRatingCalculator newRatingsAverager() {
        return new VisitorRatingAverageOfChildren();
    }
}
