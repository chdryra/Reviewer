/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Model.TreeMethods.Factories;

import com.chdryra.android.startouch.Algorithms.DataSorting.RatingBucketer;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataRating;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.VisitorFactory;
import com.chdryra.android.startouch.Model.TreeMethods.Implementation.RatingBucketerImpl;
import com.chdryra.android.startouch.Model.TreeMethods.Implementation.VisitorDataBucketer;

/**
 * Created by: Rizwan Choudrey
 * On: 06/12/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class FactoryDataBucketer {
    private final FactoryNodeTraverser mTraverserFactory;
    private final FactoryVisitorReviewNode mVisitorFactory;

    public FactoryDataBucketer(FactoryNodeTraverser traverserFactory, FactoryVisitorReviewNode
            visitorFactory) {
        mTraverserFactory = traverserFactory;
        mVisitorFactory = visitorFactory;
    }

    public RatingBucketer newRatingsBucketer() {
        return new RatingBucketerImpl(mTraverserFactory, new VisitorFactory.BucketVisitor<Float, DataRating>() {
            @Override
            public VisitorDataBucketer<Float, DataRating> newVisitor() {
                return mVisitorFactory.newRatingValueBucketer();
            }
        });
    }
}
