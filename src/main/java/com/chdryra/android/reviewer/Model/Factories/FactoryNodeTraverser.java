package com.chdryra.android.reviewer.Model.Factories;

import com.chdryra.android.reviewer.Model.Implementation.TreeMethods.Implementation
        .DepthFirstPreIterator;
import com.chdryra.android.reviewer.Model.Implementation.TreeMethods.Implementation
        .NodesTraverserIterated;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.ReviewNode;
import com.chdryra.android.reviewer.Model.Interfaces.TreeMethods.NodesTraverser;

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
