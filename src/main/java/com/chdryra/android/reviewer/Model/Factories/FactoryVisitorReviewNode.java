package com.chdryra.android.reviewer.Model.Factories;

import com.chdryra.android.reviewer.Model.Implementation.TreeMethods.ReviewGetter;
import com.chdryra.android.reviewer.Model.Implementation.TreeMethods.VisitorRatingAverager;
import com.chdryra.android.reviewer.Model.Implementation.TreeMethods.VisitorReviewDataGetterImpl;
import com.chdryra.android.reviewer.Model.Interfaces.Review;
import com.chdryra.android.reviewer.Model.Interfaces.VisitorRatingCalculator;
import com.chdryra.android.reviewer.Model.Interfaces.VisitorReviewDataGetter;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryVisitorReviewNode {
    public VisitorReviewDataGetter<Review> newReviewsCollector() {
        return new VisitorReviewDataGetterImpl<>(new ReviewGetter());
    }

    public VisitorRatingCalculator newRatingsAverager() {
        return new VisitorRatingAverager();
    }
}
