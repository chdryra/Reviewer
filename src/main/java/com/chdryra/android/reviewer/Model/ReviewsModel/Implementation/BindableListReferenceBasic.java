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
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ListItemBinder;
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
    private Collection<ListItemBinder<T>> mItemBinders;
    private Collection<ReferenceBinder<IdableList<T>>> mValueBinders;
    private ItemBindersDelegate<T> mDelegate;

    protected abstract void fireForBinder(ListItemBinder<T> binder);

    @Override
    protected abstract void doDereferencing(DereferenceCallback<IdableList<T>> callback);

    public BindableListReferenceBasic() {
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
            public void onDereferenced(@Nullable IdableList<T> data, CallbackMessage message) {
                if (data != null && !message.isError()) binder.onReferenceValue(data);
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

    protected boolean hasValueBinders() {
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

    protected void notifyItemBinders(IdableList<T> items) {
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

    protected void notifyValueBinders(IdableList<T> data) {
        for (ReferenceBinder<IdableList<T>> binder : mValueBinders) {
            binder.onReferenceValue(data);
        }
    }

    protected void notifyValueBinders() {
        if (hasValueBinders()) {
            dereference(new DereferenceCallback<IdableList<T>>() {
                @Override
                public void onDereferenced(@Nullable IdableList<T> data, CallbackMessage message) {
                    if (data != null && !message.isError()) notifyValueBinders(data);
                }
            });
        }
    }

    protected void notifyAllBinders() {
        if (hasValueBinders() || hasItemBinders()) {
            dereference(new DereferenceCallback<IdableList<T>>() {
                @Override
                public void onDereferenced(@Nullable IdableList<T> data, CallbackMessage message) {
                    if (data != null && !message.isError()) {
                        notifyValueBinders(data);
                        notifyItemBinders(data);
                    }
                }
            });
        }
    }
}
