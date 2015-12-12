package com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.VerboseDataReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.VerboseIdableItems;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.ReviewNode;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.ItemTagCollection;

/**
 * Created by: Rizwan Choudrey
 * On: 31/08/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewsFeed extends ReviewsRepository{
    DataAuthor getAuthor();

    ItemTagCollection getTags(ReviewId reviewId);

    Review getReview(VerboseDataReview datum);

    ReviewNode getMetaReview(VerboseIdableItems data, String subject);

    ReviewNode asMetaReview(VerboseDataReview datum, String subject);

    ReviewNode getFlattenedMetaReview(VerboseIdableItems data, String subject);
}
