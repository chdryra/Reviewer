package com.chdryra.android.reviewer.Models.ReviewsModel.Factories;

import com.chdryra.android.reviewer.Models.ReviewsModel.Implementation.MdReviewId;
import com.chdryra.android.reviewer.Models.ReviewsModel.Implementation.ReviewTreeNode;
import com.chdryra.android.reviewer.Models.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Models.ReviewsModel.Interfaces.ReviewNodeComponent;
import com.chdryra.android.reviewer.Models.ReviewsModel.Interfaces.TreeDataGetter;
import com.chdryra.android.reviewer.TreeMethods.Factories.FactoryVisitorReviewNode;

/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewNodeComponent {
    private FactoryVisitorReviewNode mVisitorFactory;
    private FactoryTreeDataGetter mGetterFactory;
    private FactoryReviewNode mNodeFactory;

    public FactoryReviewNodeComponent(FactoryVisitorReviewNode visitorFactory,
                                      FactoryTreeDataGetter getterFactory) {
        mVisitorFactory = visitorFactory;
        mGetterFactory = getterFactory;
        mNodeFactory = new FactoryReviewNode(this);
    }

    public ReviewNodeComponent createReviewNodeComponent(Review review, boolean isAverage) {
        MdReviewId id = new MdReviewId(review.getReviewId());
        TreeDataGetter getter = mGetterFactory.newTreeGetter();
        return new ReviewTreeNode(id, review, isAverage, getter, mVisitorFactory, mNodeFactory);
    }
}
