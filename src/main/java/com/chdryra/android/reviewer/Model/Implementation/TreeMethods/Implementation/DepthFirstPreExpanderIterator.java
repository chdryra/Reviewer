package com.chdryra.android.reviewer.Model.Implementation.TreeMethods.Implementation;

import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.ReviewNode;

/**
 * Created by: Rizwan Choudrey
 * On: 01/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DepthFirstPreExpanderIterator extends DepthFirstPreIterator {
    public DepthFirstPreExpanderIterator(ReviewNode root) {
        super(root);
    }

    @Override
    protected void pushAnyAdditionalNodesToStack(ReviewNode node) {
        if(node.isExpandable()) push(node.expand());
    }
}
