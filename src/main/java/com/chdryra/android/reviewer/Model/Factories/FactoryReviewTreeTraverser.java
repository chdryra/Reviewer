package com.chdryra.android.reviewer.Model.Factories;

import com.chdryra.android.reviewer.Model.Implementation.TreeMethods.Implementation.DepthFirstPreExpanderIterator;
import com.chdryra.android.reviewer.Model.Implementation.TreeMethods.Implementation.DepthFirstPreIterator;
import com.chdryra.android.reviewer.Model.Implementation.TreeMethods.Implementation.TreeTraverserIterated;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.ReviewNode;
import com.chdryra.android.reviewer.Model.Interfaces.TreeMethods.ReviewTreeIterator;
import com.chdryra.android.reviewer.Model.Interfaces.TreeMethods.TreeTraverser;

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
