package com.chdryra.android.reviewer.Model.Interfaces;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.IdableCollection;

/**
 * Created by: Rizwan Choudrey
 * On: 25/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface VisitorReviewsGetter extends VisitorReviewNode {
    //Overridden
    @Override
    void visit(ReviewNode node);

    //public methods
    IdableCollection<Review> getReviews();
}
