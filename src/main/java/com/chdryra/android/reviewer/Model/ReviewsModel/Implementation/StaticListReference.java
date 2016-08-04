/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
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
    public void bindToItems(ListItemBinder<T> binder) {
        if(isValidReference() && !mItemBinders.contains(binder)) mItemBinders.add(binder);
        for(T item : getData()) {
            binder.onItemValue(item);
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
