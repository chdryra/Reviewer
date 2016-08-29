/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Implementation;

import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewItemReference;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewListReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ListItemBinder;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 21/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class SimpleListReference<Value extends HasReviewId, Reference extends ReviewItemReference<Value>> extends SimpleItemReference<IdableList<Value>>
        implements ReviewListReference<Value, Reference>, ItemBindersDelegate.BindableListReference<Value, IdableList<Value>>{
    private ItemBindersDelegate<Value> mManager;
    private Collection<ListItemBinder<Value>> mItemBinders;

    public SimpleListReference(Dereferencer<IdableList<Value>> dereferencer) {
        super(dereferencer);
        mItemBinders = new ArrayList<>();
        mManager = new ItemBindersDelegate<>(this);
    }

    @Override
    public void bindToItems(ListItemBinder<Value> binder) {
        mManager.bindToItems(binder);
    }

    @Override
    public void unbindFromItems(ListItemBinder<Value> binder) {
        mManager.unbindFromItems(binder);
    }

    @Override
    public void removeItemBinder(ListItemBinder<Value> binder) {
        mItemBinders.remove(binder);
    }

    @Override
    protected void onInvalidate() {
        super.onInvalidate();
        mManager.notifyBinders();
        mItemBinders.clear();
    }

    @Override
    public Collection<ListItemBinder<Value>> getItemBinders() {
        return mItemBinders;
    }

    @Override
    public void bindItemBinder(ListItemBinder<Value> binder) {
        mItemBinders.add(binder);
        dereferenceForBinder(binder);
    }

    @Override
    public boolean containsItemBinder(ListItemBinder<Value> binder) {
        return mItemBinders.contains(binder);
    }

    private void dereferenceForBinder(final ListItemBinder<Value> binder) {
        dereference(new DereferenceCallback<IdableList<Value>>() {
            @Override
            public void onDereferenced(@Nullable IdableList<Value> data,
                                       CallbackMessage message) {
                if (data != null && !message.isError()) {
                    for (Value value : data) {
                        binder.onItemAdded(value);
                    }
                }
            }
        });
    }
}
