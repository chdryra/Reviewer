package com.chdryra.android.reviewer.Model.Factories;

import com.chdryra.android.reviewer.Model.Implementation.TreeMethods.TreeTraverserFull;
import com.chdryra.android.reviewer.Model.Implementation.TreeMethods.TreeTraverserLeaves;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.Interfaces.TreeTraverser;
import com.chdryra.android.reviewer.Model.Interfaces.VisitorReviewNode;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 30/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewTreeTraverser {
    public TreeTraverser newLeavesTraverser(ReviewNode root, VisitorReviewNode visitor) {
        ArrayList<VisitorReviewNode> visitors = new ArrayList<>();
        visitors.add(visitor);
        return newLeavesTraverser(root, visitors);
    }

    public TreeTraverser newLeavesTraverser(ReviewNode root, ArrayList<VisitorReviewNode> visitors) {
        return new TreeTraverserLeaves(root, visitors);
    }

    public TreeTraverser newTraverser(ReviewNode root, VisitorReviewNode visitor) {
        ArrayList<VisitorReviewNode> visitors = new ArrayList<>();
        visitors.add(visitor);
        return newTraverser(root, visitors);
    }

    public TreeTraverser newTraverser(ReviewNode root, ArrayList<VisitorReviewNode> visitors) {
        return new TreeTraverserFull(root, visitors);
    }
}
