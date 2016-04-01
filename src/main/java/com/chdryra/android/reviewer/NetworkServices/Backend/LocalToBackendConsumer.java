/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.NetworkServices.Backend;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces.CallbackRepository;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces
        .CallbackRepositoryMutable;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces
        .ReviewsRepositoryMutable;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces
        .ReviewsRepositoryObserver;

import com.chdryra.android.reviewer.Utils.CallbackMessage;
import com.chdryra.android.reviewer.NetworkServices.Social.Interfaces.ReviewUploaderListener;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 01/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class LocalToBackendConsumer implements ReviewUploaderListener, ReviewsRepositoryObserver,
        CallbackRepositoryMutable, CallbackRepository {
    private ReviewsRepositoryMutable mLocalRepo;
    private BackendReviewUploader mUploader;
    private ArrayList<ReviewId> mBacklogToUpload;
    private ArrayList<ReviewId> mBacklogToDelete;

    public LocalToBackendConsumer(ReviewsRepositoryMutable localRepo,
                                  BackendReviewUploader uploader) {
        mLocalRepo = localRepo;
        mLocalRepo.registerObserver(this);

        mUploader = uploader;
        mUploader.registerListener(this);

        mBacklogToUpload = new ArrayList<>();
        mBacklogToDelete = new ArrayList<>();
    }

    @Override
    public void onReviewAdded(Review review) {
        mLocalRepo.getReviews(this);
    }

    @Override
    public void onReviewRemoved(ReviewId reviewId) {
        ReviewId toRemove = null;
        for (ReviewId id : mBacklogToUpload) {
            if (id.equals(reviewId)) {
                toRemove = id;
                break;
            }
        }

        if (toRemove != null) mBacklogToUpload.remove(toRemove);
    }

    @Override
    public void onUploadedToBackend(ReviewId reviewId, CallbackMessage result) {
        if (result.isError()) return;
        updateBacklogs(reviewId);
        deleteToDelete();
    }

    @Override
    public void onDeletedFromBackend(ReviewId reviewId, CallbackMessage result) {
        //Don't care
    }

    @Override
    public void onAddedCallback(Review review, CallbackMessage result) {
        throw new IllegalStateException("Should never call this!");
    }

    @Override
    public void onRemovedCallback(ReviewId reviewId, CallbackMessage result) {
        if (result.isError()) return;
        mBacklogToDelete.remove(reviewId);
    }

    @Override
    public void onFetchedFromRepo(@Nullable Review review, CallbackMessage result) {
        throw new IllegalStateException("Should never call this!");
    }

    @Override
    public void onFetchedFromRepo(Collection<Review> reviews, CallbackMessage result) {
        if (result.isError()) return;
        updateToUpload(reviews);
        uploadToUpload();
    }

    private void deleteToDelete() {
        for (ReviewId toDelete : mBacklogToDelete) {
            mLocalRepo.removeReview(toDelete, this);
        }
    }

    private void updateBacklogs(ReviewId reviewId) {
        mBacklogToUpload.remove(reviewId);
        mBacklogToDelete.add(reviewId);
    }

    private void uploadToUpload() {
        for (ReviewId toUpload : mBacklogToUpload) {
            mUploader.uploadReview(toUpload);
        }
    }

    private void updateToUpload(Collection<Review> reviews) {
        for (Review toUpload : reviews) {
            ReviewId reviewId = toUpload.getReviewId();
            if (!mBacklogToUpload.contains(reviewId)) mBacklogToUpload.add(reviewId);
        }
    }
}
