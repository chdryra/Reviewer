/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Persistence.Interfaces;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 30/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewCollection extends ReviewsRepoReadable {
    interface Callback {
        void onAddedToCollection(CallbackMessage message);

        void onRemovedFromCollection(CallbackMessage message);

        void onCollectionHasReview(boolean hasReview, CallbackMessage message);
    }

    String getName();

    void addEntry(ReviewId reviewId, Callback callback);

    void removeEntry(ReviewId reviewId, Callback callback);

    void hasEntry(ReviewId reviewId, Callback callback);
}
