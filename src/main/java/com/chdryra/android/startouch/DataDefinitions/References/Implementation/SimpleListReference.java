/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.DataDefinitions.References.Implementation;

import com.chdryra.android.corelibrary.ReferenceModel.Implementation.DataValue;
import com.chdryra.android.corelibrary.ReferenceModel.Implementation.SubscribersManager;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataSize;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.ReviewItemReference;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.ReviewListReference;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 21/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class SimpleListReference<Value extends HasReviewId, Reference extends ReviewItemReference<Value>> extends SimpleItemReference<IdableList<Value>>
        implements ReviewListReference<Value, Reference>, SubscribersManager.SubscribableCollectionReference<Value, IdableList<Value>, DataSize> {
    private final SubscribersManager<Value, DataSize> mManager;
    private final Collection<ItemSubscriber<Value>> mItemBinders;

    protected SimpleListReference(Dereferencer<IdableList<Value>> dereferencer) {
        super(dereferencer);
        mItemBinders = new ArrayList<>();
        mManager = new SubscribersManager<>(this);
    }

    @Override
    public void subscribe(ItemSubscriber<Value> binder) {
        mManager.subscribe(binder);
    }

    @Override
    public void unsubscribe(ItemSubscriber<Value> binder) {
        mManager.unsubscribe(binder);
    }

    @Override
    public void unbindSubscriber(ItemSubscriber<Value> binder) {
        mItemBinders.remove(binder);
    }

    @Override
    protected void onInvalidate() {
        super.onInvalidate();
        mManager.notifyOnInvalidated();
        mItemBinders.clear();
    }

    @Override
    public Collection<ItemSubscriber<Value>> getItemSubscribers() {
        return mItemBinders;
    }

    @Override
    public void bindSubscriber(ItemSubscriber<Value> binder) {
        mItemBinders.add(binder);
        dereferenceForBinder(binder);
    }

    @Override
    public boolean containsSubscriber(ItemSubscriber<Value> binder) {
        return mItemBinders.contains(binder);
    }

    private void dereferenceForBinder(final ItemSubscriber<Value> binder) {
        dereference(new DereferenceCallback<IdableList<Value>>() {
            @Override
            public void onDereferenced(DataValue<IdableList<Value>> value) {
                if (value.hasValue()) {
                    for (Value item : value.getData()) {
                        binder.onItemAdded(item);
                    }
                }
            }
        });
    }
}
