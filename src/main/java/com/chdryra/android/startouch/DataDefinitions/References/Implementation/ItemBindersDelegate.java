/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.DataDefinitions.References.Implementation;

import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.Size;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.ListItemBinder;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.ListReference;

import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 21/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ItemBindersDelegate<Value, S extends Size> {
    private final BindableListReference<Value, ?, S> mReference;

    public interface BindableListReference<Value, C extends Collection<Value>, S extends Size> extends ListReference<Value, C, S> {
        boolean containsItemBinder(ListItemBinder<Value> binder);

        void bindItemBinder(ListItemBinder<Value> binder);

        void removeItemBinder(ListItemBinder<Value> binder);

        Iterable<? extends ListItemBinder<Value>> getItemBinders();
    }

    public ItemBindersDelegate(BindableListReference<Value, ?, S> reference) {
        mReference = reference;
    }

    public void bindToItems(final ListItemBinder<Value> binder) {
        if (!mReference.isValidReference()) {
            binder.onInvalidated(mReference);
        } else if (!mReference.containsItemBinder(binder)) {
            mReference.bindItemBinder(binder);
        }
    }

    public void unbindFromItems(ListItemBinder<Value> binder) {
        if (mReference.containsItemBinder(binder)) mReference.removeItemBinder(binder);
    }

    public void notifyBinders() {
        for (ListItemBinder<Value> binder : mReference.getItemBinders()) {
            binder.onInvalidated(mReference);
        }
    }
}
