/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Interfaces;


import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.BackendError;

/**
 * Created by: Rizwan Choudrey
 * On: 09/07/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface DeleteReviewCallback {
    void onReviewDeleted(String reviewId, @Nullable BackendError error);
}