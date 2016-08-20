/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.TreeMethods.Factories;

import com.chdryra.android.reviewer.Model.TreeMethods.Implementation
        .DepthFirstPreIterator;
import com.chdryra.android.reviewer.Model.TreeMethods.Implementation.TreeTraverserIterated;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.TreeMethods.Interfaces.TreeTraverser;

import java.util.Iterator;

/**
 * Created by: Rizwan Choudrey
 * On: 30/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryNodeTraverser {
    public TreeTraverser newTreeTraverser(ReviewNode root) {
        return newTraverser(new DepthFirstPreIterator(root));
    }

    private TreeTraverser newTraverser(Iterator<ReviewNode> iterator) {
        return new TreeTraverserIterated(iterator);
    }
}
