/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Model.TreeMethods.Implementation;

import com.chdryra.android.startouch.Algorithms.DataSorting.RatingBucketer;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataRating;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.VisitorFactory;
import com.chdryra.android.startouch.Model.TreeMethods.Factories.FactoryNodeTraverser;

/**
 * Created by: Rizwan Choudrey
 * On: 06/12/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class RatingBucketerImpl extends TraversalBucketer<Float, DataRating> implements RatingBucketer {
    public RatingBucketerImpl(FactoryNodeTraverser traverserFactory, VisitorFactory
            .BucketVisitor<Float, DataRating> visitorFactory) {
        super(traverserFactory, visitorFactory);
    }
}
