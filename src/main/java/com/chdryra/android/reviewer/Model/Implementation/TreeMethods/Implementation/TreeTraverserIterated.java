package com.chdryra.android.reviewer.Model.Implementation.TreeMethods.Implementation;

import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.ReviewNode;
import com.chdryra.android.reviewer.Model.Interfaces.TreeMethods.ReviewTreeIterator;
import com.chdryra.android.reviewer.Model.Interfaces.TreeMethods.TreeTraverser;
import com.chdryra.android.reviewer.Model.Interfaces.TreeMethods.VisitorReviewNode;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 30/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class TreeTraverserIterated implements TreeTraverser {
    ReviewTreeIterator mIterator;
    ArrayList<VisitorReviewNode> mVisitors;

    public TreeTraverserIterated(ReviewTreeIterator iterator) {
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
