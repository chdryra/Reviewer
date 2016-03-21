/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.Api;

import android.content.Context;

import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ModelContext;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel
        .ReviewsRepositoryMutable;

/**
 * Created by: Rizwan Choudrey
 * On: 13/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface PersistencePlugin {
    ReviewsRepositoryMutable newLocalPersistence(Context context, ModelContext modelContext);

    ReviewsRepositoryMutable newBackendPersistence(Context context, ModelContext modelContext);
}
