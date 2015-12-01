package com.chdryra.android.reviewer.Model.Implementation.TreeMethods;

import com.chdryra.android.reviewer.Model.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.Interfaces.TreeTraverser;
import com.chdryra.android.reviewer.Model.Interfaces.VisitorReviewNode;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 30/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class TreeTraverserFull implements TreeTraverser {
    ReviewNode mRoot;
    ArrayList<VisitorReviewNode> mVisitors;

    public TreeTraverserFull(ReviewNode root, ArrayList<VisitorReviewNode> visitors) {
        mRoot = root;
        mVisitors = visitors;
    }

    public ArrayList<VisitorReviewNode> getVisitors() {
        return mVisitors;
    }

    @Override
    public void traverse() {
        traverse(mRoot);
    }

    private void traverse(ReviewNode root) {
        for(VisitorReviewNode visitor : mVisitors) {
            visit(root, visitor);
        }
        expandAndVisit(root);
        visitDescendents(root);
    }

    private void visit(ReviewNode node, VisitorReviewNode visitor) {
        node.acceptVisitor(visitor);
    }

    private void expandAndVisit(ReviewNode node) {
        if(node.isExpandable()) traverse(node.expand());
    }


    private void visitDescendents(ReviewNode node) {
        for (ReviewNode child : node.getChildren()) {
            traverse(child);
        }
    }
}
