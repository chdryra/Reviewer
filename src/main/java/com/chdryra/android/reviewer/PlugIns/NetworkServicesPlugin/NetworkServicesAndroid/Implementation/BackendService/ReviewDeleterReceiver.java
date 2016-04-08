/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.NetworkServicesPlugin.NetworkServicesAndroid
        .Implementation.BackendService;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.NetworkServices.Backend.ReviewDeleterListener;

/**
 * Created by: Rizwan Choudrey
 * On: 04/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewDeleterReceiver extends BackendRepoServiceReceiver<ReviewDeleterListener>
        implements HasReviewId {
    public ReviewDeleterReceiver(ReviewId reviewId) {
        super(BackendRepoService.Service.DELETE.completed(), reviewId);
    }

    @Override
    protected void notifyListener(ReviewDeleterListener listener, DatumReviewId reviewId,
                                  CallbackMessage result) {
        listener.onDeletedFromBackend(reviewId, result);
    }
}
