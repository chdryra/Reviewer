package com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.VerboseDataReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.VerboseIdableCollection;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.ReviewNode;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.ItemTagCollection;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.TagsManager;

/**
 * Created by: Rizwan Choudrey
 * On: 31/08/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewsFeedMutable extends ReviewsRepositoryMutable, ReviewsFeed {
    @Override
    DataAuthor getAuthor();

    @Override
    ItemTagCollection getTags(String reviewId);

    @Override
    Review getReview(VerboseDataReview datum);

    @Override
    ReviewNode getMetaReview(VerboseIdableCollection data, String subject);

    @Override
    ReviewNode asMetaReview(VerboseDataReview datum, String subject);

    @Override
    ReviewNode getFlattenedMetaReview(VerboseIdableCollection data, String subject);

    @Override
    void addReview(Review review);

    @Override
    void removeReview(String reviewId);

    @Override
    Review getReview(String id);

    @Override
    Iterable<Review> getReviews();

    @Override
    TagsManager getTagsManager();

    @Override
    void registerObserver(ReviewsRepositoryObserver observer);

    @Override
    void unregisterObserver(ReviewsRepositoryObserver observer);
}
