/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.NetworkServicesPlugin.NetworkServicesAndroid
        .Implementation.BackendService;

import com.chdryra.android.corelibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumReviewId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.startouch.NetworkServices.ReviewDeleting.ReviewDeleterCallback;

/**
 * Created by: Rizwan Choudrey
 * On: 04/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewDeleterReceiver extends BackendRepoServiceReceiver<ReviewDeleterCallback>
        implements HasReviewId {
    public ReviewDeleterReceiver(ReviewId reviewId) {
        super(BackendRepoService.Service.DELETE.completed(), reviewId);
    }

    @Override
    protected void notifyListener(ReviewDeleterCallback listener, DatumReviewId reviewId,
                                  CallbackMessage result) {
        listener.onReviewDeleted(reviewId, result);
    }
}
