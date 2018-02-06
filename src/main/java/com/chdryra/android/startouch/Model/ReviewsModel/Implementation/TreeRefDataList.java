/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Model.ReviewsModel.Implementation;

import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.RefDataList;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.ReviewItemReference;
import com.chdryra.android.startouch.Model.ReviewsModel.Factories.FactoryMdReference;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.VisitorFactory;
import com.chdryra.android.startouch.Model.TreeMethods.Factories.FactoryNodeTraverser;

/**
 * Created by: Rizwan Choudrey
 * On: 29/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class TreeRefDataList<Value extends HasReviewId>
        extends TreeListReferences<Value, ReviewItemReference<Value>, RefDataList<Value>>
        implements RefDataList<Value> {
    public TreeRefDataList(ReviewNode root, FactoryMdReference referenceFactory,
                           FactoryNodeTraverser traverserFactory, VisitorFactory
                                   .ListVisitor<RefDataList<Value>> visitorFactory) {
        super(root, referenceFactory, traverserFactory, visitorFactory);
    }
}
