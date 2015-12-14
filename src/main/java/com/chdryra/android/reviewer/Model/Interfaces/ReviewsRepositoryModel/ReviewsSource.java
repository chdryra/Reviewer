package com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.VerboseDataReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.VerboseIdableItems;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.ReviewNode;

/**
 * Created by: Rizwan Choudrey
 * On: 14/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewsSource extends ReviewsRepository{
    @Nullable
    ReviewNode asMetaReview(ReviewId id);

    @Nullable
    Review getReview(VerboseDataReview datum);

    @Nullable
    ReviewNode getMetaReview(VerboseIdableItems data, String subject);

    @Nullable
    ReviewNode asMetaReview(VerboseDataReview datum, String subject);

    @Nullable
    ReviewNode getFlattenedMetaReview(VerboseIdableItems data, String subject);
}
