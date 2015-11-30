package com.chdryra.android.reviewer.Model.Implementation.TreeMethods;

import com.chdryra.android.reviewer.Model.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.Interfaces.VisitorReviewNode;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 30/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class TreeTraverserLeaves extends TreeTraverserFull{
    public TreeTraverserLeaves(ReviewNode root, ArrayList<VisitorReviewNode> visitors) {
        super(root, visitors);
    }
    private void visit(ReviewNode node, VisitorReviewNode visitor) {
        if(!isExpandable(node) && node.getChildren().size() == 0) node.acceptVisitor(visitor);
    }
}
