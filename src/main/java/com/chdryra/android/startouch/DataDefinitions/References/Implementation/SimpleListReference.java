/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.DataDefinitions.References.Implementation;

import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataSize;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.CollectionBinder;
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
        implements ReviewListReference<Value, Reference>, ItemBindersDelegate.BindableListReference<Value, IdableList<Value>, DataSize> {
    private final ItemBindersDelegate<Value, DataSize> mManager;
    private final Collection<CollectionBinder<Value>> mItemBinders;

    protected SimpleListReference(Dereferencer<IdableList<Value>> dereferencer) {
        super(dereferencer);
        mItemBinders = new ArrayList<>();
        mManager = new ItemBindersDelegate<>(this);
    }

    @Override
    public void bindToItems(CollectionBinder<Value> binder) {
        mManager.bindToItems(binder);
    }

    @Override
    public void unbindFromItems(CollectionBinder<Value> binder) {
        mManager.unbindFromItems(binder);
    }

    @Override
    public void unbindItemBinder(CollectionBinder<Value> binder) {
        mItemBinders.remove(binder);
    }

    @Override
    protected void onInvalidate() {
        super.onInvalidate();
        mManager.notifyBinders();
        mItemBinders.clear();
    }

    @Override
    public Collection<CollectionBinder<Value>> getItemBinders() {
        return mItemBinders;
    }

    @Override
    public void bindItemBinder(CollectionBinder<Value> binder) {
        mItemBinders.add(binder);
        dereferenceForBinder(binder);
    }

    @Override
    public boolean containsItemBinder(CollectionBinder<Value> binder) {
        return mItemBinders.contains(binder);
    }

    private void dereferenceForBinder(final CollectionBinder<Value> binder) {
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
