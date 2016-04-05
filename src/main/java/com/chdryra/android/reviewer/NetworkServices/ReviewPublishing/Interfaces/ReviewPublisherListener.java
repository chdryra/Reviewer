/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Interfaces;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.NetworkServices.Social.Implementation.PublishResults;
import com.chdryra.android.reviewer.Utils.CallbackMessage;

import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 05/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewPublisherListener {
    void onUploadFailed(ReviewId id, CallbackMessage result);

    void onUploadCompleted(ReviewId id, CallbackMessage result);

    void onPublishingFailed(ReviewId id, Collection<String> platforms, CallbackMessage result);

    void onPublishingCompleted(ReviewId id, Collection<PublishResults> platformsOk, Collection
            <PublishResults>
            platformsNotOk, CallbackMessage result);
}
