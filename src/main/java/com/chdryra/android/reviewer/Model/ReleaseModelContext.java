/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model;

import com.chdryra.android.reviewer.ApplicationContexts.Implementation.ModelContextBasic;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.Model.Factories.FactoryNodeTraverser;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.Factories.FactoryTagsManager;
import com.chdryra.android.reviewer.Model.Factories.FactoryVisitorReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryBinders;
import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryDataCollector;
import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryMdConverter;
import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.MdConverters.ConverterMd;

/**
 * Created by: Rizwan Choudrey
 * On: 04/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReleaseModelContext extends ModelContextBasic {

    public ReleaseModelContext(DataValidator validator) {
        setTagsManager(new FactoryTagsManager().newTagsManager());

        setVisitorsFactory(new FactoryVisitorReviewNode());

        setTreeTraversersFactory(new FactoryNodeTraverser());

        ConverterMd converter = new FactoryMdConverter().newMdConverter();
        setBindersFactory(new FactoryBinders());
        FactoryReviewNode nodeFactory = new FactoryReviewNode(getBindersFactory(),
                new FactoryDataCollector());
        FactoryReviews reviews = new FactoryReviews(nodeFactory, converter, validator);
        setReviewsFactory(reviews);
    }
}
