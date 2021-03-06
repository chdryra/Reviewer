/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Model.ReviewsModel.Factories;

import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.IdableDataCollection;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataReview;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.IdableCollection;
import com.chdryra.android.startouch.Model.ReviewsModel.Implementation.NodeDefault;
import com.chdryra.android.startouch.Model.ReviewsModel.Implementation.NodeLeaf;
import com.chdryra.android.startouch.Model.ReviewsModel.Implementation.ReviewTree;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewNodeComponent;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewReference;

/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewNode {
    private final FactoryDataReference mReferenceFactory;

    public FactoryReviewNode(FactoryDataReference referenceFactory) {
        mReferenceFactory = referenceFactory;
    }

    public ReviewNodeComponent createLeafNode(ReviewReference review) {
        return new NodeLeaf(review, mReferenceFactory.getReferenceFactory());
    }

    public ReviewNodeComponent createComponent(DataReview meta) {
        return new NodeDefault(meta, mReferenceFactory);
    }

    public ReviewNode freezeNode(ReviewNodeComponent node) {
        return new ReviewTree(node);
    }

    public ReviewNodeComponent createMetaTree(DataReview meta,
                                              Iterable<ReviewReference> reviews) {
        ReviewNodeComponent parent = createComponent(meta);
        for (ReviewReference review : reviews) {
            parent.addChild(createLeafNode(review));
        }

        return parent;
    }

    public ReviewNodeComponent createMetaTree(ReviewReference review) {
        IdableCollection<ReviewReference> single = new IdableDataCollection<>();
        single.add(review);

        return createMetaTree(review, single);
    }
}
