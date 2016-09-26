/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.NetworkServices.ReviewDeleting;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;

/**
 * Created by: Rizwan Choudrey
 * On: 26/09/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class ReviewDeleterImpl implements ReviewDeleter {
    private ReviewDeleter mBackendDeleter;
    private TagsManager mTagsManager;

    public ReviewDeleterImpl(ReviewDeleter backendDeleter, TagsManager tagsManager) {
        mBackendDeleter = backendDeleter;
        mTagsManager = tagsManager;
    }

    @Override
    public void deleteReview(final ReviewDeleterCallback callback) {
        mBackendDeleter.deleteReview(new ReviewDeleterCallback() {
            @Override
            public void onReviewDeleted(ReviewId reviewId, CallbackMessage result) {
                if(!result.isError()) mTagsManager.clearTags(reviewId.toString());
                callback.onReviewDeleted(reviewId, result);
            }
        });
    }
}
