/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.NetworkServicesPlugin.NetworkServicesAndroid
        .Implementation.BackendService;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Interfaces.ReviewUploader;

/**
 * Created by: Rizwan Choudrey
 * On: 04/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewUploaderReceiver extends BackendRepoServiceReceiver<ReviewUploader.Callback>
        implements HasReviewId {

    public ReviewUploaderReceiver(ReviewId reviewId) {
        super(BackendRepoService.Service.UPLOAD.completed(), reviewId);
    }

    @Override
    protected void notifyListener(ReviewUploader.Callback callback, DatumReviewId reviewId,
                                  CallbackMessage result) {
        callback.onReviewUploaded(reviewId, result);
    }
}
