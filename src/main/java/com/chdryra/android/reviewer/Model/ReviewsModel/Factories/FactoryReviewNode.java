/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Factories;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataReviewInfo;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableCollection;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.MdDataCollection;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.NodeInternal;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.NodeLeaf;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.ReviewTree;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNodeComponent;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewNode {
    private FactoryReviews mReviewsFactory;
    private FactoryMdReference mReferenceFactory;

    public FactoryReviewNode(FactoryReviews reviewFactory,
                             FactoryMdReference referenceFactory) {
        mReviewsFactory = reviewFactory;
        mReferenceFactory = referenceFactory;
    }

    public FactoryReviews getReviewsFactory() {
        return mReviewsFactory;
    }

    public ReviewNodeComponent createLeafNode(ReviewReference review) {
        return new NodeLeaf(review);
    }

    public ReviewNodeComponent createComponent(DataReviewInfo meta) {
        return new NodeInternal(meta, mReferenceFactory);
    }

    public ReviewNode freezeNode(ReviewNodeComponent node) {
        return new ReviewTree(node);
    }

    public ReviewNodeComponent createMetaTree(DataReviewInfo meta,
                                               Iterable<ReviewReference> reviews) {
        ArrayList<ReviewNodeComponent> leaves = new ArrayList<>();
        for (ReviewReference review : reviews) {
            leaves.add(createLeafNode(review));
        }
        ReviewNodeComponent parent = createComponent(meta);
        parent.addChildren(leaves);

        return parent;
    }

    public ReviewNodeComponent createMetaTree(ReviewReference review) {
        IdableCollection<ReviewReference> single = new MdDataCollection<>();
        single.add(review);

        return createMetaTree(review, single);
    }
}
