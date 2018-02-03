/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Application.Implementation;

import com.chdryra.android.reviewer.Application.Interfaces.RepositorySuite;
import com.chdryra.android.reviewer.Authentication.Interfaces.ProfileSocial;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.NetworkServices.ReviewDeleting.Factories.FactoryReviewDeleter;
import com.chdryra.android.reviewer.NetworkServices.ReviewDeleting.Interfaces.ReviewDeleter;
import com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Interfaces.ReviewPublisher;
import com.chdryra.android.reviewer.Persistence.Factories.FactoryReviewsRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.AuthorsRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.NodeRepository;

/**
 * Created by: Rizwan Choudrey
 * On: 03/10/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class RepositorySuiteAndroid implements RepositorySuite {
    private final NodeRepository mReviewsRepo;
    private final AuthorsRepository mAuthorsRepo;
    private final FactoryReviewsRepository mRepoFactory;
    private final FactoryReviewDeleter mDeleterFactory;
    private final ReviewPublisher mPublisher;

    public RepositorySuiteAndroid(NodeRepository reviewsRepo,
                                  AuthorsRepository authorsRepo,
                                  FactoryReviewsRepository repoFactory,
                                  FactoryReviewDeleter deleterFactory,
                                  ReviewPublisher publisher) {
        mReviewsRepo = reviewsRepo;
        mAuthorsRepo = authorsRepo;
        mRepoFactory = repoFactory;
        mDeleterFactory = deleterFactory;
        mPublisher = publisher;
    }

    @Override
    public AuthorsRepository getAuthorsRepo() {
        return mAuthorsRepo;
    }

    @Override
    public NodeRepository getReviewsRepo() {
        return mReviewsRepo;
    }

    @Override
    public ReviewsRepository getFeed(ProfileSocial profile) {
        return mRepoFactory.newFeed(profile.getAuthorId(), profile.getFollowing(), mReviewsRepo);
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
