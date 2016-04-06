/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.NetworkServices.Social.Interfaces;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.NetworkServices.Social.Implementation.PublishResults;
import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;

import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 04/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface SocialPublishingListener {
    void onPublishStatus(ReviewId reviewId, double percentage, PublishResults justUploaded);

    void onPublishCompleted(ReviewId reviewId, Collection<PublishResults> publishedOk,
                            Collection<PublishResults> publishedNotOk, CallbackMessage result);
}
