/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.DataDefinitions.References.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataSize;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.ListItemBinder;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.ReviewItemReference;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.ReviewListReference;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 14/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class StaticListReferenceBasic<Value extends HasReviewId, Reference extends ReviewItemReference<Value>>
        extends WrapperItemReference<IdableList<Value>>
        implements ReviewListReference<Value, Reference> {
    private final Collection<ListItemBinder<Value>> mItemBinders;

    StaticListReferenceBasic(IdableList<Value> value) {
        super(value);
        mItemBinders = new ArrayList<>();
    }

    @Override
    public ReviewItemReference<DataSize> getSize() {
        return new WrapperItemReference<>(getData().getDataSize());
    }

    @Override
    public void bindToItems(ListItemBinder<Value> binder) {
        if (isValidReference() && !mItemBinders.contains(binder)) mItemBinders.add(binder);
        for (Value item : getData()) {
            binder.onItemAdded(item);
        }
    }

    @Override
    public void unbindFromItems(ListItemBinder<Value> binder) {
        if (isValidReference() && mItemBinders.contains(binder)) mItemBinders.remove(binder);
    }

    @Override
    protected void onInvalidate() {
        for (ListItemBinder<Value> binder : mItemBinders) {
            binder.onInvalidated(this);
        }
        mItemBinders.clear();
    }

    @Override
    public void toItemReferences(ItemReferencesCallback<Value, Reference> callback) {
        IdableList<Reference> refs = new IdableDataList<>(getReviewId());
        for (Value item : getData()) {
            refs.add(newStaticReference(item));
        }
        callback.onItemReferences(refs);
    }

    protected abstract Reference newStaticReference(Value item);
}
