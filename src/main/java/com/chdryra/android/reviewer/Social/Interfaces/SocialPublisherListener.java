/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Social.Interfaces;

import com.chdryra.android.reviewer.Social.Implementation.PublishResults;

/**
 * Created by: Rizwan Choudrey
 * On: 12/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface SocialPublisherListener {
    void onPublished(PublishResults results);
}