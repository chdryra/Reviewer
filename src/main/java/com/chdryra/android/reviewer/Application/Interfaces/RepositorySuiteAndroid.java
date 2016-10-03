/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Application.Interfaces;

import com.chdryra.android.reviewer.Authentication.Interfaces.SocialProfile;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.References.Implementation.DataValue;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.DataReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.NetworkServices.ReviewDeleting.Factories.FactoryReviewDeleter;
import com.chdryra.android.reviewer.NetworkServices.ReviewDeleting.Interfaces.ReviewDeleter;
import com.chdryra.android.reviewer.Persistence.Factories.FactoryReviewsRepository;
import com.chdryra.android.reviewer.Persistence.Implementation.RepositoryResult;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReferencesRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.RepositoryCallback;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsSource;

/**
 * Created by: Rizwan Choudrey
 * On: 03/10/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class RepositorySuiteAndroid implements RepositorySuite {
    private ReviewsSource mMasterRepo;
    private FactoryReviewsRepository mRepoFactory;
    private FactoryReviewDeleter mDeleterFactory;
    private TagsManager mManager;

    public RepositorySuiteAndroid(ReviewsSource masterRepo,
                                  FactoryReviewsRepository repoFactory,
                                  FactoryReviewDeleter deleterFactory,
                                  TagsManager manager) {
        mMasterRepo = masterRepo;
        mRepoFactory = repoFactory;
        mDeleterFactory = deleterFactory;
        mManager = manager;
    }

    @Override
    public void getReview(ReviewId id, final RepositoryCallback callback) {
        mMasterRepo.getReference(id, new RepositoryCallback() {
            @Override
            public void onRepositoryCallback(RepositoryResult result) {
                ReviewReference reference = result.getReference();
                if(result.isReference() && reference != null) {
                    reference.dereference(new DataReference.DereferenceCallback<Review>() {
                        @Override
                        public void onDereferenced(DataValue<Review> review) {
                            callback.onRepositoryCallback(new RepositoryResult(review.getData(), review.getMessage()));
                        }
                    });
                }
            }
        });
    }

    @Override
    public ReferencesRepository getReviews(AuthorId authorId) {
        return mMasterRepo.getRepositoryForAuthor(authorId);
    }

    @Override
    public ReferencesRepository getFeed(SocialProfile profile) {
        return mRepoFactory.newFeed(profile.getAuthorId(), profile.getFollowing(), mMasterRepo);
    }

    @Override
    public ReviewDeleter newReviewDeleter(ReviewId id) {
        return mDeleterFactory.newDeleter(id, mManager);
    }
}
