/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Persistence.Interfaces;


import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Persistence.Implementation.ReviewInfo;

/**
 * Created by: Rizwan Choudrey
 * On: 13/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewReference {

    interface DereferenceCallback {
        void onDereferenced(@Nullable Review review, CallbackMessage message);
    }

    ReviewInfo getInfo();

    void registerObserver(ReferenceObservers.SubjectObserver observer);

    void registerObserver(ReferenceObservers.RatingObserver observer);

    void registerObserver(ReferenceObservers.AuthorObserver observer);

    void registerObserver(ReferenceObservers.DateObserver observer);

    void registerObserver(ReferenceObservers.CoverObserver observer);

    void registerObserver(ReferenceObservers.CriteriaObserver observer);

    void registerObserver(ReferenceObservers.CommentsObserver observer);

    void registerObserver(ReferenceObservers.FactsObserver observer);

    void registerObserver(ReferenceObservers.ImagesObserver observer);

    void registerObserver(ReferenceObservers.LocationsObserver observer);

    void registerObserver(ReferenceObservers.TagsObserver observer);

    void unregisterObserver(ReferenceObservers.SubjectObserver observer);

    void unregisterObserver(ReferenceObservers.RatingObserver observer);

    void unregisterObserver(ReferenceObservers.AuthorObserver observer);

    void unregisterObserver(ReferenceObservers.DateObserver observer);

    void unregisterObserver(ReferenceObservers.CoverObserver observer);

    void unregisterObserver(ReferenceObservers.CriteriaObserver observer);

    void unregisterObserver(ReferenceObservers.CommentsObserver observer);

    void unregisterObserver(ReferenceObservers.FactsObserver observer);

    void unregisterObserver(ReferenceObservers.ImagesObserver observer);

    void unregisterObserver(ReferenceObservers.LocationsObserver observer);

    void unregisterObserver(ReferenceObservers.TagsObserver observer);

    void dereference(DereferenceCallback callback);

    boolean isValid();
}
