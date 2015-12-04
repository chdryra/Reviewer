package com.chdryra.android.reviewer.Database.Factories;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.Database.Implementation.ReviewLoaderDynamic;
import com.chdryra.android.reviewer.Database.Implementation.ReviewLoaderStatic;
import com.chdryra.android.reviewer.Database.Interfaces.BuilderReview;
import com.chdryra.android.reviewer.Database.Interfaces.ReviewLoader;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;

/**
 * Created by: Rizwan Choudrey
 * On: 16/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewLoader {
    public ReviewLoader newStaticLoader(BuilderReview reviewBuilder, DataValidator validator) {
        return new ReviewLoaderStatic(reviewBuilder, validator);
    }

    public ReviewLoader newDynamicLoader(FactoryReviews reviewsFactory) {
        return new ReviewLoaderDynamic(reviewsFactory);
    }
}
