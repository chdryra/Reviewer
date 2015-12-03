package com.chdryra.android.reviewer.Model.Factories;

import com.chdryra.android.reviewer.Model.Implementation.TreeMethods.DepthFirstPreExpanderIterator;
import com.chdryra.android.reviewer.Model.Implementation.TreeMethods.DepthFirstPreIterator;
import com.chdryra.android.reviewer.Model.Implementation.TreeMethods.TreeTraverserIterated;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewTreeIterator;
import com.chdryra.android.reviewer.Model.Interfaces.TreeTraverser;

/**
 * Created by: Rizwan Choudrey
 * On: 30/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewTreeTraverser {
    public TreeTraverser newTreeTraverser(ReviewNode root) {
        return newTraverser(new DepthFirstPreIterator(root));
    }

    public TreeTraverser newExpandedTreeTraverser(ReviewNode root) {
        return newTraverser(new DepthFirstPreExpanderIterator(root));
    }

    private TreeTraverser newTraverser(ReviewTreeIterator iterator) {
        return new TreeTraverserIterated(iterator);
    }
}
