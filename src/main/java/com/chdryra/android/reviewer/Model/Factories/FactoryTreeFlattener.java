package com.chdryra.android.reviewer.Model.Factories;

import com.chdryra.android.reviewer.Model.Implementation.TreeMethods.Interfaces.TreeFlattener;
import com.chdryra.android.reviewer.Model.Implementation.TreeMethods.Implementation
        .TreeFlattenerImpl;

/**
 * Created by: Rizwan Choudrey
 * On: 14/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryTreeFlattener {
    FactoryVisitorReviewNode mFactoryVisitorReviewNode;
    FactoryReviewTreeTraverser mFactoryReviewTreeTraverser;

    public FactoryTreeFlattener(FactoryVisitorReviewNode factoryVisitorReviewNode,
                                FactoryReviewTreeTraverser factoryReviewTreeTraverser) {
        mFactoryVisitorReviewNode = factoryVisitorReviewNode;
        mFactoryReviewTreeTraverser = factoryReviewTreeTraverser;
    }

    public TreeFlattener newFlattener() {
        return new TreeFlattenerImpl(mFactoryVisitorReviewNode, mFactoryReviewTreeTraverser);
    }
}
