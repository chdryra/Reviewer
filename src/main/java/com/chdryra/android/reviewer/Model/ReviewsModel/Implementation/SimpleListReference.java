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
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewListReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ListItemBinder;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 21/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class SimpleListReference<T extends HasReviewId> extends SimpleItemReference<IdableList<T>> 
        implements ReviewListReference<T>, ItemBindersDelegate.BindableListReference<T, IdableList<T>>{
    private ItemBindersDelegate<T> mManager;
    private Collection<ListItemBinder<T>> mItemBinders;

    public SimpleListReference(Dereferencer<IdableList<T>> dereferencer) {
        super(dereferencer);
        mItemBinders = new ArrayList<>();
        mManager = new ItemBindersDelegate<>(this);
    }

    @Override
    public void bindToItems(ListItemBinder<T> binder) {
        mManager.bindToItems(binder);
    }

    @Override
    public void unbindFromItems(ListItemBinder<T> binder) {
        mManager.unbindFromItems(binder);
    }

    @Override
    public void removeItemBinder(ListItemBinder<T> binder) {
        mItemBinders.remove(binder);
    }

    @Override
    protected void onInvalidate() {
        super.onInvalidate();
        mManager.notifyBinders();
        mItemBinders.clear();
    }

    @Override
    public Collection<ListItemBinder<T>> getItemBinders() {
        return mItemBinders;
    }

    @Override
    public void bindItemBinder(ListItemBinder<T> binder) {
        mItemBinders.add(binder);
        dereferenceForBinder(binder);
    }

    @Override
    public boolean containsItemBinder(ListItemBinder<T> binder) {
        return mItemBinders.contains(binder);
    }

    private void dereferenceForBinder(final ListItemBinder<T> binder) {
        dereference(new DereferenceCallback<IdableList<T>>() {
            @Override
            public void onDereferenced(@Nullable IdableList<T> data,
                                       CallbackMessage message) {
                if (data != null && !message.isError()) {
                    for (T value : data) {
                        binder.onItemAdded(value);
                    }
                }
            }
        });
    }
}
