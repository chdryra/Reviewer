/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Application.Implementation;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.Application.Interfaces.RepositorySuite;
import com.chdryra.android.reviewer.Authentication.Interfaces.ProfileSocial;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.References.Implementation.DataValue;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.DataReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.NetworkServices.ReviewDeleting.Factories.FactoryReviewDeleter;
import com.chdryra.android.reviewer.NetworkServices.ReviewDeleting.Interfaces.ReviewDeleter;
import com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Interfaces.ReviewPublisher;
import com.chdryra.android.reviewer.Persistence.Factories.FactoryReviewsRepository;
import com.chdryra.android.reviewer.Persistence.Implementation.RepositoryResult;
import com.chdryra.android.reviewer.Persistence.Interfaces.AuthorsRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReferencesRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.RepositoryCallback;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsSource;

/**
 * Created by: Rizwan Choudrey
 * On: 03/10/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class RepositorySuiteAndroid implements RepositorySuite {
    private final ReviewsSource mReviewsRepo;
    private final AuthorsRepository mAuthorsRepo;
    private final FactoryReviewsRepository mRepoFactory;
    private final FactoryReviewDeleter mDeleterFactory;
    private final ReviewPublisher mPublisher;

    public RepositorySuiteAndroid(ReviewsSource reviewsRepo,
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
    public void getReview(ReviewId id, final RepositoryCallback callback) {
        mReviewsRepo.getReference(id, dereferenceOnReturn(callback));
    }

    @Override
    public AuthorsRepository getAuthorsRepo() {
        return mAuthorsRepo;
    }

    @Override
    public ReviewsSource getReviewsRepo() {
        return mReviewsRepo;
    }

    @Override
    public ReferencesRepository getFeed(ProfileSocial profile) {
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

    @NonNull
    private RepositoryCallback dereferenceOnReturn(final RepositoryCallback callback) {
        return new RepositoryCallback() {
            @Override
            public void onRepoCallback(RepositoryResult result) {
                if (result.isReference()) dereference(result.getReference(), callback);
            }
        };
    }

    private void dereference(ReviewReference reference, final RepositoryCallback callback) {
        reference.dereference(new DataReference.DereferenceCallback<Review>() {
            @Override
            public void onDereferenced(DataValue<Review> review) {
                RepositoryResult result
                        = new RepositoryResult(review.getData(), review.getMessage());
                callback.onRepoCallback(result);
            }
        });
    }
}
