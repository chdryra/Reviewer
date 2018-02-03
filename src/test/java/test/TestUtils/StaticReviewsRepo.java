/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.TestUtils;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.NamedAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.IdableCollection;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Persistence.Implementation.RepoResult;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepo;
import com.chdryra.android.reviewer.Persistence.Interfaces.RepoCallback;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepositoryObserver;
import com.chdryra.android.mygenerallibrary.TagsModel.Interfaces.TagsManager;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 17/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class StaticReviewsRepo implements ReviewsRepo {
    private IdableCollection<Review> mReviews;
    private TagsManager mTagsManager;

    public StaticReviewsRepo(IdableCollection<Review> reviews, TagsManager tagsManager) {
        mReviews = reviews;
        mTagsManager = tagsManager;
    }

    @Override
    public void getReview(ReviewId reviewId, RepoCallback callback) {
        Review result = null;
        for(Review review : mReviews) {
            if(review.getReviewId().equals(reviewId)) {
                result = review;
                break;
            }
        }

        CallbackMessage message = result == null ? CallbackMessage.error(reviewId + " not found") :
                CallbackMessage.ok();
        callback.onRepoCallback(new RepoResult(result, message));
    }

    @Override
    public void getReviews(RepoCallback callback) {
        callback.onRepoCallback(new RepoResult(mReviews));
    }

    @Override
    public void getReviews(NamedAuthor author, RepoCallback callback) {
        ArrayList<Review> result = new ArrayList<>();
        for(Review review : mReviews) {
            if(review.getAuthorId().getAuthorId().equals(author.getAuthorId())) result.add(review);
        }
        callback.onRepoCallback(new RepoResult(author, result));
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