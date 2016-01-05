package com.chdryra.android.reviewer.Model.Implementation.TreeMethods.Interfaces;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableCollection;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.ReviewNode;

/**
 * Created by: Rizwan Choudrey
 * On: 14/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface TreeFlattener {
    IdableCollection<Review> flatten(ReviewNode node);
}
