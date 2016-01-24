/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.Implementation.TreeMethods.Implementation;

import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.ReviewNode;
import com.chdryra.android.reviewer.Model.Interfaces.TreeMethods.NodesTraverser;
import com.chdryra.android.reviewer.Model.Interfaces.TreeMethods.VisitorReviewNode;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by: Rizwan Choudrey
 * On: 30/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class NodesTraverserIterated implements NodesTraverser {
    Iterator<ReviewNode> mIterator;
    ArrayList<VisitorReviewNode> mVisitors;

    public NodesTraverserIterated(Iterator<ReviewNode> iterator) {
        mIterator = iterator;
        mVisitors = new ArrayList<>();
    }

    @Override
    public void addVisitor(VisitorReviewNode visitor) {
        mVisitors.add(visitor);
    }

    @Override
    public void traverse() {
        while(mIterator.hasNext()) {
            ReviewNode node = mIterator.next();
            for(VisitorReviewNode visitor : mVisitors) {
                node.acceptVisitor(visitor);
            }
        }
    }
}
