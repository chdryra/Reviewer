/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.BackendFirebase.Interfaces;

import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.BackendFirebase.Implementation
        .FbReview;

/**
 * Created by: Rizwan Choudrey
 * On: 08/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface FirebaseDbObserver {
    void onReviewAdded(FbReview review);

    void onReviewChanged(FbReview review);

    void onReviewRemoved(FbReview review);
}
