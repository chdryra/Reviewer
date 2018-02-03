/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Interfaces;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 04/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewUploader extends NetworkUploader<ReviewUploader.Callback> {
    interface Callback {
        void onReviewUploaded(ReviewId reviewId, CallbackMessage result);
    }
}
