/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.DataDefinitions.Interfaces;

import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ListItemBinder;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReferenceBinder;

/**
 * Created by: Rizwan Choudrey
 * On: 28/07/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewListReference<T extends HasReviewId> extends ListReference<T, IdableList<T>>, HasReviewId{
    interface ItemReferencesCallback<T extends HasReviewId> {
        void onItemReferences(IdableList<ReviewItemReference<T>> references);
    }

    void toItemReferences(ItemReferencesCallback<T> callback);

    ReviewItemReference<DataSize> getSizeReference();

    @Override
    void bindToItems(ListItemBinder<T> binder);

    @Override
    void unbindFromItems(ListItemBinder<T> binder);

    @Override
    void dereference(DereferenceCallback<IdableList<T>> callback);

    @Override
    void bindToValue(ReferenceBinder<IdableList<T>> binder);

    @Override
    void unbindFromValue(ReferenceBinder<IdableList<T>> binder);

    @Override
    boolean isValidReference();

    @Override
    void invalidate();

    @Override
    ReviewId getReviewId();
}
