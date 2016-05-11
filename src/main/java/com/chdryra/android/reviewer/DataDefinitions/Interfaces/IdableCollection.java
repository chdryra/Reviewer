/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.DataDefinitions.Interfaces;

import android.support.annotation.Nullable;

import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface IdableCollection<T extends HasReviewId> extends Collection<T> {
    T getItem(int position);

    @Nullable
    T getItem(ReviewId id);
}
