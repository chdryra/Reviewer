package com.chdryra.android.reviewer.Model.Interfaces;

/**
 * Created by: Rizwan Choudrey
 * On: 30/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface TreeTraverser {
    void traverse();

    void addVisitor(VisitorReviewNode visitor);
}
