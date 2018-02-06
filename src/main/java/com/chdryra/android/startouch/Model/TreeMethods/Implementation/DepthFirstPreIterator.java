/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Model.TreeMethods.Implementation;

import android.support.annotation.NonNull;

import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewNode;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * Created by: Rizwan Choudrey
 * On: 01/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DepthFirstPreIterator implements Iterator<ReviewNode> {
    private final Stack<ReviewNode> mStack;

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
        pushChildren(next);
        return next;
    }

    private void pushChildren(ReviewNode node) {
        for(ReviewNode child : node.getChildren()) {
            push(child);
        }
    }

    private void push(ReviewNode node) {
        mStack.push(node);
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Remove is not supported");
    }
}
