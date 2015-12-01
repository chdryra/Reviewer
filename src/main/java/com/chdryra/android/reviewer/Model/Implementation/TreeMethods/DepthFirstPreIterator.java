package com.chdryra.android.reviewer.Model.Implementation.TreeMethods;

import com.chdryra.android.reviewer.Model.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewTreeIterator;

import java.util.Stack;

/**
 * Created by: Rizwan Choudrey
 * On: 01/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DepthFirstPreIterator implements ReviewTreeIterator {
    private Stack<ReviewNode> mStack;
    private ReviewNode mRoot;

    public DepthFirstPreIterator(ReviewNode root) {
        mRoot = root;
        mStack = new Stack<>();
        pushChildrenToStack(mRoot);
    }

    private void pushChildrenToStack(ReviewNode node) {
        for(ReviewNode child : node.getChildren()) {
            mStack.push(child);
        }
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public ReviewNode next() {
        return null;
    }

    @Override
    public void remove() {

    }
}
