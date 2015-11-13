package com.chdryra.android.reviewer.ReviewsProviderModel;

import com.chdryra.android.reviewer.Interfaces.Data.VerboseDataReview;
import com.chdryra.android.reviewer.Interfaces.Data.IdableCollection;
import com.chdryra.android.reviewer.Interfaces.Data.VerboseIdableCollection;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewStructure.Review;
import com.chdryra.android.reviewer.Models.TagsModel.ItemTagCollection;
import com.chdryra.android.reviewer.Models.TagsModel.TagsManager;
import com.chdryra.android.reviewer.Models.UserModel.Author;

/**
 * Created by: Rizwan Choudrey
 * On: 31/08/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewsProvider extends ReviewsRepository{
    Author getAuthor();

    ItemTagCollection getTags(String reviewId);

    Review getReview(VerboseDataReview datum);

    <T extends VerboseDataReview> Review createMetaReview(VerboseIdableCollection<T> data, String subject);

    Review createMetaReview(VerboseDataReview datum, String subject);

    <T extends VerboseDataReview> Review createFlattenedMetaReview(VerboseIdableCollection<T> data, String subject);

    @Override
    Review getReview(String reviewId);

    @Override
    IdableCollection<Review> getReviews();

    @Override
    TagsManager getTagsManager();

    @Override
    void registerObserver(ReviewsProviderObserver observer);

    @Override
    void unregisterObserver(ReviewsProviderObserver observer);
}
