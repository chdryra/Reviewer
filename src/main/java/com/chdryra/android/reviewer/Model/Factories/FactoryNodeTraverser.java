/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.Factories;

import com.chdryra.android.reviewer.Model.TreeMethods.Implementation
        .DepthFirstPreIterator;
import com.chdryra.android.reviewer.Model.TreeMethods.Implementation
        .NodesTraverserIterated;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.TreeMethods.Interfaces.NodesTraverser;

import java.util.Iterator;

/**
 * Created by: Rizwan Choudrey
 * On: 30/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryNodeTraverser {
    public NodesTraverser newTreeTraverser(ReviewNode root) {
        return newTraverser(new DepthFirstPreIterator(root));
    }

    private NodesTraverser newTraverser(Iterator<ReviewNode> iterator) {
        return new NodesTraverserIterated(iterator);
    }
}
