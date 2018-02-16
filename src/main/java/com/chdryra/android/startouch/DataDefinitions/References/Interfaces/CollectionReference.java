/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.DataDefinitions.References.Interfaces;

import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.Size;

import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 28/07/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface CollectionReference<T, C extends Collection<T>, S extends Size> extends DataReference<C> {
    void bindToItems(CollectionBinder<T> binder);

    void unbindFromItems(CollectionBinder<T> binder);

    DataReference<S> getSize();
}
