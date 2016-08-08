/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumSize;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSize;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewItemReference;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewListReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ListItemBinder;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 01/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class StaticListReference<T extends HasReviewId> extends StaticItemReference<IdableList<T>> implements ReviewListReference<T> {
    private Collection<ListItemBinder<T>> mItemBinders;

    public StaticListReference(ReviewId id, IdableList<T> value) {
        super(id, value);
        mItemBinders = new ArrayList<>();
    }

    @Override
    public void toItemReferences(ItemReferencesCallback<T> callback) {
        IdableList<ReviewItemReference<T>> refs = new IdableDataList<>(getReviewId());
        for(T item : getData()) {
            refs.add(new StaticItemReference<>(getReviewId(), item));
        }
        callback.onItemReferences(refs);
    }

    @Override
    public ReviewItemReference<DataSize> getSize() {
        DataSize size = new DatumSize(getReviewId(), getData().size());
        return new StaticItemReference<>(getReviewId(), size);
    }

    @Override
    public void bindToItems(ListItemBinder<T> binder) {
        if(isValidReference() && !mItemBinders.contains(binder)) mItemBinders.add(binder);
        for(T item : getData()) {
            binder.onItemAdded(item);
        }
    }

    @Override
    public void unbindFromItems(ListItemBinder<T> binder) {
        if(isValidReference() && mItemBinders.contains(binder)) mItemBinders.remove(binder);
    }

    @Override
    protected void onInvalidate() {
        for(ListItemBinder<T> binder : mItemBinders) {
            binder.onInvalidated(this);
        }
        mItemBinders.clear();
    }
}
