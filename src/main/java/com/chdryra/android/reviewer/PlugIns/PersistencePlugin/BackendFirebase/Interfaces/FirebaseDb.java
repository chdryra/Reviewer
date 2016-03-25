/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.BackendFirebase.Interfaces;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.Utils.ReviewDataHolder;
import com.firebase.client.FirebaseError;

import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 24/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface FirebaseDb {
    interface AddCallback {
        void onReviewAdded(Review review, @Nullable FirebaseError error);
    }

    interface DeleteCallback {
        void onReviewDeleted(ReviewId reviewId, @Nullable FirebaseError error);
    }

    interface GetCallback {
        void onReview(ReviewDataHolder review, @Nullable FirebaseError error);
    }

    interface GetCollectionCallback {
        void onReviewCollection(Collection<ReviewDataHolder> reviews, @Nullable FirebaseError
                error);
    }

    void addReview(Review review, TagsManager tagsManager, AddCallback callback);

    void deleteReview(ReviewId reviewId, DeleteCallback callback);

    void getReview(ReviewId id, TagsManager tagsManager, GetCallback callback);

    void getReviews(TagsManager tagsManager, GetCollectionCallback callback);
}
