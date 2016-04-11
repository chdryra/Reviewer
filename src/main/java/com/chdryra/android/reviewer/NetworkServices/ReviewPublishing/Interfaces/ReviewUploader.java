/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Interfaces;

/**
 * Created by: Rizwan Choudrey
 * On: 04/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewUploader extends NetworkPublisher<ReviewUploaderListener>{
    @Override
    void registerListener(ReviewUploaderListener reviewUploaderListener);

    @Override
    void unregisterListener(ReviewUploaderListener reviewUploaderListener);

    @Override
    void publishReview();
}
