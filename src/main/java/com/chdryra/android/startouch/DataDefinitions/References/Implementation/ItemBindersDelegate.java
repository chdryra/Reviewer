/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.DataDefinitions.References.Implementation;

import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.Size;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.CollectionBinder;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.CollectionReference;

import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 21/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ItemBindersDelegate<Value, S extends Size> {
    private final BindableListReference<Value, ?, S> mReference;

    public interface BindableListReference<Value, C extends Collection<Value>, S extends Size> extends CollectionReference<Value, C, S> {
        boolean containsItemBinder(CollectionBinder<Value> binder);

        void bindItemBinder(CollectionBinder<Value> binder);

        void unbindItemBinder(CollectionBinder<Value> binder);

        Collection<CollectionBinder<Value>> getItemBinders();
    }

    public ItemBindersDelegate(BindableListReference<Value, ?, S> reference) {
        mReference = reference;
    }

    public void bindToItems(final CollectionBinder<Value> binder) {
        if (!mReference.isValidReference()) {
            binder.onInvalidated(mReference);
        } else if (!mReference.containsItemBinder(binder)) {
            mReference.bindItemBinder(binder);
        }
    }

    public void unbindFromItems(CollectionBinder<Value> binder) {
        if (mReference.containsItemBinder(binder)) mReference.unbindItemBinder(binder);
    }

    public void notifyBinders() {
        for (CollectionBinder<Value> binder : mReference.getItemBinders()) {
            binder.onInvalidated(mReference);
        }
    }
}
