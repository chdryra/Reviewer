/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Model;

import com.chdryra.android.startouch.ApplicationContexts.Implementation.ModelContextBasic;
import com.chdryra.android.startouch.DataDefinitions.References.Factories.FactoryReferences;
import com.chdryra.android.startouch.Model.ReviewsModel.Factories.FactoryDataReference;
import com.chdryra.android.startouch.Model.ReviewsModel.Factories.FactoryReviewNode;
import com.chdryra.android.startouch.Model.ReviewsModel.Factories.FactoryReviews;
import com.chdryra.android.startouch.Model.TreeMethods.Factories.FactoryDataBucketer;
import com.chdryra.android.startouch.Model.TreeMethods.Factories.FactoryNodeTraverser;
import com.chdryra.android.startouch.Model.TreeMethods.Factories.FactoryVisitorReviewNode;

/**
 * Created by: Rizwan Choudrey
 * On: 04/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReleaseModelContext extends ModelContextBasic {
    public ReleaseModelContext() {
        setReferenceFactory(new FactoryReferences());

        FactoryVisitorReviewNode visitorFactory = new FactoryVisitorReviewNode();
        FactoryNodeTraverser traverserFactory = new FactoryNodeTraverser();

        FactoryDataReference mdReferenceFactory = new FactoryDataReference(getReferencesFactory(),
                traverserFactory, visitorFactory);

        FactoryReviews reviews = new FactoryReviews(new FactoryReviewNode(mdReferenceFactory),
                mdReferenceFactory);

        setReviewsFactory(reviews);

        setBucketerFactory(new FactoryDataBucketer(traverserFactory, visitorFactory));
    }
}
