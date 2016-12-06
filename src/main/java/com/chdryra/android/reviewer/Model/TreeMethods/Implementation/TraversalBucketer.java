/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.TreeMethods.Implementation;

import com.chdryra.android.mygenerallibrary.OtherUtils.TagKeyGenerator;
import com.chdryra.android.reviewer.Algorithms.DataSorting.DataBucketer;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.VisitorFactory;
import com.chdryra.android.reviewer.Model.TreeMethods.Factories.FactoryNodeTraverser;
import com.chdryra.android.reviewer.Model.TreeMethods.Interfaces.TreeTraverser;
import com.chdryra.android.reviewer.Model.TreeMethods.Interfaces.VisitorReviewNode;

import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 05/12/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class TraversalBucketer<BucketingValue, Data extends HasReviewId> implements DataBucketer<BucketingValue, Data> {
    private static final String VISITOR
            = TagKeyGenerator.getTag(TraversalBucketer.class, "Visitor");

    private final FactoryNodeTraverser mTraverserFactory;
    private final VisitorFactory.BucketVisitor<BucketingValue, Data> mVisitorFactory;

    public TraversalBucketer(FactoryNodeTraverser traverserFactory,
                             VisitorFactory.BucketVisitor<BucketingValue, Data> visitorFactory) {
        mTraverserFactory = traverserFactory;
        mVisitorFactory = visitorFactory;
    }

    @Override
    public void bucketData(ReviewNode root, final DataBucketerCallback<BucketingValue, Data> callback) {
        TreeTraverser traverser = mTraverserFactory.newTreeTraverser(root);
        final VisitorDataBucketer<BucketingValue, Data> visitor = mVisitorFactory.newVisitor();
        traverser.addVisitor(VISITOR, visitor);
        traverser.traverse(new TreeTraverser.TraversalCallback() {
            @Override
            public void onTraversed(Map<String, VisitorReviewNode> visitors) {
                callback.onDataBucketed(visitor.getDistribution());
            }
        });
    }
}
