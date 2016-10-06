/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model;

import com.chdryra.android.reviewer.ApplicationContexts.Implementation.ModelContextBasic;
import com.chdryra.android.reviewer.DataDefinitions.References.Factories.FactoryReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryMdReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.TagsModel.Implementation.TagsManagerImpl;
import com.chdryra.android.reviewer.Model.TreeMethods.Factories.FactoryNodeTraverser;
import com.chdryra.android.reviewer.Model.TreeMethods.Factories.FactoryVisitorReviewNode;

/**
 * Created by: Rizwan Choudrey
 * On: 04/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReleaseModelContext extends ModelContextBasic {
    public ReleaseModelContext() {
        setTagsManager(new TagsManagerImpl());

        setReferenceFactory(new FactoryReference());

        FactoryMdReference mdReferenceFactory = new FactoryMdReference(getReferenceFactory(),
                new FactoryNodeTraverser(), new FactoryVisitorReviewNode());

        FactoryReviews reviews = new FactoryReviews(mdReferenceFactory);

        setReviewsFactory(reviews);
    }
}
