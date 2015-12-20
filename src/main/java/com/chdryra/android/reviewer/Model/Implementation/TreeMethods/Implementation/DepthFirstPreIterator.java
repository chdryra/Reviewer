package com.chdryra.android.reviewer.Model.Implementation.TreeMethods.Implementation;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.ReviewNode;
import com.chdryra.android.reviewer.Model.Interfaces.TreeMethods.ReviewTreeIterator;

import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * Created by: Rizwan Choudrey
 * On: 01/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DepthFirstPreIterator implements ReviewTreeIterator {
    private Stack<ReviewNode> mStack;

    public DepthFirstPreIterator(@NonNull ReviewNode root) {
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
        pushChildrenToStack(next);
        pushAnyAdditionalNodesToStack(next);
        return next;
    }

    private void pushChildrenToStack(ReviewNode node) {
        for(ReviewNode child : node.getChildren()) {
            push(child);
        }
    }

    protected void push(ReviewNode node) {
        mStack.push(node);
    }

    protected void pushAnyAdditionalNodesToStack(ReviewNode node) {

    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Remove is not supported");
    }
}
