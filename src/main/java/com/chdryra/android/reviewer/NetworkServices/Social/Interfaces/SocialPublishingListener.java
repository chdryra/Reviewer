/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.NetworkServices.Social.Interfaces;

import com.chdryra.android.reviewer.Utils.CallbackMessage;
import com.chdryra.android.reviewer.NetworkServices.Social.Implementation.PublishResults;

import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 04/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface SocialPublishingListener {
    void onPublishStatus(double percentage, PublishResults justUploaded);

    void onPublishCompleted(Collection<PublishResults> publishedOk,
                            Collection<PublishResults> publishedNotOk, CallbackMessage result);
}
