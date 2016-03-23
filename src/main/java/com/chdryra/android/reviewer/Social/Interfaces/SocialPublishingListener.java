/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Social.Interfaces;

import com.chdryra.android.reviewer.Social.Implementation.PublishResults;
import com.chdryra.android.reviewer.Social.Implementation.SocialPublishingError;

import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 04/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface SocialPublishingListener {
    void onUploadStatus(double percentage, PublishResults justUploaded);

    void onUploadCompleted(Collection<PublishResults> publishedOk,
                           Collection<PublishResults> publishedNotOk, SocialPublishingError error);
}
