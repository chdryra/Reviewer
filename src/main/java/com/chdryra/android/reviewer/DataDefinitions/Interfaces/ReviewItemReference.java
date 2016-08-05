/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.DataDefinitions.Interfaces;

import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReferenceBinder;

/**
 * Created by: Rizwan Choudrey
 * On: 28/07/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewItemReference<T extends HasReviewId> extends DataReference<T>, HasReviewId{
    @Override
    void dereference(DereferenceCallback<T> callback);

    @Override
    void bindToValue(ReferenceBinder<T> binder);

    @Override
    void unbindFromValue(ReferenceBinder<T> binder);

    @Override
    void registerListener(InvalidationListener listener);

    @Override
    void unregisterListener(InvalidationListener listener);

    @Override
    boolean isValidReference();

    @Override
    void invalidate();

    @Override
    ReviewId getReviewId();
}
