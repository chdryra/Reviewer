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
 * On: 16/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface ListItemBinder<T> {
    void onItemAdded(T value);

    void onItemRemoved(T value);

    void onListChanged(Collection<T> newItems);

    void onInvalidated(ListReference<T, ?> reference);
}