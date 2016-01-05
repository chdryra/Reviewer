package com.chdryra.android.reviewer.Model.Implementation.TreeMethods.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableCollection;
import com.chdryra.android.reviewer.Model.Factories.FactoryNodeTraverser;
import com.chdryra.android.reviewer.Model.Factories.FactoryVisitorReviewNode;
import com.chdryra.android.reviewer.Model.Implementation.TreeMethods.Interfaces.TreeFlattener;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.ReviewNode;
import com.chdryra.android.reviewer.Model.Interfaces.TreeMethods.NodesTraverser;
import com.chdryra.android.reviewer.Model.Interfaces.TreeMethods.VisitorReviewDataGetter;

/**
 * Created by: Rizwan Choudrey
 * On: 14/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class TreeFlattenerImpl implements TreeFlattener {
    private FactoryVisitorReviewNode mVisitorFactory;
    private FactoryNodeTraverser mTraverserFactory;

    public TreeFlattenerImpl(FactoryVisitorReviewNode visitorFactory,
                             FactoryNodeTraverser traverserFactory) {
        mVisitorFactory = visitorFactory;
        mTraverserFactory = traverserFactory;
    }

    @Override
    public IdableCollection<Review> flatten(ReviewNode node) {
        NodesTraverser traverser = mTraverserFactory.newTreeTraverser(node);
        VisitorReviewDataGetter<Review> getter = mVisitorFactory.newReviewsCollector();
        traverser.addVisitor(getter);
        traverser.traverse();
        return getter.getData();
    }
}
