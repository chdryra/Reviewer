/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.DataDefinitions.References.Implementation;

import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.IdableDataList;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataSize;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.ReviewItemReference;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.ReviewListReference;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 14/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class StaticListReferenceBasic<Value extends HasReviewId, Reference extends ReviewItemReference<Value>>
        extends StaticItemReference<IdableList<Value>>
        implements ReviewListReference<Value, Reference> {
    private final Collection<ItemSubscriber<Value>> mItemBinders;

    StaticListReferenceBasic(IdableList<Value> value) {
        super(value);
        mItemBinders = new ArrayList<>();
    }

    @Override
    public ReviewItemReference<DataSize> getSize() {
        return new StaticItemReference<>(getData().getDataSize());
    }

    @Override
    public void subscribe(ItemSubscriber<Value> binder) {
        if (isValidReference() && !mItemBinders.contains(binder)) mItemBinders.add(binder);
        for (Value item : getData()) {
            binder.onItemAdded(item);
        }
    }

    @Override
    public void unsubscribe(ItemSubscriber<Value> binder) {
        if (isValidReference() && mItemBinders.contains(binder)) mItemBinders.remove(binder);
    }

    @Override
    protected void onInvalidate() {
        for (ItemSubscriber<Value> binder : mItemBinders) {
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
