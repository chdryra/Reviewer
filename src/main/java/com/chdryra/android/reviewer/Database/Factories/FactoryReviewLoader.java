package com.chdryra.android.reviewer.Database.Factories;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.Database.Implementation.ReviewLoaderDynamic;
import com.chdryra.android.reviewer.Database.Implementation.ReviewLoaderStatic;
import com.chdryra.android.reviewer.Database.Interfaces.FactoryReviewFromDataHolder;
import com.chdryra.android.reviewer.Database.Interfaces.ReviewLoader;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Factories.FactoryReviewNode;

/**
 * Created by: Rizwan Choudrey
 * On: 16/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewLoader {
    public ReviewLoader newStaticLoader(FactoryReviewFromDataHolder reviewBuilder, DataValidator validator) {
        return new ReviewLoaderStatic(reviewBuilder, validator);
    }

    public ReviewLoader newDynamicLoader(FactoryReviewNode nodeFactory) {
        return new ReviewLoaderDynamic(nodeFactory);
    }
}
