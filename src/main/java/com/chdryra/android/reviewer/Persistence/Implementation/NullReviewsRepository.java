/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Persistence.Implementation;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepositoryObserver;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 16/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class NullReviewsRepository implements ReviewsRepository{
    private TagsManager mMangaer;

    public NullReviewsRepository(TagsManager mangaer) {
        mMangaer = mangaer;
    }

    @Override
    public void getReviews(RepositoryCallback callback) {
        callback.onRepositoryCallback(new RepositoryResult(new ArrayList<Review>()));
    }

    @Override
    public void getReferences(RepositoryCallback callback) {
        callback.onRepositoryCallback(new RepositoryResult(new ArrayList<ReviewReference>(), null));
    }

    @Override
    public void getReview(ReviewId reviewId, RepositoryCallback callback) {
        callback.onRepositoryCallback(new RepositoryResult(reviewId, CallbackMessage.error("Review not found")));
    }

    @Override
    public void getReference(ReviewId reviewId, RepositoryCallback callback) {
        callback.onRepositoryCallback(new RepositoryResult(new NullReviewReference()));
    }

    @Override
    public void getReviews(DataAuthor author, RepositoryCallback callback) {
        callback.onRepositoryCallback(new RepositoryResult(author, new ArrayList<Review>()));
    }

    @Override
    public ReviewsRepository getReviews(DataAuthor author) {
        return this;
    }

    @Override
    public void getReferences(DataAuthor author, RepositoryCallback callback) {
        callback.onRepositoryCallback(new RepositoryResult(new ArrayList<ReviewReference>(), author));
    }

    @Override
    public TagsManager getTagsManager() {
        return mMangaer;
    }

    @Override
    public void registerObserver(ReviewsRepositoryObserver observer) {

    }

    @Override
    public void unregisterObserver(ReviewsRepositoryObserver observer) {

    }
}
