package com.chdryra.android.reviewer.Model.Factories;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataTag;
import com.chdryra.android.reviewer.Model.Implementation.TreeMethods.Implementation.ReviewGetter;
import com.chdryra.android.reviewer.Model.Implementation.TreeMethods.Implementation.TagsGetter;
import com.chdryra.android.reviewer.Model.Implementation.TreeMethods.Implementation.VisitorRatingAverager;
import com.chdryra.android.reviewer.Model.Implementation.TreeMethods.Implementation.VisitorReviewDataGetterImpl;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.TagsManager;
import com.chdryra.android.reviewer.Model.Interfaces.TreeMethods.VisitorRatingCalculator;
import com.chdryra.android.reviewer.Model.Interfaces.TreeMethods.VisitorReviewDataGetter;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryVisitorReviewNode {
    public VisitorReviewDataGetter<Review> newReviewsCollector() {
        return new VisitorReviewDataGetterImpl<>(new ReviewGetter());
    }

    public VisitorReviewDataGetter<DataTag> newTagsCollector(TagsManager tagsManager) {
        return new VisitorReviewDataGetterImpl<>(new TagsGetter(tagsManager));
    }

    public VisitorRatingCalculator newRatingsAverager() {
        return new VisitorRatingAverager();
    }
}
