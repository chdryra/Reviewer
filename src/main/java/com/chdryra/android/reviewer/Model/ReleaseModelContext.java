/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model;

import com.chdryra.android.reviewer.ApplicationContexts.Implementation.ModelContextBasic;
import com.chdryra.android.reviewer.Model.Factories.FactoryNodeTraverser;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.Factories.FactoryTagsManager;
import com.chdryra.android.reviewer.Model.Factories.FactoryVisitorReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryMdConverter;
import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryMdReference;

/**
 * Created by: Rizwan Choudrey
 * On: 04/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReleaseModelContext extends ModelContextBasic {
    public ReleaseModelContext() {
        setTagsManager(new FactoryTagsManager().newTagsManager());

        FactoryReviews reviews = new FactoryReviews(new FactoryMdConverter().newMdConverter(),
                new FactoryMdReference(new FactoryNodeTraverser(), new FactoryVisitorReviewNode()));
        setReviewsFactory(reviews);
    }
}
