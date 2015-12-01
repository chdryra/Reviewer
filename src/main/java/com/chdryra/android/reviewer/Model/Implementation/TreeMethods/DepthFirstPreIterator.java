package com.chdryra.android.reviewer.Model.Implementation.TreeMethods;

import com.chdryra.android.reviewer.Model.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewTreeIterator;

import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * Created by: Rizwan Choudrey
 * On: 01/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DepthFirstPreIterator implements ReviewTreeIterator {
    private Stack<ReviewNode> mStack;

    public DepthFirstPreIterator(ReviewNode root) {
        mStack = new Stack<>();
        mStack.push(root);
    }

    @Override
    public boolean hasNext() {
        return !mStack.isEmpty();
    }

    @Override
    public ReviewNode next() {
        if(!hasNext()) throw new NoSuchElementException("No nodes left");
        ReviewNode next = mStack.pop();
        mStack.addAll(next.getChildren());
        return next;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Remove is not supported");
    }
}
