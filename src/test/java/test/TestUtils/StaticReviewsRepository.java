/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.TestUtils;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableCollection;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Persistence.Interfaces.CallbackRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepositoryObserver;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;

/**
 * Created by: Rizwan Choudrey
 * On: 17/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class StaticReviewsRepository implements ReviewsRepository {
    private IdableCollection<Review> mReviews;
    private TagsManager mTagsManager;

    public StaticReviewsRepository(IdableCollection<Review> reviews, TagsManager tagsManager) {
        mReviews = reviews;
        mTagsManager = tagsManager;
    }

    @Override
    public void getReview(ReviewId id, CallbackRepository callback) {
        Review result = null;
        for(Review review : mReviews) {
            if(review.getReviewId().equals(id)) {
                result = review;
                break;
            }
        }

        callback.onFetchedFromRepo(result, CallbackMessage.ok("Fetched"));
    }

    @Override
    public void getReviews(CallbackRepository callback) {
        callback.onFetchedFromRepo(mReviews, CallbackMessage.ok("Fetched"));
    }

    @Override
    public TagsManager getTagsManager() {
        return mTagsManager;
    }

    @Override
    public void registerObserver(ReviewsRepositoryObserver observer) {

    }

    @Override
    public void unregisterObserver(ReviewsRepositoryObserver observer) {

    }
}
