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
import com.chdryra.android.reviewer.Social.Implementation.PublishResults;

import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 04/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface SocialPublisher extends NetworkPublisher<SocialPublisher.Listener>{
    interface Listener {
        void onPublishingFailed(ReviewId reviewId, Collection<String> platforms, CallbackMessage
                result);

        void onPublishingCompleted(ReviewId reviewId, Collection<PublishResults> publishedOk,
                                   Collection<PublishResults> publishedNotOk, CallbackMessage result);

        void onPublishingStatus(ReviewId reviewId, double percentage, PublishResults justUploaded);
    }
}
