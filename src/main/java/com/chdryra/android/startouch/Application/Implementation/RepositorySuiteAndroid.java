/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Application.Implementation;

import com.chdryra.android.startouch.Application.Interfaces.RepositorySuite;
import com.chdryra.android.startouch.Authentication.Interfaces.SocialProfileRef;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.startouch.NetworkServices.ReviewDeleting.FactoryReviewDeleter;
import com.chdryra.android.startouch.NetworkServices.ReviewDeleting.ReviewDeleter;
import com.chdryra.android.startouch.NetworkServices.ReviewPublishing.Interfaces.ReviewPublisher;
import com.chdryra.android.startouch.Persistence.Factories.FactoryReviewsRepo;
import com.chdryra.android.startouch.Persistence.Interfaces.AuthorsRepo;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewNodeRepo;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewsRepoReadable;

/**
 * Created by: Rizwan Choudrey
 * On: 03/10/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class RepositorySuiteAndroid implements RepositorySuite {
    private final AuthorsRepo mAuthorsRepo;
    private final ReviewNodeRepo mReviewsRepo;
    private final FactoryReviewsRepo mRepoFactory;
    private final FactoryReviewDeleter mDeleterFactory;
    private final ReviewPublisher mPublisher;

    public RepositorySuiteAndroid(AuthorsRepo authorsRepo,
                                  ReviewNodeRepo reviewsRepo,
                                  FactoryReviewsRepo repoFactory,
                                  FactoryReviewDeleter deleterFactory,
                                  ReviewPublisher publisher) {
        mReviewsRepo = reviewsRepo;
        mAuthorsRepo = authorsRepo;
        mRepoFactory = repoFactory;
        mDeleterFactory = deleterFactory;
        mPublisher = publisher;
    }

    @Override
    public AuthorsRepo getAuthors() {
        return mAuthorsRepo;
    }

    @Override
    public ReviewNodeRepo getReviews() {
        return mReviewsRepo;
    }

    @Override
    public ReviewsRepoReadable getFeed(SocialProfileRef profile) {
        return mRepoFactory.newFeed(profile, getReviews());
    }

    @Override
    public ReviewDeleter newReviewDeleter(ReviewId id) {
        return mDeleterFactory.newDeleter(id);
    }

    @Override
    public ReviewPublisher getReviewPublisher() {
        return mPublisher;
    }
}
