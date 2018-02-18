/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.DataDefinitions.References.Implementation;

import android.support.annotation.Nullable;

import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.Size;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.CollectionBinder;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReferenceBinder;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 21/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class BindableListReferenceBasic<T, C extends Collection<T>, S extends Size> extends
        BindableReferenceBasic<C>
        implements ItemBindersDelegate.BindableListReference<T, C, S> {
    private final Collection<CollectionBinder<T>> mItemBinders;
    private final Collection<ReferenceBinder<C>> mValueBinders;
    private final ItemBindersDelegate<T, S> mDelegate;

    protected abstract void fireForBinder(CollectionBinder<T> binder);

    protected BindableListReferenceBasic() {
        mValueBinders = new ArrayList<>();
        mItemBinders = new ArrayList<>();
        mDelegate = new ItemBindersDelegate<>(this);
    }

    @Nullable
    @Override
    protected abstract C getNullValue();

    @Override
    protected Collection<ReferenceBinder<C>> getBinders() {
        return mValueBinders;
    }

    @Override
    protected void bind(final ReferenceBinder<C> binder) {
        mValueBinders.add(binder);
        dereference(new DereferenceCallback<C>() {
            @Override
            public void onDereferenced(DataValue<C> value) {
                if (value.hasValue()) binder.onReferenceValue(value.getData());
            }
        });
    }

    @Override
    protected void onInvalidate() {
        super.onInvalidate();
        mDelegate.notifyBinders();
        mItemBinders.clear();
        mValueBinders.clear();
    }

    protected boolean hasItemBinders() {
        return mItemBinders.size() > 0;
    }

    private boolean hasValueBinders() {
        return mValueBinders.size() > 0;
    }

    @Override
    protected boolean contains(ReferenceBinder<C> binder) {
        return mValueBinders.contains(binder);
    }

    @Override
    protected void removeUnboundBinder(ReferenceBinder<C> binder) {
        mValueBinders.remove(binder);
    }

    @Override
    public void unbindFromItems(CollectionBinder<T> binder) {
        mDelegate.unbindFromItems(binder);
    }

    @Override
    public void bindToItems(final CollectionBinder<T> binder) {
        mDelegate.bindToItems(binder);
    }

    @Override
    public Collection<CollectionBinder<T>> getItemBinders() {
        return mItemBinders;
    }

    @Override
    public void unbindItemBinder(CollectionBinder<T> binder) {
        mItemBinders.remove(binder);
    }

    @Override
    public void bindItemBinder(final CollectionBinder<T> binder) {
        if(!containsItemBinder(binder)) {
            mItemBinders.add(binder);
            fireForBinder(binder);
        }
    }

    @Override
    public boolean containsItemBinder(CollectionBinder<T> binder) {
        return mItemBinders.contains(binder);
    }

    private void notifyOnCollectionChanged(C items) {
        for (CollectionBinder<T> binder : mItemBinders) {
            binder.onCollectionChanged(items);
        }
    }

    protected void notifyOnAdded(C data) {
        for (CollectionBinder<T> binder : mItemBinders) {
            notifyOnAdded(binder, data);
        }
    }

    protected void notifyOnAdded(T item) {
        for (CollectionBinder<T> binder : mItemBinders) {
            binder.onItemAdded(item);
        }
    }

    protected void notifyOnAdded(CollectionBinder<T> binder, C data) {
        for (T reference : data) {
            binder.onItemAdded(reference);
        }
    }

    protected void notifyOnRemoved(C data) {
        for (CollectionBinder<T> binder : mItemBinders) {
            for (T reference : data) {
                binder.onItemRemoved(reference);
            }
        }
    }

    protected void notifyOnRemoved(T item) {
        for (CollectionBinder<T> binder : mItemBinders) {
            binder.onItemRemoved(item);
        }
    }

    private void notifyValueBinders(C data) {
        for (ReferenceBinder<C> binder : mValueBinders) {
            binder.onReferenceValue(data);
        }
    }

    protected void notifyValueBinders() {
        if (hasValueBinders()) {
            dereference(new DereferenceCallback<C>() {
                @Override
                public void onDereferenced(DataValue<C> value) {
                    if (value.hasValue()) notifyValueBinders(value.getData());
                }
            });
        }
    }

    protected void notifyAllBinders() {
        if (hasValueBinders() || hasItemBinders()) {
            dereference(new DereferenceCallback<C>() {
                @Override
                public void onDereferenced(DataValue<C> value) {
                    if (value.hasValue()) {
                        notifyValueBinders(value.getData());
                        notifyOnCollectionChanged(value.getData());
                    }
                }
            });
        }
    }
}
