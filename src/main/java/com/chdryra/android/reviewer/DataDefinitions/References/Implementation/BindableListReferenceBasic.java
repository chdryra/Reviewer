/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.DataDefinitions.References.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.ListItemBinder;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReferenceBinder;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 21/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class BindableListReferenceBasic<T extends HasReviewId> extends 
        BindableReferenceBasic<IdableList<T>>
        implements ItemBindersDelegate.BindableListReference<T, IdableList<T>> {
    private final Collection<ListItemBinder<T>> mItemBinders;
    private final Collection<ReferenceBinder<IdableList<T>>> mValueBinders;
    private final ItemBindersDelegate<T> mDelegate;

    protected abstract void fireForBinder(ListItemBinder<T> binder);

    @Override
    protected abstract void doDereferencing(DereferenceCallback<IdableList<T>> callback);

    protected BindableListReferenceBasic() {
        mValueBinders = new ArrayList<>();
        mItemBinders = new ArrayList<>();
        mDelegate = new ItemBindersDelegate<>(this);
    }

    @Override
    protected Collection<ReferenceBinder<IdableList<T>>> getBinders() {
        return mValueBinders;
    }

    @Override
    protected void bind(final ReferenceBinder<IdableList<T>> binder) {
        mValueBinders.add(binder);
        dereference(new DereferenceCallback<IdableList<T>>() {
            @Override
            public void onDereferenced(DataValue<IdableList<T>> value) {
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
    protected boolean contains(ReferenceBinder<IdableList<T>> binder) {
        return mValueBinders.contains(binder);
    }

    @Override
    protected void removeBinder(ReferenceBinder<IdableList<T>> binder) {
        mValueBinders.remove(binder);
    }

    @Override
    public void unbindFromItems(ListItemBinder<T> binder) {
        mDelegate.unbindFromItems(binder);
    }

    @Override
    public void bindToItems(final ListItemBinder<T> binder) {
        mDelegate.bindToItems(binder);
    }

    @Override
    public Iterable<? extends ListItemBinder<T>> getItemBinders() {
        return mItemBinders;
    }

    @Override
    public void removeItemBinder(ListItemBinder<T> binder) {
        mItemBinders.remove(binder);
    }

    @Override
    public void bindItemBinder(final ListItemBinder<T> binder) {
        mItemBinders.add(binder);
        fireForBinder(binder);
    }

    @Override
    public boolean containsItemBinder(ListItemBinder<T> binder) {
        return mItemBinders.contains(binder);
    }

    private void notifyItemBinders(IdableList<T> items) {
        for (ListItemBinder<T> binder : mItemBinders) {
            binder.onListChanged(items);
        }
    }

    protected void notifyItemBindersAdd(IdableList<T> data) {
        for (ListItemBinder<T> binder : mItemBinders) {
            notifyItemBinderAdd(binder, data);
        }
    }

    protected void notifyItemBinderAdd(ListItemBinder<T> binder, IdableList<T> data) {
        for (T reference : data) {
            binder.onItemAdded(reference);
        }
    }

    protected void notifyItemBindersRemove(IdableList<T> data) {
        for (ListItemBinder<T> binder : mItemBinders) {
            for (T reference : data) {
                binder.onItemRemoved(reference);
            }
        }
    }

    private void notifyValueBinders(IdableList<T> data) {
        for (ReferenceBinder<IdableList<T>> binder : mValueBinders) {
            binder.onReferenceValue(data);
        }
    }

    protected void notifyValueBinders() {
        if (hasValueBinders()) {
            dereference(new DereferenceCallback<IdableList<T>>() {
                @Override
                public void onDereferenced(DataValue<IdableList<T>> value) {
                    if (value.hasValue()) notifyValueBinders(value.getData());
                }
            });
        }
    }

    protected void notifyAllBinders() {
        if (hasValueBinders() || hasItemBinders()) {
            dereference(new DereferenceCallback<IdableList<T>>() {
                @Override
                public void onDereferenced(DataValue<IdableList<T>> value) {
                    if (value.hasValue()) {
                        notifyValueBinders(value.getData());
                        notifyItemBinders(value.getData());
                    }
                }
            });
        }
    }
}
