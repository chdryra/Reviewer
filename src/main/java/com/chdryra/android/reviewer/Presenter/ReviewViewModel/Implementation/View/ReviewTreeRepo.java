/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataReviewInfo;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.NodeInternal;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Persistence.Implementation.RepositoryResult;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepositoryObserver;

/**
 * Created by: Rizwan Choudrey
 * On: 08/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewTreeRepo extends NodeInternal implements ReviewsRepository.RepositoryCallback,
        ReviewsRepositoryObserver, ReviewNode {

    private ReviewsRepository mRepo;

    public ReviewTreeRepo(DataReviewInfo meta, ReviewsRepository repo,
                          FactoryReviewNode nodeFactory) {
        super(meta, nodeFactory);
        mRepo = repo;
        mRepo.registerObserver(this);
        mRepo.getReferences(this);
    }


    @Override
    public void onRepositoryCallback(RepositoryResult result) {
        if (!result.isError()) {
            if(result.isReferenceCollection()) {
                for (ReviewReference review : result.getReferences()) {
                    addChild(review);
                }
            } else if(result.isReference()) {
                ReviewReference reference = result.getReference();
                if(reference != null) addChild(reference);
            }
        }
    }

    @Override
    public void onReviewAdded(Review review) {
        mRepo.getReference(review.getReviewId(), this);
    }

    @Override
    public void onReviewRemoved(ReviewId reviewId) {
        removeChild(reviewId);
    }

    private void addChild(ReviewReference review) {
        super.addChild(getNodeFactory().createLeafNode(review));
    }

    public void detachFromRepo() {
        mRepo.unregisterObserver(this);
    }
}
