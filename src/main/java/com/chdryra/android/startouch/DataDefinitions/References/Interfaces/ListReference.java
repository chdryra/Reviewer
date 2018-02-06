/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.DataDefinitions.References.Interfaces;

import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 28/07/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface ListReference<T, C extends Collection<T>> extends DataReference<C> {
    void bindToItems(ListItemBinder<T> binder);

    void unbindFromItems(ListItemBinder<T> binder);
}
