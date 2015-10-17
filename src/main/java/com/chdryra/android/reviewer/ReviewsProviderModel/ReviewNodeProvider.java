package com.chdryra.android.reviewer.ReviewsProviderModel;

import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.Model.TagsModel.TagsManager;
import com.chdryra.android.reviewer.Model.TreeMethods.VisitorReviewsGetter;

/**
 * Created by: Rizwan Choudrey
 * On: 16/10/2015
 * Email: rizwan.choudrey@gmail.com
 *
 * Currently just really a wrapper for a {@link ReviewNode}.
 */
public class ReviewNodeProvider extends StaticReviewsProvider{
    public ReviewNodeProvider(ReviewNode node, TagsManager tagsManager) {
        super(VisitorReviewsGetter.flatten(node), tagsManager);
    }
}
