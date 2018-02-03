/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Application.Interfaces;

import com.chdryra.android.reviewer.Social.Implementation.SocialPlatformList;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPublisher;

/**
 * Created by: Rizwan Choudrey
 * On: 04/10/2016
 * Email: rizwan.choudrey@gmail.com
 */

public interface SocialSuite {
    SocialPlatformList getSocialPlatforms();

    SocialPublisher newPublisher();
}
