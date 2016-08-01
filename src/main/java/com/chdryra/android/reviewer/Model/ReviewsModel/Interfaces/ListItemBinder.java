/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ListReference;

/**
 * Created by: Rizwan Choudrey
 * On: 16/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface ListItemBinder<T> {
    void onItemValue(T value);

    void onItemRemoved(T value);

    void onInvalidated(ListReference<T, ?> reference);
}
