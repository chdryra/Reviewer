/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.DataDefinitions.References.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.ListReference;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.ListItemBinder;

import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 21/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ItemBindersDelegate<T> {
    private BindableListReference<T, ?> mReference;

    public interface BindableListReference<T, C extends Collection<T>> extends ListReference<T, C> {
        boolean containsItemBinder(ListItemBinder<T> binder);

        void bindItemBinder(ListItemBinder<T> binder);

        void removeItemBinder(ListItemBinder<T> binder);

        Iterable<? extends ListItemBinder<T>> getItemBinders();
    }

    public ItemBindersDelegate(BindableListReference<T, ?> reference) {
        mReference = reference;
    }

    public void bindToItems(final ListItemBinder<T> binder) {
        if (!mReference.isValidReference()) {
            binder.onInvalidated(mReference);
        } else if (!mReference.containsItemBinder(binder)) {
            mReference.bindItemBinder(binder);
        }
    }

    public void unbindFromItems(ListItemBinder<T> binder) {
        if (mReference.containsItemBinder(binder)) mReference.removeItemBinder(binder);
    }

    public void notifyBinders() {
        for (ListItemBinder<T> binder : mReference.getItemBinders()) {
            binder.onInvalidated(mReference);
        }
    }
}
