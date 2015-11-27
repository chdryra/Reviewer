package com.chdryra.android.reviewer.Model.Interfaces;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.VerboseDataReview;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.VerboseIdableCollection;

/**
 * Created by: Rizwan Choudrey
 * On: 31/08/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewsFeed extends ReviewsRepository{
    DataAuthor getAuthor();

    ItemTagCollection getTags(String reviewId);

    Review getReview(VerboseDataReview datum);

    Review createMetaReview(VerboseIdableCollection data, String subject);

    Review asMetaReview(VerboseDataReview datum, String subject);

    Review createFlattenedMetaReview(VerboseIdableCollection data, String subject);

    @Override
    Review getReview(String reviewId);

    @Override
    Iterable<Review> getReviews();

    @Override
    TagsManager getTagsManager();

    @Override
    void registerObserver(ReviewsRepositoryObserver observer);

    @Override
    void unregisterObserver(ReviewsRepositoryObserver observer);
}
